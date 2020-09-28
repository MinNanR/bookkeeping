package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;

public interface ExpenseTypeService {

    /**
     * 添加支出类型
     * @param typeName
     * @param userId
     * @throws EntityAlreadyExistException
     */
    void addExpenseType(String typeName, Integer userId) throws EntityAlreadyExistException;
}
