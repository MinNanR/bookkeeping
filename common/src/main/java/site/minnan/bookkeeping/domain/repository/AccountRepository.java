package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.Account;

public interface AccountRepository extends CrudRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    @Query("select account.id from Account account where account.userId = :id")
    Integer findAccountIdFirstByUserId(Integer id);
}
