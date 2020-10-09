package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;

import java.math.BigDecimal;
import java.util.Optional;

public interface WarehouseService {

    Warehouse createWarehouse(Integer accountId, String warehouseNam, Optional<BigDecimal> balance) throws EntityAlreadyExistException;
}
