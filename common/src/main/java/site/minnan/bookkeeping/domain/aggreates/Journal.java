package site.minnan.bookkeeping.domain.aggreates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@Entity
@Table(name = "e_journal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount", columnDefinition = "decimal(11,2) comment '金额'")
    private BigDecimal amount;

    @Column(name = "warehouse_id", columnDefinition = "int comment '金库id'")
    private Integer warehouseId;

    @Column(name = "leger_id", columnDefinition = "int comment '账本id'")
    private Integer ledgerId;

    @Column(name = "user_id", columnDefinition = "int comment '使用者id'")
    private Integer userId;

    @Column(name = "journal_type_id", columnDefinition = "int comment '类型id'")
    private Integer journalTypeId;

    @Column(name = "currency_id", columnDefinition = "int comment '货币id'")
    private Integer currencyId;

    @Column(name = "journal_direction", columnDefinition = "varchar(20) comment '流水方向'")
    @Enumerated(EnumType.STRING)
    private JournalDirection journalDirection;

    @Column(name = "journal_time", columnDefinition = "timestamp comment '记录产生时间'")
    private Timestamp journalTime;

    @Column(name = "remark", columnDefinition = "varchar(200) comment '备注'")
    private String remark;

    public static Journal of(BigDecimal amount, Warehouse warehouse, Ledger ledger, JournalType journalType,
                             Timestamp journalTime, String remark) {
        return new Journal(null, amount, warehouse.getId(), ledger.getId(), warehouse.getUserId(), journalType.getId(),
                warehouse.getCurrencyId(), journalType.getJournalDirection(), journalTime, remark);
    }

    public Journal copy() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(this);
        return objectMapper.readValue(s, Journal.class);
    }

    public void changeInformation(Optional<JournalType> journalType, Optional<Timestamp> journalTime,
                                  Optional<String> remark){
        journalType.ifPresent(type -> this.journalTypeId = type.getId());
        journalTime.ifPresent(time -> this.journalTime = time);
        remark.ifPresent(r -> this.remark = r);
    }

    public void changeWarehouse(Integer warehouseId){
        this.warehouseId = warehouseId;
    }

    public void changeAmount(BigDecimal amount){
        this.amount = amount;
    }
}

