package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.RecordService;
import site.minnan.bookkeeping.domain.aggreates.*;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.entity.IncomeType;
import site.minnan.bookkeeping.domain.repository.*;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.AddExpenseDTO;
import site.minnan.bookkeeping.userinterface.dto.AddIncomeDTO;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 添加支出记录
     *
     * @param dto
     */
    @Override
    public void addExpense(AddExpenseDTO dto) throws EntityNotExistException {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(dto.getWarehouseId());
        Warehouse warehouse = warehouseOptional.orElseThrow(() -> new EntityNotExistException("金库不存在"));
        Optional<Ledger> ledgerOptional = ledgerRepository.findOne((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("accountId"), warehouse.getAccountId()));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("year"), DateUtil.thisYear()));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("month"), DateUtil.thisMonth()));
            return conjunction;
        });
        Optional<Account> accountOptional = accountRepository.findById(warehouse.getAccountId());
        Account account = accountOptional.orElseThrow(() -> new EntityNotExistException("账户不存在"));
        Ledger ledger = ledgerOptional.orElseGet(() -> ledgerService.createLedger(warehouse.getAccountId()));
        Optional<ExpenseType> expenseTypeOptional = expenseTypeRepository.findById(dto.getExpenseTypeId());
        ExpenseType expenseType = expenseTypeOptional.orElseThrow(() -> new EntityNotExistException("支出类型不存在"));
        Expense expense = Expense.of(warehouse.getId(), ledger.getId(), dto.getAmount(), expenseType,
                dto.getCreateTime(), dto.getRemark());
        //记入账号
        account.settle(expense);
        //记入当月账本
        ledger.cost(expense);
        //记入金库
        warehouse.settle(expense);
        accountRepository.save(account);
        ledgerRepository.save(ledger);
        warehouseRepository.save(warehouse);
        expenseRepository.save(expense);
    }

    /**
     * 添加收入记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    @Override
    public void addIncome(AddIncomeDTO dto) throws EntityNotExistException {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(dto.getWarehouseId());
        Warehouse warehouse = warehouseOptional.orElseThrow(() -> new EntityNotExistException("金库不存在"));
        Optional<Ledger> ledgerOptional = ledgerRepository.findOne((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("accountId"), warehouse.getAccountId()));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("year"), DateUtil.thisYear()));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("month"), DateUtil.thisMonth()));
            return conjunction;
        });
        Optional<Account> accountOptional = accountRepository.findById(warehouse.getAccountId());
        Account account = accountOptional.orElseThrow(() -> new EntityNotExistException("账户不存在"));
        Ledger ledger = ledgerOptional.orElseGet(() -> ledgerService.createLedger(warehouse.getAccountId()));
        Optional<IncomeType> incomeTypeOptional = incomeTypeRepository.findById(dto.getIncomeTypeId());
        IncomeType incomeType = incomeTypeOptional.orElseThrow(() -> new EntityNotExistException("收入类型不存在"));
        Income income = Income.of(warehouse.getId(), incomeType, dto.getAmount(), dto.getCreateTime());
        //记入账号
        account.settle(income);
        //记入账本
        ledger.earn(income);
        //记入金库
        warehouse.settle(income);
        //记入流水
        accountRepository.save(account);
        ledgerRepository.save(ledger);
        warehouseRepository.save(warehouse);
        incomeRepository.save(income);
    }
}
