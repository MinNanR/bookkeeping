package site.minnan.bookkeeping.domain.vo.incometype;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncomeTypeVO {

    private Integer id;

    private String typeName;

    private String updateTime;

    private String updateUserName;
}
