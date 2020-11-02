package site.minnan.bookkeeping.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.CustomConfigService;
import site.minnan.bookkeeping.domain.service.JournalTypeService;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.userinterface.dto.config.AddJournalTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.config.AddLedgerDTO;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;

import java.util.Optional;

@Service
public class CustomConfigServiceImpl implements CustomConfigService {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private JournalTypeService journalTypeService;

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

    /**
     * 添加流水类型
     *
     * @param dto
     * @param direction
     */
    @Override
    public void createJournalType(AddJournalTypeDTO dto, JournalDirection direction) {
        Integer parentId = dto.getParentId() == null ? 0 : dto.getParentId();
        journalTypeService.createJournalType(dto.getTypeName(), parentId, dto.getUserId(), direction);
    }
}
