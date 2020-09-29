package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.entity.report.IncomeAnalysisYearlyReport;

public interface IncomeAnalysisYearlyReportRepository extends CrudRepository<IncomeAnalysisYearlyReport, Integer>,
        JpaSpecificationExecutor<IncomeAnalysisYearlyReport> {
}
