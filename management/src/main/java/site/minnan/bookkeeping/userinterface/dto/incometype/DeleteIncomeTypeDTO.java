package site.minnan.bookkeeping.userinterface.dto.incometype;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteIncomeTypeDTO {

    @NotNull(message = "未指定删除的收入类型id")
    Integer id;
}
