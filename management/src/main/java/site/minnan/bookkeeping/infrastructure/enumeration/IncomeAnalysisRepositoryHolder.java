package site.minnan.bookkeeping.infrastructure.enumeration;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import site.minnan.bookkeeping.domain.entity.report.AbstractIncomeReport;
import site.minnan.bookkeeping.domain.repository.IncomeAnalysisDailyReportRepository;
import site.minnan.bookkeeping.domain.repository.IncomeAnalysisMonthlyReportRepository;
import site.minnan.bookkeeping.domain.repository.IncomeAnalysisQuarterlyReportRepository;
import site.minnan.bookkeeping.domain.repository.IncomeAnalysisYearlyReportRepository;

public enum IncomeAnalysisRepositoryHolder implements RepositoryEnum {


    DAILY {
        @Override
        public JpaSpecificationExecutor<? extends AbstractIncomeReport> getRepository() {
            return SpringUtil.getBean(IncomeAnalysisDailyReportRepository.class);
        }
    },

    MONTHLY {
        @Override
        public JpaSpecificationExecutor<? extends AbstractIncomeReport> getRepository() {
            return SpringUtil.getBean(IncomeAnalysisMonthlyReportRepository.class);
        }
    },

    QUARTERLY{
        @Override
        public JpaSpecificationExecutor<? extends AbstractIncomeReport> getRepository() {
            return SpringUtil.getBean(IncomeAnalysisQuarterlyReportRepository.class);
        }
    },

    YEARLY {
        @Override
        public JpaSpecificationExecutor<? extends AbstractIncomeReport> getRepository() {
            return SpringUtil.getBean(IncomeAnalysisYearlyReportRepository.class);
        }
    };


}
