package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.JournalApplicationService;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.aggreates.JournalType;
import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.domain.repository.JournalRepository;
import site.minnan.bookkeeping.domain.repository.JournalTypeRepository;
import site.minnan.bookkeeping.domain.repository.LedgerRepository;
import site.minnan.bookkeeping.domain.repository.WarehouseRepository;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class JournalApplicationServiceImpl implements JournalApplicationService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private JournalTypeRepository journalTypeRepository;

    @Autowired
    private JournalRepository journalRepository;


    /**
     * 添加记录
     *
     * @param dto
     * @param direction
     */
    @Override
    @Transactional
    public void addJournal(AddJournalDTO dto, JournalDirection direction) {
        Optional<JournalType> journalTypeOptional = journalTypeRepository.findById(dto.getJournalTypeId());
        if (!journalTypeOptional.isPresent() || !direction.equals(journalTypeOptional.get().getJournalDirection())) {
            throw new EntityNotExistException("流水类型不存在");
        }
        JournalType journalType = journalTypeOptional.get();
        Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                .orElseThrow(() -> new EntityNotExistException("账户不存在"));
        Ledger ledger = ledgerRepository.findById(dto.getLedgerId())
                .orElseThrow(() -> new EntityNotExistException("账本不存在"));
        Timestamp journalTime = Optional.ofNullable(dto.getJournalTime()).orElseGet(() ->
                Timestamp.valueOf(DateUtil.format(DateTime.now(), "yyyy-MM-dd HH:mm:00")));
        Journal journal = Journal.of(dto.getAmount(), warehouse, ledger, journalType, journalTime, dto.getRemark());
        warehouse.settle(journal);
        ledger.settle(journal);
        warehouseRepository.save(warehouse);
        ledgerRepository.save(ledger);
        journalRepository.save(journal);
    }
}
