package site.minnan.bookkeeping.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.CustomConfigService;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.userinterface.dto.config.AddLedgerDTO;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;

@Service
public class CustomConfigServiceImpl implements CustomConfigService {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private WarehouseService warehouseService;

    /**
     * 创建账本
     *
     * @param dto
     */
    @Override
    public void createLedger(AddLedgerDTO dto) {
        ledgerService.createLedger(dto.getLedgerName(), dto.getUserId());
    }

    /**
     * 创建账户
     *
     * @param dto
     */
    @Override
    public void createWarehouse(AddWarehouseDTO dto) {
        warehouseService.createWarehouse(dto);
    }
}
