package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 8611523383383250860L;

    private String username;

    private String password;
}
