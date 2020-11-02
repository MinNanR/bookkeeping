package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;

public interface WarehouseService {

    /**
     * 创建账户
     *
     * @param dto
     */
    void createWarehouse(AddWarehouseDTO dto);

    /**
     * 修正流水记录
     * @param source
     * @param target
     */
    void correctJournal(Journal source, Journal target);
}
