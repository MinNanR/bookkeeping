package site.minnan.bookkeeping.aplication.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.IncomeTypeApplicationService;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.entity.IncomeType;
import site.minnan.bookkeeping.domain.repository.IncomeTypeRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.AdministratorService;
import site.minnan.bookkeeping.domain.service.IncomeTypeService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.expensetype.ExpenseTypeVO;
import site.minnan.bookkeeping.domain.vo.incometype.IncomeTypeVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.incometype.AddIncomeTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.DeleteIncomeTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.GetIncomeTypeListDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.UpdateIncomeTypeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IncomeTypeApplicationServiceImpl implements IncomeTypeApplicationService {

    @Autowired
    private IncomeTypeService incomeTypeService;

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private AdministratorService administratorService;

    /**
     * 添加收入类型
     *
     * @param dto
     * @throws EntityAlreadyExistException 收入类型名称已存在
     */
    @Override
    public void addIncomeType(AddIncomeTypeDTO dto) throws EntityAlreadyExistException {
        incomeTypeService.addIncomeType(dto.getTypeName(), dto.getUserId());
    }

    /**
     * 查询支出类型
     *
     * @param dto
     * @return
     */
    @Override
    public QueryVO<IncomeTypeVO> getIncomeTypeList(GetIncomeTypeListDTO dto) {
        String typeName = Optional.ofNullable(dto.getTypeName()).orElse("");
        Page<IncomeType> incomeTypePage = incomeTypeRepository.findAll(SpecificationGenerator.like("typeName",
                typeName), PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize(), Sort.by(Sort.Direction.DESC,
                "updateTime")));
        Set<Integer> userIdSet = incomeTypePage.get().map(IncomeType::getUpdateUserId).collect(Collectors.toSet());
        Map<Integer, String> idToUsernameMap = administratorService.mapAdministratorIdToUsername(userIdSet);
        List<IncomeTypeVO> voList = incomeTypePage.get().map(et -> new IncomeTypeVO(et.getId(), et.getTypeName(),
                DateUtil.format(et.getUpdateTime(),
                        "yyyy-MM-dd HH:mm:ss"), idToUsernameMap.get(et.getUpdateUserId()))).collect(Collectors.toList());
        return new QueryVO<>(voList, incomeTypePage.getTotalElements());
    }

    /**
     * 修改收入类型
     *
     * @param dto
     * @throws EntityNotExistException     找不到id对应的收入类型
     * @throws EntityAlreadyExistException 该名称收入类型已存在
     */
    @Override
    public void updateIncomeType(UpdateIncomeTypeDTO dto) throws EntityNotExistException, EntityAlreadyExistException {
        incomeTypeService.updateIncomeType(dto.getId(), dto.getTypeName(), dto.getUserId());
    }

    /**
     * 删除收入类型
     *
     * @param dto
     * @throws EmptyResultDataAccessException
     */
    @Override
    public void deleteIncomeType(DeleteIncomeTypeDTO dto) throws EmptyResultDataAccessException {
        incomeTypeRepository.deleteById(dto.getId());
    }
}
