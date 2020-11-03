package site.minnan.bookkeeping.domain.vo;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class JournalListItemVO {

    private Integer id;

    private BigDecimal amount;

    private String warehouseName;

    private String journalType;

    private String currency;

    private String journalDirection;

    private String journalTime;

    private String remark;

    public static JournalListItemVO assemble(Integer id, BigDecimal amount, String warehouseName, String journalType,
                                             String currency, JournalDirection direction, Timestamp journalTime,
                                             String remark) {
        String journalDirection = direction.name();
        String time = DateUtil.format(journalTime, "yyyy-MM-dd HH:mm");
        return new JournalListItemVO(id, amount, warehouseName, journalType, currency, journalDirection, time, remark);
    }
}
