package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

@Data
public class RegisterDTO {

    private String verificationCode;

    private String username;
}
