package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.domain.repository.WarehouseRepository;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.Arrays;
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

    /**
     * 修改流水所属金库
     *
     * @param source
     * @param target
     */
    @Override
    public void correctJournal(Journal source, Journal target) {
        Warehouse sourceWarehouse, targetWarehouse;
        if (!source.getWarehouseId().equals(target.getWarehouseId())) {
            sourceWarehouse = warehouseRepository.findById(source.getWarehouseId()).get();
            targetWarehouse = warehouseRepository.findById(target.getWarehouseId()).get();
            //移除流水记录
            sourceWarehouse.removeJournal(source);
            //移动到目标金库
            targetWarehouse.settleJournal(source);
        }else {
            sourceWarehouse = warehouseRepository.findById(source.getWarehouseId()).get();
            targetWarehouse = sourceWarehouse;
        }
        //金额有变化时
        if(!source.getAmount().equals(target.getAmount())){
            targetWarehouse.settleJournal(source, target);
        }
        warehouseRepository.saveAll(Arrays.asList(sourceWarehouse, targetWarehouse));
    }

    /**
     * 金库id
     *
     * @param warehouseId
     */
    @Override
    public Integer getAccountId(Integer warehouseId) {
        return warehouseRepository.findAccountIdById(warehouseId);
    }

    /**
     * 将id转换为名称
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable("WarehouseName")
    public String mapIdToName(Integer id) {
        return warehouseRepository.findWarehouseNameById(id).orElse("");
    }
}
