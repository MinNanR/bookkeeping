package site.minnan.bookkeeping.domain.aggreates;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.bookkeeping.domain.entity.IncomeType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

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

    @Column(name = "ledger_id", columnDefinition = "int comment '账本id'")
    private Integer ledgerId;

    @Column(name = "amount", columnDefinition = "decimal(11, 2) comment '数量'")
    private BigDecimal amount;

    @Column(name = "create_time", columnDefinition = "timestamp comment '时间'")
    private Timestamp createTime;

    @Column(name = "income_type_id", columnDefinition = "int comment '收入类型id'")
    private Integer incomeTypeId;

    @Column(name = "remark", columnDefinition = "text comment '备注'")
    private String remark;

    public static Income of(Integer warehouseId, Integer ledgerId, IncomeType incomeType, BigDecimal amount,
                            Timestamp createTime,
                            String remark) {
        return new Income(null, warehouseId, ledgerId, amount, createTime, incomeType.getId(), remark);
    }

    @Override
    public BigDecimal calculate() {
        return amount;
    }

    public void changeInformation(Optional<IncomeType> incomeType, Optional<Timestamp> createTime,
                                  Optional<String> remark) {
        incomeType.ifPresent(type -> this.incomeTypeId = type.getId());
        createTime.ifPresent(time -> this.createTime = time);
        remark.ifPresent(e -> this.remark = e);
    }

    public void changeAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void changeWarehouse(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
    }

    /**
     * 修正记录中的数值
     *
     * @param newAmount
     * @return
     */
    @Override
    public BigDecimal correct(BigDecimal newAmount) {
        return newAmount.subtract(amount);
    }
}
