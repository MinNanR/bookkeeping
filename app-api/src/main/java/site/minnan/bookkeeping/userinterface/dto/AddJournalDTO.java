package site.minnan.bookkeeping.userinterface.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class AddJournalDTO {
    @NotNull(message = "未指定金库id")
    private Integer warehouseId;

    @NotNull(message = "数量不能为空")
    private BigDecimal amount;

    @NotNull(message = "未指定类型")
    private Integer journalTypeId;

    @NotNull(message = "时间未填写")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp createTime;

    private String remark;
}
