package site.minnan.bookkeeping.domain.repository;

import site.minnan.bookkeeping.domain.aggreates.Administrator;
import org.springframework.data.repository.CrudRepository;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer> {

    Administrator findFirstByUsername(String username);
}
