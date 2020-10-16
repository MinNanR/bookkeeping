package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteExpenseDTO {

    @NotNull(message = "未指定要删除的支出记录")
    private Integer id;
}
