package site.minnan.bookkeeping.userinterface.dto.config;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddLedgerDTO {

    private Integer userId;

    @NotEmpty(message = "账本名称不能为空")
    private String ledgerName;
}
