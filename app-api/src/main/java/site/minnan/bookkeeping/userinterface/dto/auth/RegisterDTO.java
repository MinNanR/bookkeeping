package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterDTO {

    @Pattern(regexp = "^[0-9]{6}$", message = "验证码必须是6位数字")
    private String verificationCode;

    @NotBlank(message = "手机号码不能为空")
    private String username;
}
