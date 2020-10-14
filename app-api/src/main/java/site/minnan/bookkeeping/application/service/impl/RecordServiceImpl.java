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
import site.minnan.bookkeeping.userinterface.dto.ModifyExpenseDTO;
import site.minnan.bookkeeping.userinterface.dto.ModifyIncomeDTO;

import javax.persistence.EntityExistsException;
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
        Income income = Income.of(warehouse.getId(), incomeType, dto.getAmount(), dto.getCreateTime(), dto.getRemark());
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

    /**
     * 修改支出记录
     *
     * @param dto
     * @throws EntityNotExistException
     */
    @Override
    public void modifyExpense(ModifyExpenseDTO dto) throws EntityNotExistException {
        //从仓库中获取支出记录
        Optional<Expense> expenseOptional = expenseRepository.findById(dto.getId());
        Expense expense = expenseOptional.orElseThrow(() -> new EntityNotExistException("支出记录不存在"));
        //修改支出类型
        Optional<ExpenseType> expenseType =
                Optional.ofNullable(dto.getExpenseTypeId()).map(expenseTypeId -> expenseTypeRepository.findById(expenseTypeId)
                        .orElseThrow(() -> new EntityExistsException("支出类型不存在")));
        //修改创建时间
        Optional<Timestamp> creatTime = Optional.ofNullable(dto.getCreateTime());
        //修改备注
        Optional<String> remark = Optional.ofNullable(dto.getRemark());
        expense.changeInformation(expenseType, creatTime, remark);
        Optional<BigDecimal> amountOptional = Optional.ofNullable(dto.getAmount());
        //需要修改金额
        if (amountOptional.isPresent() && amountOptional.get().compareTo(expense.getAmount()) != 0) {
            BigDecimal targetAmount = amountOptional.get();
            BigDecimal originalAmount = expense.getAmount();
            //修改仓库
            Warehouse originalWarehouse = warehouseRepository.findById(expense.getWarehouseId()).get();
            Optional<Integer> warehouseIdOptional = Optional.ofNullable(dto.getWarehouseId());
            if (warehouseIdOptional.isPresent()) {
                Integer warehouseId = warehouseIdOptional.get();
                Warehouse targetWarehouse =
                        warehouseRepository.findById(warehouseId).orElseThrow(() -> new EntityExistsException("金库不存在"));
                //转移金库
                originalWarehouse.removeExpense(originalAmount);
                targetWarehouse.settle(expense);
                warehouseRepository.save(originalWarehouse);
                warehouseRepository.save(targetWarehouse);
                expense.changeWarehouse(targetWarehouse);
            } else {
                originalWarehouse.modifyExpense(expense, targetAmount);
                warehouseRepository.save(originalWarehouse);
            }
            //修正账户中的余额
            Account account = accountRepository.findById(originalWarehouse.getAccountId()).get();
            account.modifyExpense(expense, targetAmount);
            accountRepository.save(account);
            //修正账本中的记录
            Ledger ledger = ledgerRepository.findById(expense.getLedgerId()).get();
            ledger.modifyCost(expense, targetAmount);
            ledgerRepository.save(ledger);
            expense.changeAmount(targetAmount);
        } else {
            Optional<Integer> warehouseIdOptional = Optional.ofNullable(dto.getWarehouseId());
            if (warehouseIdOptional.isPresent()) {
                Warehouse originalWarehouse = warehouseRepository.findById(expense.getWarehouseId()).get();
                Integer warehouseId = warehouseIdOptional.get();
                Warehouse targetWarehouse =
                        warehouseRepository.findById(warehouseId).orElseThrow(() -> new EntityExistsException("金库不存在"));
                //转移金库
                originalWarehouse.removeExpense(expense.getAmount());
                targetWarehouse.settle(expense);
                warehouseRepository.save(originalWarehouse);
                warehouseRepository.save(targetWarehouse);
                expense.changeWarehouse(targetWarehouse);
            }
        }
        expenseRepository.save(expense);
    }

    @Override
    public void modifyIncome(ModifyIncomeDTO dto) throws EntityNotExistException {
    }
}
