package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.AddExpenseDTO;

public interface RecordApplicationService {

    void addExpense(AddExpenseDTO dto) throws EntityNotExistException;
}
