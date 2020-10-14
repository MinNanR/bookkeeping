package site.minnan.bookkeeping.userinterface.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ModifyIncomeDTO {

    @NotNull
    private Integer id;

    private Integer warehouseId;

    private BigDecimal amount;

    private Integer incomeTypeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp createTime;

    private String remark;
}
