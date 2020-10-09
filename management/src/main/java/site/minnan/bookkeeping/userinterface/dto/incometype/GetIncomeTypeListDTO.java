package site.minnan.bookkeeping.userinterface.dto.incometype;

import lombok.Data;
import site.minnan.bookkeeping.userinterface.dto.QueryDTO;

@Data
public class GetIncomeTypeListDTO extends QueryDTO {

    private String typeName;
}
