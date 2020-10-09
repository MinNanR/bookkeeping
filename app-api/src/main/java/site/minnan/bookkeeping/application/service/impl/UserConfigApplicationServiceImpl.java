package site.minnan.bookkeeping.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.UserConfigApplicationService;
import site.minnan.bookkeeping.domain.aggreates.Account;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.domain.repository.AccountRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.AddWarehouseDTO;

import java.util.Optional;

@Service
public class UserConfigApplicationServiceImpl implements UserConfigApplicationService {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void createWarehouse(AddWarehouseDTO dto) throws EntityAlreadyExistException {
        Account account = accountRepository.findOne(SpecificationGenerator.equal("userId",
                dto.getUserId())).get();
        Warehouse warehouse = warehouseService.createWarehouse(account.getId(), dto.getWarehouseName(),
                Optional.ofNullable(dto.getBalance()));
        account.addWarehouse(warehouse);
        accountRepository.save(account);
    }
}

