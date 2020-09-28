package site.minnan.bookkeeping.userinterface.dto.expensetype;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateExpenseTypeDTO {

    private Integer userId;

    @NotNull
    private Integer id;

    @NotBlank
    private String typeName;
}
