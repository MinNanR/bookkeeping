package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.entity.report.IncomeAnalysisQuarterlyReport;

public interface IncomeAnalysisQuarterlyReportRepository extends CrudRepository<IncomeAnalysisQuarterlyReport,
        Integer>, JpaSpecificationExecutor<IncomeAnalysisQuarterlyReport> {
}
