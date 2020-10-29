package site.minnan.bookkeeping.domain.aggreates;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Entity
@Table(name = "e_warehouse")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "warehouse_name", columnDefinition = "varchar(50) comment '金库名称'")
    private String warehouseName;

    @Column(name = "balance", columnDefinition = "decimal(11, 2) comment '余额'")
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

    @Column(name = "create_time", columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    public static Warehouse of(String warehouseName, Optional<BigDecimal> balance, Integer userId) {
//        return new Warehouse(null, warehouseName, balance.orElse(BigDecimal.ZERO), userId,
//                Timestamp.from(Instant.now()));
        return null;
    }
}
