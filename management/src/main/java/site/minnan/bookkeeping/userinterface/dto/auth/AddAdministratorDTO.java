package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AddAdministratorDTO implements Serializable {

    private static final long serialVersionUID = -5541350962808032861L;

    @NotBlank
    String username;

    @NotBlank
    String password;

    String nickName;
}
