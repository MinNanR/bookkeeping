package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.UpdateJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.sql.Timestamp;
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

    @Autowired
    private WarehouseService warehouseService;

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
        warehouse.settleJournal(journal);
        ledger.settle(journal);
        warehouseRepository.save(warehouse);
        ledgerRepository.save(ledger);
        journalRepository.save(journal);
    }

    /**
     * 修改记录
     *
     * @param dto
     */
    @Override
    public void modifyJournal(UpdateJournalDTO dto) throws JsonProcessingException {
        Optional<Journal> journalOptional = journalRepository.findById(dto.getId());
        Journal journal = journalOptional.orElseThrow(() -> new EntityNotExistException("记录不存在"));
        Journal copy = journal.copy();
        Optional<JournalType> journalType = Optional.ofNullable(dto.getJournalTypeId())
                .map(id ->
                        journalTypeRepository.findOne((root, query, criteriaBuilder) -> {
                            Predicate conjunction = criteriaBuilder.conjunction();
                            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("journalDirection"),
                                    journal.getJournalDirection()));
                            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("id"), id));
                            return conjunction;
                        }).orElseThrow(() -> new EntityNotExistException("类型不存在"))
                );
        Optional<Timestamp> journalTime = Optional.ofNullable(dto.getJournalTime());
        Optional<String> remark = Optional.ofNullable(dto.getRemark());
        copy.changeInformation(journalType, journalTime, remark);
        if (dto.getAmount() != null) {
            copy.changeAmount(dto.getAmount());
            Ledger ledger = ledgerRepository.findById(copy.getLedgerId()).get();
            ledger.correct(journal, copy);
            ledgerRepository.save(ledger);
        }
        if (dto.getWarehouseId() != null) {
            copy.changeWarehouse(dto.getWarehouseId());
        }
        warehouseService.correctJournal(journal, copy);
        journalRepository.save(copy);
    }
}
