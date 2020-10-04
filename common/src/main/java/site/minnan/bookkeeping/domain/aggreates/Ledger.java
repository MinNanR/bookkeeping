package site.minnan.bookkeeping.domain.aggreates;

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

    @Column(name = "user_id", columnDefinition = "int comment '用户id'")
    private Integer userId;

    @Column(name = "ledger_name", columnDefinition = "varchar(20) comment '账本名称'")
    private String ledgerName;

    @Column(name = "total_balance", columnDefinition = "decimal(11, 2) comment '账本总余额'")
    private BigDecimal totalBalance;

    @Column(name = "saving_goals", columnDefinition = "decimal(11, 2) comment '存钱目标'")
    private BigDecimal savingGoals;

    @Column(name = "budget", columnDefinition = "decimal(11, 2) comment '花销预算'")
    private BigDecimal budget;

    @Column(name = "cost", columnDefinition = "decimal(11, 2) comment '花销总额'")
    private BigDecimal cost;

    @Column(name = "earn", columnDefinition = "decimal(11, 2) comment '收入总额'")
    private BigDecimal earn;

    @Transient
    private List<Warehouse> warehouseList;

    private Ledger(String ledgerName, Integer userId) {
        this.ledgerName = ledgerName;
        this.userId = userId;
        this.totalBalance = BigDecimal.ZERO;
        this.earn = BigDecimal.ZERO;
        this.cost = BigDecimal.ZERO;
    }

    public static Ledger of(String ledgerName, Integer userId) {
        return new Ledger(ledgerName, userId);
    }

    public void cost(Expense expense) {
        cost = cost.add(expense.getAmount());
        totalBalance = totalBalance.add(expense.calculate());
    }

    public void earn(Income income) {
        earn = earn.add(income.getAmount());
        totalBalance = totalBalance.add(income.calculate());
    }
}
