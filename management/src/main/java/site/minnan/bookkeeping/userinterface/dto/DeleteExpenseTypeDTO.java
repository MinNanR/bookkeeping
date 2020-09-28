package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteExpenseTypeDTO {

    @NotNull
    private Integer id;
}
