package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
public class GetAdministratorListDTO extends QueryDTO{

    private String username;
}
