package site.minnan.bookkeeping.domain.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginVO {

    private String jwtToken;

    private String role;
}
