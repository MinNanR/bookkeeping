package site.minnan.bookkeeping.userinterface.dto.in;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GetAdministratorListDTO {

    private String username;

    @NotNull
    private Integer pageSize;

    @NotNull
    private Integer pageIndex;
}
