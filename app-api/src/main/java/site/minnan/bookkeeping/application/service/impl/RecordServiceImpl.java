package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
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
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.*;

import javax.persistence.criteria.Predicate;
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
    private JournalService journalService;

    @Autowired
    private WarehouseService warehouseService;

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
    public void modifyJournal(ModifyJournalDTO dto, JournalDirection direction) throws EntityNotExistException, JsonProcessingException {
        Optional<Journal> expenseOptional = journalRepository.findById(dto.getId());
        Journal expense = expenseOptional.orElseThrow(() -> new EntityNotExistException("支出记录不存在"));
        Journal copy = expense.copy();
        Optional<JournalType> expenseType = Optional.ofNullable(dto.getJournalTypeId()).map(id -> {
            JournalType journalType = journalTypeRepository.findById(id).orElseThrow(() -> new EntityNotExistException(
                    "支出类型不存在"));
            if (!direction.equals(journalType.getJournalDirection())) {
                throw new EntityNotExistException("类型不存在");
            }
            return journalType;
        });
        Optional<Timestamp> createTime = Optional.ofNullable(dto.getCreateTime());
        Optional<String> remark = Optional.ofNullable(dto.getRemark());
        copy.changeInformation(expenseType, createTime, remark);
        if (dto.getWarehouseId() != null) {
            Warehouse warehouse =
                    warehouseRepository.findById(dto.getWarehouseId()).orElseThrow(() -> new EntityNotExistException(
                            "金库不存在"));
            copy.changeWarehouse(warehouse);
        }
        if(dto.getAmount() != null){
            copy.changeAmount(dto.getAmount());
        }
        warehouseService.moveJournal(expense, copy);
        ledgerService.moveJournal(expense, copy);
        journalRepository.save(copy);
    }

    /**
     * 删除支出记录
     *
     * @param dto
     */
    @Override
    public void deleteExpense(DeleteExpenseDTO dto) throws EntityNotExistException {

    }

    /**
     * 删除收入记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    @Override
    public void deleteIncome(DeleteIncomeDTO dto) throws EntityNotExistException {

    }
}
