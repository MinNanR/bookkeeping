package site.minnan.bookkeeping.userinterface.dto.expensetype;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ExpenseTypeDTO {

    private Integer userId;

    @NotBlank
    private String typeName;
}
