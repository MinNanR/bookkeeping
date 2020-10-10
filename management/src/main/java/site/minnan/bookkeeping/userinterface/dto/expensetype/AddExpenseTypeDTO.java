package site.minnan.bookkeeping.userinterface.dto.expensetype;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddExpenseTypeDTO {

    private Integer userId;

    @NotBlank(message = "支出类型名称不能为空")
    private String typeName;
}
