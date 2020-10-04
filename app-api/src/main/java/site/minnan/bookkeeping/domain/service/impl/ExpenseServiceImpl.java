package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.minnan.bookkeeping.domain.aggreates.Expense;
import site.minnan.bookkeeping.domain.aggreates.Ledger;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.repository.*;
import site.minnan.bookkeeping.domain.service.ExpenseService;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Override
    @Transactional
    public void addExpense(Integer warehouseId, Integer expenseTypeId, BigDecimal amount) throws EntityNotExistException {
        //查找金库
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);
        Warehouse warehouse = warehouseOptional.orElseThrow(() -> new EntityNotExistException("金库不存在"));
        //查找账本
        Optional<Ledger> ledgerOptional = ledgerRepository.findById(warehouse.getLedgerId());
        Ledger ledger = ledgerOptional.orElseThrow(() -> new EntityNotExistException("未绑定账本"));
        //查找支出类型
        Optional<ExpenseType> expenseTypeOptional = expenseTypeRepository.findById(expenseTypeId);
        ExpenseType expenseType = expenseTypeOptional.orElseThrow(() -> new EntityNotExistException("支出类型不存在"));
        //生成支出记录
        Expense expense = Expense.of(warehouseId, amount, expenseType);
        //结算金库情况
        warehouse.settle(expense);
        //修改花销预算
        ledger.cost(expense);
        expenseRepository.save(expense);
        warehouseRepository.save(warehouse);
        ledgerRepository.save(ledger);
    }
}
