package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Ledger;

public interface LedgerService {

    /**
     * 创建账本
     *
     * @return
     */
    Ledger createLedger(Integer accountId);
}
