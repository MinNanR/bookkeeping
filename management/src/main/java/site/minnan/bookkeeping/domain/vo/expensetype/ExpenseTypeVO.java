package site.minnan.bookkeeping.domain.vo.expensetype;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.minnan.bookkeeping.domain.entity.ExpenseType;

@Data
@AllArgsConstructor
public class ExpenseTypeVO {

    private Integer id;

    private String typeName;

    private String updateTime;

    private String updateUserName;

}
