package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

@Data
public class GetUserListDTO extends QueryDTO{

    private String nickName;

    private String userType;
}
