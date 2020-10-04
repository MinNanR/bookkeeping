package site.minnan.bookkeeping.domain.aggreates;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "e_income")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Income extends Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "warehouse_id", columnDefinition = "int comment '金库id'")
    private Integer warehouseId;

    @Column(name = "amount", columnDefinition = "decimal(11, 2) comment '数量'")
    private BigDecimal amount;

    @Column(name = "create_time", columnDefinition = "timestamp comment '时间'")
    private Timestamp createTime;

    @Column(name = "income_type_id", columnDefinition = "int comment '收入类型id'")
    private Integer incomeTypeId;

    public Income of(Integer warehouseId, BigDecimal amount) {
        return new Income(null, warehouseId, amount, Timestamp.from(Instant.now()), 0);
    }

    @Override
    public BigDecimal calculate() {
        return amount;
    }
}
