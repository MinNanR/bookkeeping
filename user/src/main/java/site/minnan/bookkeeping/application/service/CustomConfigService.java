package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.userinterface.dto.config.AddLedgerDTO;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;

public interface CustomConfigService {

    /**
     * 创建账本
     * @param dto
     */
    void createLedger(AddLedgerDTO dto);

    /**
     * 创建账户
     * @param dto
     */
    void createWarehouse(AddWarehouseDTO dto);
}
