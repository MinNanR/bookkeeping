package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class UpdateExpenseDTO {

    @NotNull(message = "未指定更新的记录")
    private Integer id;

    private Integer warehouseId;

    private BigDecimal amount;

    private Integer expenseTypeId;

    private Timestamp createTime;
}
