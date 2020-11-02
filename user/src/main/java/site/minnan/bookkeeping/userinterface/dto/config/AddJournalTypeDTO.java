package site.minnan.bookkeeping.userinterface.dto.config;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddJournalTypeDTO {

    @NotEmpty(message = "类型名称不能为空")
    private String typeName;

    private Integer parentId;

    private Integer userId;
}
