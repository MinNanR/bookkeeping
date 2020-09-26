package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

@Data
public class GetLogListDTO extends QueryDTO{

    private String username;

    private String operation;

}
