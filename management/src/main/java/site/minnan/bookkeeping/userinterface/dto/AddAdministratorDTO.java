package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddAdministratorDTO implements Serializable {

    String username;

    String password;

    String role;
}
