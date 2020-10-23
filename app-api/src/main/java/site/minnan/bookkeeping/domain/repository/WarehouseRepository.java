package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;

import java.util.Optional;

public interface WarehouseRepository extends CrudRepository<Warehouse, Integer>, JpaSpecificationExecutor<Warehouse> {

    @Query("select warehouse.accountId from Warehouse warehouse where warehouse.id = (:id)")
    Integer findAccountIdById(Integer id);

    @Query("select warehouse.warehouseName from Warehouse warehouse where warehouse.id = (:id)")
    Optional<String> findWarehouseNameById(Integer id);
}
