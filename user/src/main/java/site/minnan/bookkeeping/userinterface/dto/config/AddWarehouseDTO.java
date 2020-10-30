package site.minnan.bookkeeping.userinterface.dto.config;

import lombok.Data;
import site.minnan.bookkeeping.infrastructure.enumeration.WarehouseType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AddWarehouseDTO {

    private Integer userId;

    @NotEmpty(message = "账户不能为空")
    private String warehouseName;

    private BigDecimal balance;

    @NotNull(message = "未指定一级账户")
    private WarehouseType type;

    @NotNull(message = "未指定账本")
    private Integer ledgerId;

    @NotNull(message = "未指定货币类型")
    private Integer currencyId;
}
