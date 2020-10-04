package site.minnan.bookkeeping.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.RecordApplicationService;
import site.minnan.bookkeeping.domain.service.ExpenseService;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.AddExpenseDTO;

@Service
public class RecordApplicationServiceImpl implements RecordApplicationService {

    @Autowired
    private ExpenseService expenseService;

    @Override
    public void addExpense(AddExpenseDTO dto) throws EntityNotExistException {
        expenseService.addExpense(dto.getWarehouseId(), dto.getExpenseTypeId(), dto.getAmount());
    }
}
