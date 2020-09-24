package site.minnan.bookkeeping.userinterface.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginVO {

    private String jwtToken;
}
