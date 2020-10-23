package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.aggreates.Ledger;

public interface LedgerService {

    /**
     * 创建账本
     *
     * @return
     */
    Ledger createLedger(Integer accountId);

    /**
     * 创建指定月份的账本
     *
     * @param accountId
     * @param year
     * @param month
     * @return
     */
    Ledger createLedger(Integer accountId, Integer year, Integer month);

    /**
     * 修正账本
     *
     * @param source
     * @param target
     */
    void correctJournal(Journal source, Journal target);
}
