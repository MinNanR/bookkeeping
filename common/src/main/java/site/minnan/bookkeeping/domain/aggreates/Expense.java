package site.minnan.bookkeeping.domain.aggreates;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.bookkeeping.domain.entity.ExpenseType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * 支出记录
 */
@Entity
@Table(name = "e_expense")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Expense extends Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "warehouse_id", columnDefinition = "int comment '金库id'")
    private Integer warehouseId;

    @Column(name = "amount", columnDefinition = "decimal(11, 2) comment '数量'")
    private BigDecimal amount;

    @Column(name = "create_time", columnDefinition = "timestamp comment '时间'")
    private Timestamp createTime;

    @Column(name = "expense_type_id", columnDefinition = "int comment '支出类型id'")
    private Integer expenseTypeId;

    public static Expense of(Integer warehouseId, BigDecimal amount, ExpenseType expenseType) {
        return new Expense(null, warehouseId, amount, Timestamp.from(Instant.now()), expenseType.getId());
    }

    @Override
    public BigDecimal calculate() {
        return amount.abs().negate();
    }
}
