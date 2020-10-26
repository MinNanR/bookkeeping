package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;

import java.util.List;

public interface UserRepository extends CrudRepository<CustomUser, Integer>, JpaSpecificationExecutor<CustomUser> {

    @Query("select new CustomUser(user.id, user.username) from CustomUser user where user.id in (:ids) and user.role = " +
            "'ADMIN'")
    List<CustomUser> findAdministratorsById(Iterable<Integer> ids);

    CustomUser findFirstByUsername(String username);
}
