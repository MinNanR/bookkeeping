package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ToString
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 4601614480588276675L;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
