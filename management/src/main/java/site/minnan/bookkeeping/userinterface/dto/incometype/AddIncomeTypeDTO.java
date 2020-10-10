package site.minnan.bookkeeping.userinterface.dto.incometype;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddIncomeTypeDTO {

    private Integer userId;

    @NotBlank(message = "收入类型不能为空")
    private String typeName;
}
