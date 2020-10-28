package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.AuthUser;

public interface UserRepository extends CrudRepository<AuthUser, Integer>, JpaSpecificationExecutor<AuthUser> {
}
