package site.minnan.bookkeeping.aplication.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.ExpenseTypeApplicationService;
import site.minnan.bookkeeping.domain.entity.JournalType;
import site.minnan.bookkeeping.domain.repository.JournalTypeRepository;
import site.minnan.bookkeeping.domain.service.AdministratorService;
import site.minnan.bookkeeping.domain.service.JournalTypeService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.expensetype.ExpenseTypeVO;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.expensetype.DeleteExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.AddExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.GetExpenseTypeListDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.UpdateExpenseTypeDTO;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpenseTypeApplicationServiceImpl implements ExpenseTypeApplicationService {

    @Autowired
    private JournalTypeService journalTypeService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private JournalTypeRepository journalTypeRepository;

    /**
     * 添加支出类型
     *
     * @param dto
     * @throws EntityAlreadyExistException
     */
    @Override
    public void addExpenseType(AddExpenseTypeDTO dto) throws EntityAlreadyExistException {
        journalTypeService.addJournalType(dto.getTypeName(), dto.getUserId(), JournalDirection.EXPENSE);
    }

    /**
     * 查询支出类型
     *
     * @param dto
     * @return
     */
    @Override
    public QueryVO<ExpenseTypeVO> getExpenseTypeList(GetExpenseTypeListDTO dto) {
        String typeName = Optional.ofNullable(dto.getTypeName()).orElse("");
        Page<JournalType> expenseTypePage = journalTypeRepository.findAll((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.like(root.get("typeName"), StrUtil.format("%{}%",
                    typeName)));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("journalDirection"),
                    JournalDirection.EXPENSE));
            return conjunction;
        }, PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize(), Sort.by(Sort.Direction.DESC, "updateTime")));
        Set<Integer> userIdSet = expenseTypePage.get().map(JournalType::getUpdateUserId).collect(Collectors.toSet());
        Map<Integer, String> idToUsernameMap = administratorService.mapAdministratorIdToUsername(userIdSet);
        List<ExpenseTypeVO> voList = expenseTypePage.get().map(et -> new ExpenseTypeVO(et.getId(), et.getTypeName(),
                DateUtil.format(et.getUpdateTime(),
                        "yyyy-MM-dd HH:mm:ss"), idToUsernameMap.get(et.getUpdateUserId()))).collect(Collectors.toList());
        return new QueryVO<>(voList, expenseTypePage.getTotalElements());
    }

    /**
     * 修改支出类型
     *
     * @param dto
     * @throws EntityNotExistException
     * @throws EntityNotExistException 找不到id对应的支出类型
     */
    @Override
    public void updateExpenseType(UpdateExpenseTypeDTO dto) throws EntityNotExistException,
            EntityAlreadyExistException {
        journalTypeService.updateJournalType(dto.getId(), dto.getTypeName(), dto.getUserId(), JournalDirection.EXPENSE);
    }

    /**
     * 删除支出类型
     *
     * @param dto
     */
    @Override
    public void deleteExpenseType(DeleteExpenseTypeDTO dto) throws EmptyResultDataAccessException {
        journalTypeRepository.deleteById(dto.getId());
    }
}
