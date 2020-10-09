package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;

public interface CustomUserRepository extends CrudRepository<CustomUser, Integer>,
        JpaSpecificationExecutor<CustomUser> {
}
