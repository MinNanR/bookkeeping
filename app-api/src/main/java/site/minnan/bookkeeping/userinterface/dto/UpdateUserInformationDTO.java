package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

@Data
public class UpdateUserInformationDTO {

    private Integer userId;

    private String nickName;

    private String userType;
}
