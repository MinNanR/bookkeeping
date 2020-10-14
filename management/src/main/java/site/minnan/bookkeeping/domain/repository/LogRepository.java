package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.Log;

import java.sql.Timestamp;

public interface LogRepository extends CrudRepository<Log, Integer>, JpaSpecificationExecutor<Log> {

}
