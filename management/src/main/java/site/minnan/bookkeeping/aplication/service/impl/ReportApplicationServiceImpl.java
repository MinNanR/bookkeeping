package site.minnan.bookkeeping.aplication.service.impl;

import cn.hutool.core.date.DateTime;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.ReportApplicationService;
import site.minnan.bookkeeping.domain.entity.report.AbstractIncomeReport;
import site.minnan.bookkeeping.domain.vo.report.GetIncomeReportVO;
import site.minnan.bookkeeping.domain.vo.report.IncomeReportVO;
import site.minnan.bookkeeping.infrastructure.enumeration.IncomeAnalysisRepositoryHolder;
import site.minnan.bookkeeping.userinterface.dto.GetIncomeReportDTO;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportApplicationServiceImpl implements ReportApplicationService {

    @Override
    public GetIncomeReportVO getIncomeReport(GetIncomeReportDTO dto) {
        Optional<Integer> year = Optional.ofNullable(dto.getYear());
        Optional<Integer> month = Optional.ofNullable(dto.getMonth());
        Optional<Integer> day = Optional.ofNullable(dto.getDay());
        Optional<Integer> quarter = Optional.ofNullable(dto.getQuarter());
        JpaSpecificationExecutor<? extends AbstractIncomeReport> repository =
                IncomeAnalysisRepositoryHolder.values()[dto.getReportType()].getRepository();
        List<? extends AbstractIncomeReport> reportList = repository.findAll((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            year.ifPresent(y -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get("year"), y)));
            quarter.ifPresent(q -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get("quarter"), q)));
            month.ifPresent(m -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get("month"), m)));
            day.ifPresent(d -> conjunction.getExpressions().add(criteriaBuilder.equal(root.get("day"), d)));
            return conjunction;
        });
        List<IncomeReportVO> collect =
                reportList.stream().map(AbstractIncomeReport::toIncomeReportVO).collect(Collectors.toList());
        return new GetIncomeReportVO(collect);
    }
}
