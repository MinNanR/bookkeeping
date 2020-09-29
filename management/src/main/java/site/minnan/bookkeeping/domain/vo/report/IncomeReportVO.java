package site.minnan.bookkeeping.domain.vo.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IncomeReportVO {

    private BigDecimal amount;

    private String time;
}
