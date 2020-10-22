package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Journal;

public interface JournalService {

    /**
     * 修改账本
     * @param source
     * @param target
     */
    void changeLedger(Journal source, Journal target);

    /**
     * 修改金额
     * @param source
     * @param target
     */
    void changeAmount(Journal source, Journal target);
}
