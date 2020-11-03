package site.minnan.bookkeeping.userinterface.dto.journal;

import lombok.Data;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.userinterface.dto.QueryDTO;

import javax.validation.constraints.NotNull;

@Data
public class QueryJournalDTO extends QueryDTO {

    private JournalDirection direction;

    @NotNull(message = "未指定账本")
    private Integer ledgerId;

    private Integer warehouseId;
}
