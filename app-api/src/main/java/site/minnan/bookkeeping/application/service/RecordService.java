package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.AddExpenseDTO;

public interface RecordService {

    /**
     * 添加支出记录
     *
     * @param dto
     */
    void addExpense(AddExpenseDTO dto) throws EntityNotExistException;
}
