package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddUserDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String nickName;
}
