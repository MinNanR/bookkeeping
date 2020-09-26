package site.minnan.bookkeeping.domain.vo.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.minnan.bookkeeping.domain.aggreates.Log;

import java.util.List;

@Data
@AllArgsConstructor
public class GetLogListVO {

    private List<LogVO> logList;

    private Long totalCount;
}
