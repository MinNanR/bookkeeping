package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.entity.JournalType;

import java.util.Optional;

public interface JournalTypeRepository extends CrudRepository<JournalType, Integer>,
        JpaSpecificationExecutor<JournalType> {

    @Query("select journalType.typeName from JournalType journalType where journalType.id = (:id)")
    Optional<String> findJournalTypeNameById(Integer id);
}
