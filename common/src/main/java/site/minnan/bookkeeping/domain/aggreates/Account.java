package site.minnan.bookkeeping.domain.aggreates;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Table(name = "e_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", columnDefinition = "int comment '用户id'")
    private Integer userId;

    @Column(name = "total_balance", columnDefinition = "decimal(11,2) comment '总余额'")
    private BigDecimal totalBalance;

    @Column(name = "total_income", columnDefinition = "decimal(11,2) comment '历史总收入'")
    private BigDecimal totalIncome;

    @Column(name = "total_expense", columnDefinition = "decimal(11,2) comment '历史总支出'")
    private BigDecimal totalExpense;

    public static Account of(Integer userId){
        return new Account(null, userId, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public void settle(Income journal) {
        this.totalBalance = this.totalBalance.add(journal.calculate());
        this.totalIncome = this.totalIncome.add(journal.getAmount());
    }

    public void settle(Expense journal){
        this.totalBalance = this.totalBalance.add(journal.calculate());
        this.totalExpense = this.totalExpense.add(journal.getAmount());
    }

    public void addWarehouse(Warehouse warehouse){
        this.totalBalance = this.totalBalance.add(warehouse.getBalance());
    }

    public void modifyExpense(Expense expense, BigDecimal newAmount){
        totalBalance = totalBalance.add(expense.correct(newAmount));
        totalExpense = totalExpense.subtract(expense.correct(newAmount));
    }

    public void modifyIncome(Income income, BigDecimal newAmount){
        totalBalance = totalBalance.add(income.correct(newAmount));
        totalIncome = totalIncome.add(income.correct(newAmount));
    }

}
