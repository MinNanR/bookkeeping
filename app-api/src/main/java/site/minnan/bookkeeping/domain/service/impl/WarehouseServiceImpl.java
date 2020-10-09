package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Expense;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.domain.repository.WarehouseRepository;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Override
    public Warehouse createWarehouse(Integer accountId, String warehouseName, Optional<BigDecimal> balance) throws EntityAlreadyExistException {
        Optional<Warehouse> check = warehouseRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("accountId"), accountId));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("warehouseName"), warehouseName));
            return conjunction;
        });
        if (check.isPresent()) {
            throw new EntityAlreadyExistException("同名金库已存在");
        }
        Warehouse warehouse = Warehouse.of(warehouseName, balance.orElse(BigDecimal.ZERO), accountId);
        return warehouseRepository.save(warehouse);

    }
}
