package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;

import java.util.Map;

public interface WarehouseService {

    /**
     * 创建账户
     *
     * @param dto
     */
    void createWarehouse(AddWarehouseDTO dto);

    /**
     * 修正流水记录
     *
     * @param source
     * @param target
     */
    void correctJournal(Journal source, Journal target);

    /**
     * 将id映射成对应的名字
     *
     * @param ids
     * @return
     */
    Map<Integer, String> mapIdToWarehouseName(Iterable<Integer> ids);

    /**
     * 将货币id映射为货币名称
     *
     * @param ids
     * @return
     */
    Map<Integer, String> mapIdToCurrencyName(Iterable<Integer> ids);
}
