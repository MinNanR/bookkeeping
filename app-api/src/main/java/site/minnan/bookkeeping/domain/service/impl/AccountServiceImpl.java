package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Account;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.repository.AccountRepository;
import site.minnan.bookkeeping.domain.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 修正账户中的余额
     *
     * @param source
     * @param target
     * @param accountId
     */
    @Override
    public void correctJournal(Journal source, Journal target, Integer accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.settle(source, target);
        accountRepository.save(account);
    }
}
