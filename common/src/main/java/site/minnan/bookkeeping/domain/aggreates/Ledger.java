package site.minnan.bookkeeping.domain.aggreates;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Ledger(Integer accountId) {
        this(accountId, DateUtil.thisYear(), DateUtil.thisMonth() + 1);
    }

    public Ledger(Integer accountId, Integer year, Integer month) {
        this.accountId = accountId;
        this.totalExpense = BigDecimal.ZERO;
        this.totalIncome = BigDecimal.ZERO;
        this.year = year;
        this.month = month;
        this.ledgerName = StrUtil.format("{}年{}月账本", this.year, this.month);
    }

    private void cost(Journal journal) {
        totalExpense = totalExpense.add(journal.getAmount());
    }

    private void earn(Journal journal) {
        totalIncome = totalIncome.add(journal.getAmount());
    }

    /**
     * 添加流水
     * @param journal
     */
    public void addJournal(Journal journal) {
        switch (journal.getJournalDirection()) {
            case EXPENSE:
                cost(journal);
                break;
            case INCOME:
                earn(journal);
                break;
        }
    }

    /**
     * 移除流水
     * @param journal
     */
    public void removeJournal(Journal journal) {
        switch (journal.getJournalDirection()) {
            case INCOME:
                totalIncome = totalIncome.subtract(journal.getAmount());
                break;
            case EXPENSE:
                totalExpense = totalExpense.subtract(journal.getAmount());
                break;
        }
    }

    /**
     * 修正流水金额
     * @param source
     * @param target
     */
    public void correctJournal(Journal source, Journal target) {
        switch (source.getJournalDirection()){
            case INCOME:
                totalIncome = totalIncome.subtract(source.getAmount().subtract(target.getAmount()));
                break;
            case EXPENSE:
                totalExpense = totalExpense.subtract(source.getAmount().subtract(target.getAmount()));
                break;
        }
    }
}
