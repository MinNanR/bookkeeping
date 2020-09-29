package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.entity.report.IncomeAnalysisMonthlyReport;

public interface IncomeAnalysisMonthlyReportRepository extends CrudRepository<IncomeAnalysisMonthlyReport,Integer>,
        JpaSpecificationExecutor<IncomeAnalysisMonthlyReport> {
}
