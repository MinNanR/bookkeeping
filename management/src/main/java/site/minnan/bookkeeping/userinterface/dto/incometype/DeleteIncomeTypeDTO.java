package site.minnan.bookkeeping.userinterface.dto.incometype;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteIncomeTypeDTO {

    @NotNull
    Integer id;
}