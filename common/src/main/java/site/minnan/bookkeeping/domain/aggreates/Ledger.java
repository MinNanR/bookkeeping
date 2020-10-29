package site.minnan.bookkeeping.domain.aggreates;

import cn.hutool.core.util.StrUtil;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "e_ledger")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ledger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ledger_name", columnDefinition = "varchar(20) comment '账本名称'")
    private String ledgerName;

    @Column(name = "user_id", columnDefinition = "int comment '使用者id'")
    private Integer userId;

    @Column(name = "create_time",columnDefinition = "timestamp comment '创建时间'")
    private Timestamp createTime;

    @Column(name = "total_balance", columnDefinition = "decimal(11, 2) comment '总余额'")
    private BigDecimal totalBalance;

    @Column(name = "total_income", columnDefinition = "decimal(11,2) comment '总收入'")
    @Setter
    private BigDecimal totalIncome;

    @Column(name ="total_expense", columnDefinition = "decimal(11,2) comment '总支出'")
    @Setter
    private BigDecimal totalExpense;

    @Column(name = "currency_id", columnDefinition = "int comment '货币id'")
    private Integer currencyId;

    public static Ledger of(Currency currency, Integer userId){
//        String ledgerName = StrUtil.format("我的{}账本", currency.getName());
//        return new Ledger(null, ledgerName,userId,Timestamp.from(Instant.now()), BigDecimal.ZERO, BigDecimal.ZERO,
//                currency.getId());
        return null;
    }

}
