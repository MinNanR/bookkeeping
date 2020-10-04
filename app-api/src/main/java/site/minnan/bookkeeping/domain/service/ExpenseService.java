package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

import java.math.BigDecimal;

public interface ExpenseService {

    void addExpense(Integer warehouseId,Integer expenseTypeId, BigDecimal amount) throws EntityNotExistException;
}
