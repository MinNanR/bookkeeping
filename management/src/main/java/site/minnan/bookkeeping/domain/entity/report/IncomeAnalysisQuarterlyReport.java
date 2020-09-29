package site.minnan.bookkeeping.domain.entity.report;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.bookkeeping.domain.vo.report.IncomeReportVO;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "report_income_analysis_quarterly")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncomeAnalysisQuarterlyReport extends AbstractIncomeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "year", columnDefinition = "int comment'年份'")
    private Integer year;

    @Column(name = "quarter", columnDefinition = "int comment '季度'")
    private Integer quarter;

    @Column(name = "amount", columnDefinition = "decimal(11, 2) comment '收入总数'")
    private BigDecimal amount;

    @Override
    public IncomeReportVO toIncomeReportVO() {
        return new IncomeReportVO(amount, StrUtil.format("{}年第{}季度", year, quarter));
    }
}
