package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.entity.ExpenseType;

public interface ExpenseTypeRepository extends CrudRepository<ExpenseType, Integer>,
        JpaSpecificationExecutor<ExpenseType> {
}
