package site.minnan.bookkeeping.userinterface.dto.journal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import site.minnan.bookkeeping.domain.aggreates.Ledger;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class AddJournalDTO {

    @NotNull(message = "未指定账户")
    private Integer warehouseId;

    @NotNull(message = "未指定账本")
    private Integer ledgerId;

    @NotNull(message = "数目未填写")
    private BigDecimal amount;

    @NotNull(message = "流水类型未 填写")
    private Integer journalTypeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+08:00")
    private Timestamp journalTime;

    private String remark;
}
