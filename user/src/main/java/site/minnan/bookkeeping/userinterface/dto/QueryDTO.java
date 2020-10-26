package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QueryDTO {

    @NotNull
    private Integer pageIndex;

    @NotNull
    private Integer pageSize;
}
