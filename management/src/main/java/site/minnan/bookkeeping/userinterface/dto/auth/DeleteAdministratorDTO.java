package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteAdministratorDTO {

    @NotNull(message = "id不能为空")
    private Integer id;
}
