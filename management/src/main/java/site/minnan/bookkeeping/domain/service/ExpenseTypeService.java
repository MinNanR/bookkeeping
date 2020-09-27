package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityExistException;

public interface ExpenseTypeService {

    void addExpenseType(String typeName, Integer userId) throws EntityExistException;
}
