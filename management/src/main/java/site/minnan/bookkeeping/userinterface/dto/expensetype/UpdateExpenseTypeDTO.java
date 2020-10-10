package site.minnan.bookkeeping.userinterface.dto.expensetype;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateExpenseTypeDTO {

    private Integer userId;

    @NotNull(message = "未指定要修改的支出类型id")
    private Integer id;

    @NotBlank(message = "新名称不能为空")
    private String typeName;
}
