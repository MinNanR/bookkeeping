package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.userinterface.dto.AddLedgerDTO;
import site.minnan.bookkeeping.userinterface.dto.AddWarehouseDTO;

public interface UserConfigApplicationService {

    void createLedger(AddLedgerDTO dto);

    void createWarehouse(AddWarehouseDTO dto) throws EntityAlreadyExistException;
}
