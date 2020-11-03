package site.minnan.bookkeeping.userinterface.dto.journal;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class UpdateJournalDTO {

    @NotNull(message = "未指定要修改的记录")
    private Integer id;

    private Integer warehouseId;

    private BigDecimal amount;

    private Integer journalTypeId;

    private Timestamp journalTime;

    private String remark;
}
