package site.minnan.bookkeeping.userinterface.dto.expensetype;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteExpenseTypeDTO {

    @NotNull
    private Integer id;
}
