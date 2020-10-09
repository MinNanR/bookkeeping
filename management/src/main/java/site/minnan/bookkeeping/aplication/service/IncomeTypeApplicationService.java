package site.minnan.bookkeeping.aplication.service;

import org.springframework.dao.EmptyResultDataAccessException;
import site.minnan.bookkeeping.domain.vo.incometype.IncomeTypeVO;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.incometype.AddIncomeTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.DeleteIncomeTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.GetIncomeTypeListDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.UpdateIncomeTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.GetExpenseTypeListDTO;

public interface IncomeTypeApplicationService {

    /**
     * 添加收入类型
     *
     * @param dto
     * @throws EntityAlreadyExistException 收入类型名称已存在
     */
    void addIncomeType(AddIncomeTypeDTO dto) throws EntityAlreadyExistException;

    /**
     * 查询支出类型
     *
     * @param dto
     * @return
     */
    QueryVO<IncomeTypeVO> getIncomeTypeList(GetIncomeTypeListDTO dto);

    /**
     * 修改收入类型
     *
     * @param dto
     * @throws EntityNotExistException     找不到id对应的收入类型
     * @throws EntityAlreadyExistException 该名称收入类型已存在
     */
    void updateIncomeType(UpdateIncomeTypeDTO dto) throws EntityNotExistException, EntityAlreadyExistException;

    /**
     * 删除收入类型
     *
     * @param dto
     * @throws EmptyResultDataAccessException
     */
    void deleteIncomeType(DeleteIncomeTypeDTO dto) throws EmptyResultDataAccessException;
}
