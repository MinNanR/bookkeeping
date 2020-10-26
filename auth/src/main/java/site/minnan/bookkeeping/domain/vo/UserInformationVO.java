package site.minnan.bookkeeping.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInformationVO {

    private String jwtToken;

    private String role;
}
