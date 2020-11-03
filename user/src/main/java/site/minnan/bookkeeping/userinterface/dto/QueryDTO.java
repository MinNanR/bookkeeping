package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QueryDTO {

    @NotNull(message = "未指定显示数量")
    private Integer pageSize;

    @NotNull(message = "未指定页码")
    private Integer pageIndex;
}
