package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.JournalType;

import java.util.List;

public interface JournalTypeRepository extends CrudRepository<JournalType, Integer>, JpaSpecificationExecutor<JournalType> {

    @Query("select new JournalType(journalType.id, journalType.typeName) from JournalType journalType where journalType.id in " +
            "(:ids)")
    List<JournalType> findAllNameById(Iterable<Integer> ids);
}
