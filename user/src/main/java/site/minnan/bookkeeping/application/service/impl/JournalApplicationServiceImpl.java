package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import site.minnan.bookkeeping.domain.service.JournalService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.domain.vo.JournalListItemVO;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.journal.DeleteJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.QueryJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.UpdateJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
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

    @Autowired
    private JournalService journalService;

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

    /**
     * 删除记录
     *
     * @param dto
     */
    @Override
    @Transactional
    public void deleteJournal(DeleteJournalDTO dto) throws JsonProcessingException {
        Optional<Journal> journalOptional = journalRepository.findById(dto.getId());
        Journal journal = journalOptional.orElseThrow(() -> new EntityNotExistException("记录不存在"));
        Journal copy = journal.copy();
        copy.changeAmount(BigDecimal.ZERO);
        Ledger ledger = ledgerRepository.findById(journal.getLedgerId()).get();
        ledger.correct(journal, copy);
        ledgerRepository.save(ledger);
        warehouseService.correctJournal(journal, copy);
        journalRepository.delete(journal);
    }

    /**
     * 查询记录
     *
     * @param dto
     * @return
     */
    @Override
    public QueryVO<JournalListItemVO> getJournalList(QueryJournalDTO dto) {
        Integer ledgerId = dto.getLedgerId();
        Optional<Integer> warehouseId = Optional.ofNullable(dto.getWarehouseId());
        Optional<JournalDirection> direction = Optional.ofNullable(dto.getDirection());
        Integer pageIndex = dto.getPageIndex();
        Integer pageSize = dto.getPageSize();
        Page<Journal> journalPage = journalRepository.findAll((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("ledgerId"), ledgerId));
            warehouseId.ifPresent(id -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get("warehouseId"), id)));
            direction.ifPresent(d -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get(
                    "journalDirection"), d)));
            return conjunction;
        }, PageRequest.of(pageIndex - 1, pageSize, Sort.by(Sort.Direction.DESC, "journalTime")));
        List<JournalListItemVO> voList = journalService.assembleJournal(journalPage.getContent());
        return new QueryVO<>(voList, journalPage.getTotalElements());
    }
}
