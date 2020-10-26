package site.minnan.bookkeeping.userinterface.dto.journal;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import site.minnan.bookkeeping.userinterface.dto.QueryDTO;

import java.sql.Timestamp;

@Data
public class GetJournalListDTO extends QueryDTO {

    Integer userId;

    String warehouseId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Timestamp startTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Timestamp endTime;

    Integer journalTypeId;
}
