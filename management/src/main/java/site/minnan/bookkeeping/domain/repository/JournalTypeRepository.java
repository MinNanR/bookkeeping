package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.entity.JournalType;

public interface JournalTypeRepository extends CrudRepository<JournalType, Integer>,
        JpaSpecificationExecutor<JournalType> {
}
