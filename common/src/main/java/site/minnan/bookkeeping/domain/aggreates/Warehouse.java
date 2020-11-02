package site.minnan.bookkeeping.domain.aggreates;

import lombok.*;
import site.minnan.bookkeeping.infrastructure.enumeration.WarehouseType;
import site.minnan.bookkeeping.infrastructure.interfaces.Statistics;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Entity
@Table(name = "e_warehouse")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Warehouse implements Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "warehouse_name", columnDefinition = "varchar(50) comment '金库名称'")
    private String warehouseName;

    @Column(name = "balance", columnDefinition = "decimal(11, 2) comment '余额'")
    @Setter
    private BigDecimal balance;

    @Column(name = "total_income", columnDefinition = "decimal(11, 2) comment '总收入'")
    @Setter
    private BigDecimal totalIncome;

    @Column(name = "total_expense", columnDefinition = "decimal(11, 2) comment '总支出'")
    @Setter
    private BigDecimal totalExpense;

    @Column(name = "ledger_id", columnDefinition = "int comment '账本id'")
    private Integer ledgerId;

    @Column(name = "currency_id", columnDefinition = "int comment '货币id'")
    private Integer currencyId;

    @Column(name = "user_id", columnDefinition = "int comment '用户id'")
    private Integer userId;

    @Column(name = "warehouse_type", columnDefinition = "varchar(20) comment '一级账户'")
    @Enumerated(value = EnumType.STRING)
    private WarehouseType warehouseType;

    @Column(name = "create_time", columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    public static Warehouse of(String warehouseName, Optional<BigDecimal> balance, WarehouseType type,
                               Currency currency, Integer ledgerId, Integer userId) {
        return new Warehouse(null, warehouseName, balance.orElse(BigDecimal.ZERO), BigDecimal.ZERO, BigDecimal.ZERO,
                ledgerId, currency.getId(), userId, type, Timestamp.from(Instant.now()));
    }

    /**
     * 根据流水记录结算账户
     *
     * @param journal
     */
    public void settleJournal(Journal journal) {
        journal.getJournalDirection().calculate(this).accept(journal.getAmount());
    }

    public void removeJournal(Journal journal){
        journal.getJournalDirection().calculate(this).accept(journal.getAmount().negate());
    }

    public void settleJournal(Journal source, Journal target){
        source.getJournalDirection().correct(this).accept(source.getAmount(), target.getAmount());
    }
}
