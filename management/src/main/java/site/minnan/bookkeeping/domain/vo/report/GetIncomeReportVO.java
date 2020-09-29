package site.minnan.bookkeeping.domain.vo.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetIncomeReportVO {

    List<IncomeReportVO> list;


}
