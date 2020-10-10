package site.minnan.bookkeeping.userinterface.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ModifyExpenseDTO {

    @NotNull(message = "未指定要修改的支出记录")
    private Integer id;

    private Integer warehouseId;

    private BigDecimal amount;

    private Integer expenseTypeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Timestamp creatTime;
}
