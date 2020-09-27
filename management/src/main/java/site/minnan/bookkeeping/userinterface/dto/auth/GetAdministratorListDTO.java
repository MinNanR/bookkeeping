package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;
import site.minnan.bookkeeping.userinterface.dto.QueryDTO;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
public class GetAdministratorListDTO extends QueryDTO {

    private String username;
}
