package site.minnan.bookkeeping.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;

import java.util.List;

public interface WarehouseRepository extends CrudRepository<Warehouse, Integer>, JpaSpecificationExecutor<Warehouse> {

    @Query("select new Warehouse(warehouse.id, warehouse.warehouseName) from Warehouse warehouse where warehouse.id in (:ids)")
    List<Warehouse> findAllNameById(Iterable<Integer> ids);
}
