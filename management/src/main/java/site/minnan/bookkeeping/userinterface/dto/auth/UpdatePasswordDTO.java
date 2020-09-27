package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdatePasswordDTO implements Serializable {

    private static final long serialVersionUID = 6634012722049164459L;

    Integer id;

    @NotBlank
    String originalPassword;

    @NotBlank
    String newPassword;
}
