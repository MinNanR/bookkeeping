package site.minnan.bookkeeping.userinterface.dto.auth;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AddUserDTO {

    @Pattern(regexp = "^1(3|4|5|6|7|8|9)\\d{9}$", message = "手机号码格式错误")
    private String username;
}
