package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class LoginCodeDTO {

    @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\d{9}$", message = "请提供正确的手机号码")
    private String username;
}
