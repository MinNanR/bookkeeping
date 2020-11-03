package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;

import java.util.Map;

public interface JournalTypeService {

    /**
     * 创建流水类型
     *
     * @param typeName
     * @param parentId
     * @param userId
     * @param direction
     */
    void createJournalType(String typeName, Integer parentId, Integer userId, JournalDirection direction);

    /**
     * 根据id查找名称集合
     * @param ids
     * @return
     */
    Map<Integer,String> mapIdToName(Iterable<Integer> ids);
}
