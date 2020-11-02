package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.userinterface.dto.config.AddJournalTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.config.AddLedgerDTO;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;

public interface CustomConfigService {

    /**
     * 创建账本
     *
     * @param dto
     */
    void createLedger(AddLedgerDTO dto);

    /**
     * 创建账户
     *
     * @param dto
     */
    void createWarehouse(AddWarehouseDTO dto);

    /**
     * 添加流水类型
     *
     * @param dto
     * @param direction
     */
    void createJournalType(AddJournalTypeDTO dto, JournalDirection direction);
}
