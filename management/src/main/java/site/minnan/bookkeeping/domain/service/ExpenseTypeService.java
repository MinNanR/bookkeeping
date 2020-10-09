package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

public interface ExpenseTypeService {

    /**
     * 添加支出类型
     * @param typeName
     * @param userId
     * @throws EntityAlreadyExistException 支出类型名称已存在
     */
    void addExpenseType(String typeName, Integer userId) throws EntityAlreadyExistException;

    /**
     * 修改类型名称
     * @param id
     * @param newTypeName
     * @param userId
     * @throws EntityAlreadyExistException 支出类型名称已存在
     * @throws EntityNotExistException 找不到id对应的支出类型
     */
    void changeExpenseTypeName(Integer id, String newTypeName, Integer userId) throws EntityAlreadyExistException,
            EntityNotExistException;
}
