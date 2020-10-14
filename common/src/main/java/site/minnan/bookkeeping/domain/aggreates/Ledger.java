package site.minnan.bookkeeping.domain.aggreates;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.bookkeeping.domain.entity.ExpenseType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "e_ledger")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id", columnDefinition = "int comment '账户id'")
    private Integer accountId;

    @Column(name = "ledger_name", columnDefinition = "varchar(20) comment '账本名称'")
    private String ledgerName;

    @Column(name = "budget", columnDefinition = "decimal(11, 2) comment '花销预算'")
    private BigDecimal budget;

    @Column(name = "total_expense", columnDefinition = "decimal(11, 2) comment '花销总额'")
    private BigDecimal totalExpense;

    @Column(name = "total_income", columnDefinition = "decimal(11, 2) comment '收入总额'")
    private BigDecimal totalIncome;

    @Column(name = "year", columnDefinition = "int comment '所属年份'")
    private Integer year;

    @Column(name = "month", columnDefinition = "int comment '所属月份'")
    private Integer month;

    @Transient
    private List<Journal> journalList;

    public Ledger(Integer accountId){
        this.accountId = accountId;
        this.totalExpense = BigDecimal.ZERO;
        this.totalIncome = BigDecimal.ZERO;
        this.year = DateUtil.thisYear();
        this.month = DateUtil.thisMonth() + 1;
        this.ledgerName = StrUtil.format("{}年{}月账本", this.year, this.month);
    }

    public void cost(Expense expense) {
        totalExpense = totalExpense.add(expense.getAmount());
    }

    public void earn(Income income) {
        totalIncome = totalIncome.add(income.getAmount());
    }

    public void modifyCost(Expense expense, BigDecimal newAmount){
        totalExpense = totalExpense.subtract(expense.correct(newAmount));
    }

    public void modifyEarn(Income income, BigDecimal newAmount){
        totalIncome = totalIncome.add(income.correct(newAmount));
    }
}
