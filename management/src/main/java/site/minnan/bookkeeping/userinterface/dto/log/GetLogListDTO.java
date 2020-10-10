package site.minnan.bookkeeping.userinterface.dto.log;

import lombok.Data;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.userinterface.dto.QueryDTO;

@Data
public class GetLogListDTO extends QueryDTO {

    private String username;

    private Operation operation;

}
