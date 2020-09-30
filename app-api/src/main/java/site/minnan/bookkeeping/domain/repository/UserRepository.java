package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;

public interface UserRepository extends CrudRepository<CustomUser, Integer>, JpaSpecificationExecutor<CustomUser> {

    CustomUser findFirstByUsername(String username);
}
