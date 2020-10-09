package site.minnan.bookkeeping.aplication.service;

import org.springframework.dao.EmptyResultDataAccessException;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.expensetype.ExpenseTypeVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.expensetype.DeleteExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.AddExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.GetExpenseTypeListDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.UpdateExpenseTypeDTO;

public interface ExpenseTypeApplicationService {

    /**
     * 添加支出类型
     *
     * @param dto
     * @throws EntityAlreadyExistException
     */
    void addExpenseType(AddExpenseTypeDTO dto) throws EntityAlreadyExistException;

    /**
     * 查询支出类型
     *
     * @param dto
     * @return
     */
    QueryVO<ExpenseTypeVO> getExpenseTypeList(GetExpenseTypeListDTO dto);

    /**
     * 修改支出类型
     *
     * @param dto
     * @throws EntityNotExistException
     * @throws EntityNotExistException 找不到id对应的支出类型
     */
    void updateExpenseType(UpdateExpenseTypeDTO dto) throws EntityNotExistException, EntityAlreadyExistException;

    /**
     * 删除支出类型
     *
     * @param dto
     */
    void deleteExpenseType(DeleteExpenseTypeDTO dto) throws EmptyResultDataAccessException;
}
