package site.minnan.bookkeeping.domain.aggreates;

import cn.hutool.core.util.StrUtil;
import lombok.*;
import site.minnan.bookkeeping.infrastructure.interfaces.Statistics;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "e_ledger")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ledger implements Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ledger_name", columnDefinition = "varchar(20) comment '账本名称'")
    private String ledgerName;

    @Column(name = "user_id", columnDefinition = "int comment '使用者id'")
    private Integer userId;

    @Column(name = "create_time", columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    @Column(name = "total_balance", columnDefinition = "decimal(11, 2) comment '总余额'")
    @Setter
    private BigDecimal totalBalance;

    @Column(name = "total_income", columnDefinition = "decimal(11,2) comment '总收入'")
    @Setter
    private BigDecimal totalIncome;

    @Column(name = "total_expense", columnDefinition = "decimal(11,2) comment '总支出'")
    @Setter
    private BigDecimal totalExpense;

    @Column(name = "currency_id", columnDefinition = "int comment '货币id'")
    private Integer currencyId;

    /**
     * 统计项目余额
     *
     * @return
     */
    @Override
    public BigDecimal getBalance() {
        return totalBalance;
    }

    /**
     * 注入统计后的余额
     *
     * @param balance
     */
    @Override
    public void setBalance(BigDecimal balance) {
        this.totalBalance = balance;
    }

    public static Ledger of(String ledgerName, Currency currency, Integer userId) {
        return new Ledger(null, ledgerName, userId, Timestamp.from(Instant.now()), BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, currency.getId());
    }

    /**
     * 根据流水记录结算账本
     *
     * @param journal
     */
    public void settle(Journal journal) {
        journal.getJournalDirection().calculate(this).accept(journal.getAmount());
    }

    /**
     * 根据流水记录修改账本
     *
     * @param source
     * @param target
     */
    public void correct(Journal source, Journal target) {
        source.getJournalDirection().correct(this).accept(source.getAmount(), target.getAmount());
    }

}
