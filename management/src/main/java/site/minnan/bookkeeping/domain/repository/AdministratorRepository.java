package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import site.minnan.bookkeeping.domain.aggreates.Administrator;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer>,
        JpaSpecificationExecutor<Administrator> {

    @Query("select new Administrator(admin.id, admin.username) from Administrator admin where admin.id in (:ids)")
    List<Administrator> findAdministratorsById(Iterable<Integer> ids);
}
