package site.minnan.bookkeeping.userinterface.dto.expensetype;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteExpenseTypeDTO {

    @NotNull(message = "未指定删除的支出类型id")
    private Integer id;
}
