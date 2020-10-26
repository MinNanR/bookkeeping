package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.RecordService;
import site.minnan.bookkeeping.domain.aggreates.Account;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.domain.entity.JournalType;
import site.minnan.bookkeeping.domain.repository.*;
import site.minnan.bookkeeping.domain.service.JournalService;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.journal.JournalVO;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.DeleteJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.GetJournalListDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.ModifyJournalDTO;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private JournalTypeRepository journalTypeRepository;

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private JournalService journalService;

    /**
     * 添加流水记录
     *
     * @param dto
     * @param direction
     * @throws EntityNotExistException
     */
    @Override
    public void addJournal(AddJournalDTO dto, JournalDirection direction) throws EntityNotExistException {
        Timestamp createTime = dto.getCreateTime();
        DateTime dateTime = new DateTime(createTime);
        int year = dateTime.year();
        int month = dateTime.month() + 1;
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(dto.getWarehouseId());
        Warehouse warehouse = warehouseOptional.orElseThrow(() -> new EntityNotExistException("金库不存在"));
        Optional<Ledger> ledgerOptional = ledgerRepository.findOne((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("accountId"), warehouse.getAccountId()));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("year"), year));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("month"), month));
            return conjunction;
        });
        Optional<Account> accountOptional = accountRepository.findById(warehouse.getAccountId());
        Account account = accountOptional.orElseThrow(() -> new EntityNotExistException("账户不存在"));
        Ledger ledger = ledgerOptional.orElseGet(() -> ledgerService.createLedger(warehouse.getAccountId(), year,
                month));
        Optional<JournalType> journalTypeOptional = journalTypeRepository.findById(dto.getJournalTypeId());
        JournalType journalType = journalTypeOptional.orElseThrow(() -> new EntityNotExistException("支出类型不存在"));
        if (!direction.equals(journalType.getJournalDirection())) {
            throw new EntityNotExistException("类型不存在");
        }
        Journal journal = Journal.of(warehouse.getId(), ledger.getId(), dto.getAmount(), journalType, createTime,
                dto.getRemark());
        //记入账号
        account.settle(journal);
        //记入当月账本
        ledger.addJournal(journal);
        //记入金库中
        warehouse.settleJournal(journal);
        accountRepository.save(account);
        ledgerRepository.save(ledger);
        warehouseRepository.save(warehouse);
        journalRepository.save(journal);
    }

    /**
     * 修改流水记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    @Override
    public void modifyJournal(ModifyJournalDTO dto) throws EntityNotExistException, JsonProcessingException {
        Optional<Journal> journalOptional = journalRepository.findById(dto.getId());
        Journal journal = journalOptional.orElseThrow(() -> new EntityNotExistException("记录不存在"));
        Journal copy = journal.copy();
        Optional<JournalType> JournalType = Optional.ofNullable(dto.getJournalTypeId()).map(id -> {
            JournalType journalType = journalTypeRepository.findById(id).orElseThrow(() -> new EntityNotExistException(
                    "类型不存在"));
            if (!journal.getJournalDirection().equals(journalType.getJournalDirection()))
                throw new EntityNotExistException("类型不存在");
            return journalType;
        });
        Optional<Timestamp> createTime = Optional.ofNullable(dto.getCreateTime());
        Optional<String> remark = Optional.ofNullable(dto.getRemark());
        copy.changeInformation(JournalType, createTime, remark);
        if (dto.getWarehouseId() != null) {
            Warehouse warehouse =
                    warehouseRepository.findById(dto.getWarehouseId()).orElseThrow(() -> new EntityNotExistException(
                            "金库不存在"));
            copy.changeWarehouse(warehouse);
        }
        if (dto.getAmount() != null) {
            copy.changeAmount(dto.getAmount());
        }
        warehouseService.correctJournal(journal, copy);
        ledgerService.correctJournal(journal, copy);
        journalRepository.save(copy);
    }

    /**
     * 删除支出记录
     *
     * @param dto
     */
    @Override
    public void deleteJournal(DeleteJournalDTO dto) throws EntityNotExistException, JsonProcessingException {
        Optional<Journal> journalOptional = journalRepository.findById(dto.getId());
        Journal journal = journalOptional.orElseThrow(() -> new EntityNotExistException("记录不存在"));
        Journal copy = journal.copy();
        copy.changeAmount(BigDecimal.ZERO);
        warehouseService.correctJournal(journal, copy);
        ledgerService.correctJournal(journal, copy);
        journalRepository.delete(copy);
    }

    /**
     * 查询流水记录
     *
     * @param dto
     * @return
     */
    @Override
    public QueryVO<JournalVO> getJournalList(GetJournalListDTO dto) {
        Integer accountId = accountRepository.findAccountIdFirstByUserId(dto.getUserId());
        Optional<String> warehouseId = Optional.ofNullable(dto.getWarehouseId());
        Optional<Integer> journalTypeId = Optional.ofNullable(dto.getJournalTypeId());
        Optional<Timestamp> startTime = Optional.ofNullable(dto.getStartTime());
        Optional<Timestamp> endTime = Optional.ofNullable(dto.getEndTime());
        //TODO 找不到accountId，需要在根对象上补充
        Page<JournalVO> journalPage = journalRepository.findAll((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            warehouseId.ifPresent(id -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get("id"), id)));
            journalTypeId.ifPresent(id -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get(
                    "journalTypeId"), id)));
            startTime.ifPresent(time -> conjunction.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.get(
                    "createTime"), time)));
            endTime.ifPresent(time -> conjunction.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.get(
                    "createTime"), time)));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("accountId"), accountId));
            return conjunction;
        }, PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize(), Sort.by(Sort.Direction.DESC, "createTime")))
                .map(journal -> journalService.assembleJournalVO(journal));
        return new QueryVO<>(journalPage.getContent(), journalPage.getTotalElements());
    }


}
