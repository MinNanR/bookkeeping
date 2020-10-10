package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;
import site.minnan.bookkeeping.domain.entity.UserType;

@Data
public class GetUserListDTO extends QueryDTO{

    private String nickName;

    private UserType userType;
}
