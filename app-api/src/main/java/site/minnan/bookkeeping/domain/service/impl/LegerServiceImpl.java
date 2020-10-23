package site.minnan.bookkeeping.domain.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.repository.LedgerRepository;
import site.minnan.bookkeeping.domain.service.AccountService;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.domain.service.WarehouseService;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
import java.util.Arrays;

@Service
public class LegerServiceImpl implements LedgerService {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private AccountService accountService;

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

    /**
     * 创建指定月份的账本
     *
     * @param accountId
     * @param year
     * @param month
     * @return
     */
    @Override
    public Ledger createLedger(Integer accountId, Integer year, Integer month) {
        return ledgerRepository.save(new Ledger(accountId, year, month));
    }

    /**
     * 修正账本
     *
     * @param source
     * @param target
     */
    @Override
    public void correctJournal(Journal source, Journal target) {
        Ledger sourceLedger, targetLedger;
        DateTime targetCreateTime = DateTime.of(target.getCreateTime());
        if (!DateUtil.isSameMonth(source.getCreateTime(), targetCreateTime)) {
            int year = targetCreateTime.year();
            int month = targetCreateTime.month() + 1;
            sourceLedger = ledgerRepository.findById(source.getLedgerId()).get();
            targetLedger = ledgerRepository.findOne((root, query, criteriaBuilder) -> {
                Predicate conjunction = criteriaBuilder.conjunction();
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("year"), year));
                conjunction.getExpressions().add(criteriaBuilder.equal(root.get("month"), month));
                return conjunction;
            }).orElseGet(() -> {
                Ledger ledger = createLedger(warehouseService.getAccountId(target.getWarehouseId()), year, month);
                target.changeLedger(ledger);
                return ledger;
            });
            sourceLedger.removeJournal(source);
            targetLedger.addJournal(source);
        } else {
            sourceLedger = ledgerRepository.findById(source.getLedgerId()).get();
            targetLedger = sourceLedger;
        }
        //金额有变化时
        if (!source.getAmount().equals(target.getAmount())) {
            targetLedger.correctJournal(source, target);
            accountService.correctJournal(source, target, warehouseService.getAccountId(target.getWarehouseId()));
        }
        ledgerRepository.saveAll(Arrays.asList(sourceLedger, targetLedger));
    }
}
