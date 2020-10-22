package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Journal;

public interface AccountService {

    /**
     * 修正账户中的余额
     * @param source
     * @param target
     * @param accountId
     */
    void correctJournal(Journal source, Journal target, Integer accountId);
}
