package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;

public interface WarehouseRepository extends CrudRepository<Warehouse, Integer>, JpaSpecificationExecutor<Warehouse> {

    @Query("select warehouse.accountId from Warehouse warehouse where warehouse.id = (:id)")
    Integer findAccountIdById(Integer id);
}
