package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer>, JpaSpecificationExecutor<Currency> {
}
