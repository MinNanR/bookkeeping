package site.minnan.bookkeeping.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.UserConfigApplicationService;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.userinterface.dto.AddLedgerDTO;
import site.minnan.bookkeeping.userinterface.dto.AddWarehouseDTO;

import java.util.Optional;

@Service
public class UserConfigApplicationServiceImpl implements UserConfigApplicationService {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private WarehouseService warehouseService;

    @Override
    public void createLedger(AddLedgerDTO dto) {
        ledgerService.addLedger(dto.getLedgerName(), dto.getUserId());
    }

    @Override
    public void createWarehouse(AddWarehouseDTO dto) throws EntityAlreadyExistException {
        warehouseService.createWarehouse(dto.getLedgerId(), dto.getWarehouseName(), Optional.ofNullable(dto.getBalance()));
    }
}

