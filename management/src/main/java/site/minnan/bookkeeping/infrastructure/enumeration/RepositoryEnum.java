package site.minnan.bookkeeping.infrastructure.enumeration;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import site.minnan.bookkeeping.domain.entity.report.AbstractIncomeReport;

public interface RepositoryEnum {

    JpaSpecificationExecutor<? extends AbstractIncomeReport> getRepository();
}
