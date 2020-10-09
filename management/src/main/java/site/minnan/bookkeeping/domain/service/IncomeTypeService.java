package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

public interface IncomeTypeService {

    /**
     * 添加收入类型
     *
     * @param typeName
     * @param userId
     * @throws EntityAlreadyExistException 名称已存在
     */
    void addIncomeType(String typeName, Integer userId) throws EntityAlreadyExistException;

    /**
     * 修改收入类型
     *
     * @param id
     * @param newTypeName
     * @param userId
     */
    void updateIncomeType(Integer id, String newTypeName, Integer userId) throws EntityNotExistException,
            EntityAlreadyExistException;
}
