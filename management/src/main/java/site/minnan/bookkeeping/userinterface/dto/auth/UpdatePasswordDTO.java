package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UpdatePasswordDTO implements Serializable {

    private static final long serialVersionUID = 6634012722049164459L;

    Integer id;

    @NotBlank(message = "原密码不能为空")
    String originalPassword;

    @NotBlank(message = "新密码不能为空")
    String newPassword;
}
