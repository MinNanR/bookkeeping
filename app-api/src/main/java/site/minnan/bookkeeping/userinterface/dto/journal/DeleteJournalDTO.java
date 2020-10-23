package site.minnan.bookkeeping.userinterface.dto.journal;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteJournalDTO {

    @NotNull(message = "未指定记录id")
    Integer id;
}
