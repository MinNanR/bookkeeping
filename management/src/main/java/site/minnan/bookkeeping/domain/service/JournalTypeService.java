package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

public interface JournalTypeService {

    /**
     * 添加流水类型类型
     *
     * @param typeName
     * @param userId
     * @param journalDirection
     * @throws EntityAlreadyExistException 名称已存在
     */
    void addJournalType(String typeName, Integer userId, JournalDirection journalDirection) throws EntityAlreadyExistException;

    /**
     * 修改流水类型
     *  @param id
     * @param newTypeName
     * @param userId
     * @param journalDirection
     */
    void updateJournalType(Integer id, String newTypeName, Integer userId, JournalDirection journalDirection) throws EntityNotExistException,
            EntityAlreadyExistException;
}
