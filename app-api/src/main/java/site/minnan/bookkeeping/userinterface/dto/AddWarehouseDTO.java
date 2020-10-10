package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AddWarehouseDTO {

    Integer userId;

    @NotBlank(message = "名称不能为空")
    String warehouseName;

    BigDecimal balance;

}
