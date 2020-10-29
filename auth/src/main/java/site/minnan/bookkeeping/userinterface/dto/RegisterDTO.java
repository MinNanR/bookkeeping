package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RegisterDTO {

    @Pattern(regexp = "^1([3456789])\\d{9}$", message = "请提供正确的手机号码")
    private String username;

    @NotNull
    @Length(min = 6, max = 20, message = "密码长度必须为6到20位")
    private String password;

    @Pattern(regexp = "^[0-9]{6}$", message = "验证码必须为6位数字")
    private String verificationCode;
}
