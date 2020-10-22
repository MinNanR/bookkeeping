package site.minnan.bookkeeping.domain.aggreates;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "e_warehouse")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "warehouse_name", columnDefinition = "varchar(50) comment '金库名称'")
    private String warehouseName;

    @Column(name = "balance", columnDefinition = "decimal(11, 2) comment '余额'")
    private BigDecimal balance;

    @Column(name = "account_id", columnDefinition = "int comment '账本id'")
    private Integer accountId;

    @Column(name = "create_time", columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    @Transient
    private List<Journal> journalList;

    public static Warehouse of(String warehouseName, BigDecimal balance, Integer accountId) {
        return new Warehouse(null, warehouseName, balance, accountId, Timestamp.from(Instant.now()), null);
    }

    /**
     * 结算流水（添加时使用）
     * @param journal
     */
    public void settleJournal(Journal journal) {
        this.balance = this.balance.add(journal.calculate());
    }

    /**
     * 结算流水（修改时使用）
     * @param source
     * @param target
     */
    public void settleJournal(Journal source, Journal target){
        BigDecimal diff = source.correct(target.getAmount());
        this.balance = this.balance.subtract(diff);
    }

    /**
     * 移除流水
     * @param journal
     */
    public void removeJournal(Journal journal){
        this.balance = this.balance.subtract(journal.calculate());
    }
}
