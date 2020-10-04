package site.minnan.bookkeeping.aplication.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.ExpenseTypeApplicationService;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.repository.ExpenseTypeRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.AdministratorService;
import site.minnan.bookkeeping.domain.service.ExpenseTypeService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.expensetype.ExpenseTypeVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.expensetype.DeleteExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.AddExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.GetExpenseTypeListDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.UpdateExpenseTypeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpenseTypeApplicationServiceImpl implements ExpenseTypeApplicationService {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    /**
     * 添加支出类型
     *
     * @param dto
     * @throws EntityAlreadyExistException
     */
    @Override
    public void addExpenseType(AddExpenseTypeDTO dto) throws EntityAlreadyExistException {
        expenseTypeService.addExpenseType(dto.getTypeName(), dto.getUserId());
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
        Page<ExpenseType> expenseTypePage = expenseTypeRepository.findAll(SpecificationGenerator.like("typeName",
                typeName), PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize(), Sort.by(Sort.Direction.DESC,
                "updateTime")));
        Set<Integer> userIdSet = expenseTypePage.get().map(ExpenseType::getUpdateUserId).collect(Collectors.toSet());
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
     */
    @Override
    public void updateExpenseType(UpdateExpenseTypeDTO dto) throws EntityNotExistException {
        Optional<ExpenseType> expenseTypeInDB = expenseTypeRepository.findById(dto.getId());
        ExpenseType expenseType = expenseTypeInDB.orElseThrow(EntityNotExistException::new);
        expenseType.changeTypeName(dto.getTypeName(), dto.getUserId());
        expenseTypeRepository.save(expenseType);
    }

    /**
     * 删除支出类型
     *
     * @param dto
     */
    @Override
    public void deleteExpenseType(DeleteExpenseTypeDTO dto) throws EmptyResultDataAccessException {
        expenseTypeRepository.deleteById(dto.getId());
    }
}
