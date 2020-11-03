package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.Currency;

import java.util.List;

public interface CurrencyRepository extends CrudRepository<Currency, Integer>, JpaSpecificationExecutor<Currency> {

    @Query("select new Currency(currency.id, currency.name) from Currency currency where currency.id in (:ids)")
    List<Currency> findAllNameById(Iterable<Integer> ids);
}
