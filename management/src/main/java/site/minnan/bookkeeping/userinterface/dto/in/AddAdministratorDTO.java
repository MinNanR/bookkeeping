package site.minnan.bookkeeping.userinterface.dto.in;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddAdministratorDTO implements Serializable {

    private static final long serialVersionUID = -5541350962808032861L;

    String username;

    String password;

    String nickName;
}
