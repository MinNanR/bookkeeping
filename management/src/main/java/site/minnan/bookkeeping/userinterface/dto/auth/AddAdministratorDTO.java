package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AddAdministratorDTO implements Serializable {

    private static final long serialVersionUID = -5541350962808032861L;

    @NotBlank(message = "用户名不能为空")
    String username;

    @NotBlank(message = "密码不能为空")
    String password;

    String nickName;
}
