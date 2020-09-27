package site.minnan.bookkeeping.userinterface.dto.expensetype;

import lombok.Data;
import site.minnan.bookkeeping.userinterface.dto.QueryDTO;

@Data
public class GetExpenseTypeListDTO extends QueryDTO {

    private String typeName;
}
