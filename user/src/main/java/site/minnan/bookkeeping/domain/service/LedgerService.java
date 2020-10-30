package site.minnan.bookkeeping.domain.service;

import java.math.BigDecimal;

public interface LedgerService {

    void createLedger(String legerName, Integer userId);

    void createWarehouse(Integer ledgerId, BigDecimal amount);
}
