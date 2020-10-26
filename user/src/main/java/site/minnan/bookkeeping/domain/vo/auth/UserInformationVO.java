package site.minnan.bookkeeping.domain.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInformationVO {

    private String jwtToken;
}
