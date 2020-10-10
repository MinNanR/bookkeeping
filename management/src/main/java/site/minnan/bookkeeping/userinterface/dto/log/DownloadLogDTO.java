package site.minnan.bookkeeping.userinterface.dto.log;

import lombok.Data;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;

@Data
public class DownloadLogDTO {

    private String username;

    private Operation operation;
}
