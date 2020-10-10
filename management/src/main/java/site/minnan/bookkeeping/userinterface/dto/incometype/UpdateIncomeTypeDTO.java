package site.minnan.bookkeeping.userinterface.dto.incometype;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateIncomeTypeDTO {

    @NotNull(message = "未指定修改的收入类型id")
    private Integer id;

    @NotBlank(message = "新名称不能为空")
    private String typeName;

    private Integer userId;
}
