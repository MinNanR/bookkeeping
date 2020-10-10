package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.AddExpenseDTO;
import site.minnan.bookkeeping.userinterface.dto.AddIncomeDTO;
import site.minnan.bookkeeping.userinterface.dto.ModifyExpenseDTO;

public interface RecordService {

    /**
     * 添加支出记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    void addExpense(AddExpenseDTO dto) throws EntityNotExistException;

    /**
     * 添加收入记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    void addIncome(AddIncomeDTO dto) throws EntityNotExistException;

    /**
     * 修改支出记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    void modifyExpense(ModifyExpenseDTO dto) throws EntityNotExistException;
}
