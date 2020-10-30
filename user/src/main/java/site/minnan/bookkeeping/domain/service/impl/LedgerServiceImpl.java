package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Currency;
import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.repository.CurrencyRepository;
import site.minnan.bookkeeping.domain.repository.LedgerRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class LedgerServiceImpl implements LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public void createLedger(String legerName, Integer userId) {
        Optional<Currency> currencyOptional = currencyRepository.findOne(SpecificationGenerator.equal("code", "CNY"));
        Currency currency = currencyOptional.orElseThrow(() -> new EntityNotExistException("未添加人民币货币"));
        Ledger newLedger = Ledger.of(legerName, currency, userId);
        ledgerRepository.save(newLedger);
    }

    @Override
    public void createWarehouse(Integer ledgerId, BigDecimal amount) {
        Ledger ledger = ledgerRepository.findById(ledgerId).get();
        ledger.setTotalBalance(ledger.getTotalBalance().add(amount));
        ledgerRepository.save(ledger);
    }
}
