package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ToString
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 8611523383383250860L;

    @NotBlank(message = "登录名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
