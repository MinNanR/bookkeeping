package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.repository.LedgerRepository;
import site.minnan.bookkeeping.domain.service.LedgerService;

@Service
public class LegerServiceImpl implements LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    /**
     * 创建账本
     *
     * @param accountId
     * @return
     */
    @Override
    public Ledger createLedger(Integer accountId) {
        return ledgerRepository.save(new Ledger(accountId));
    }
}
