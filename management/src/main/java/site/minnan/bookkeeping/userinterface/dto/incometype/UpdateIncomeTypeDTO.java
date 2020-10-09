package site.minnan.bookkeeping.userinterface.dto.incometype;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateIncomeTypeDTO {

    @NotNull
    private Integer id;

    @NotBlank
    private String typeName;

    private Integer userId;
}
