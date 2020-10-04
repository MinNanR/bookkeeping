package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;

import java.math.BigDecimal;
import java.util.Optional;

public interface WarehouseService {

    void createWarehouse(Integer ledgerId, String warehouseNam, Optional<BigDecimal> balance) throws EntityAlreadyExistException;
}
