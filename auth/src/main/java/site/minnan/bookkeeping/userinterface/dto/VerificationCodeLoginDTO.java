package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class VerificationCodeLoginDTO {

    @Pattern(regexp = "^1([3456789])\\d{9}$", message = "请提供正确的手机号码")
    private String username;

    @Pattern(regexp = "^[0-9]{6}")
    private String verificationCode;
}
