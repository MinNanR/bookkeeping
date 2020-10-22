package site.minnan.bookkeeping.domain.aggreates;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.BeanUtils;
import site.minnan.bookkeeping.domain.entity.JournalType;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * 流水项
 */
@Entity
@Table(name = "e_journal")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Journal implements Cloneable {

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

    @Column(name = "journal_type_id", columnDefinition = "int comment '收入类型id'")
    private Integer journalTypeId;

    @Column(name = "remark", columnDefinition = "text comment '备注'")
    private String remark;

    @Enumerated(value = EnumType.STRING)
    private JournalDirection journalDirection;

    /**
     * 计算金额变化，支出时返回负数，收入时返回正数
     *
     * @return
     */
    public BigDecimal calculate() {
        return journalDirection.calculate().apply(this.amount);
    }

    public BigDecimal correct(BigDecimal newAmount){
        return journalDirection.correct().apply(this.amount, newAmount);
    }

    public Journal copy() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(this);
        return objectMapper.readValue(s, Journal.class);
    }

    public void changeAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void changeWarehouse(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
    }

    public void changeInformation(Optional<JournalType> journalType, Optional<Timestamp> createTime,
                                  Optional<String> remark) {
        journalType.ifPresent(type -> this.journalTypeId = type.getId());
        createTime.ifPresent(time -> this.createTime = time);
        remark.ifPresent(e -> this.remark = e);
    }

    public static Journal of(Integer warehouseId, Integer ledgerId, BigDecimal amount, JournalType journalType,
                             Timestamp createTime, String remark) {
        return new Journal(null, warehouseId, ledgerId, amount, createTime, journalType.getId(), remark,
                journalType.getJournalDirection());
    }
}
