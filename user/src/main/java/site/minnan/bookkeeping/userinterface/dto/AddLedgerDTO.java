package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddLedgerDTO {

    private Integer userId;

    @NotEmpty
    private String ledgerName;
}
