package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import site.minnan.bookkeeping.domain.aggreates.Administrator;
import org.springframework.data.repository.CrudRepository;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer>,
        JpaSpecificationExecutor<Administrator> {
}
