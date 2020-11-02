package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;

public interface JournalTypeService {

    /**
     * 创建流水类型
     * @param typeName
     * @param parentId
     * @param userId
     * @param direction
     */
    void createJournalType(String typeName, Integer parentId, Integer userId, JournalDirection direction);
}
