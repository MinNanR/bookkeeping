package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Currency;
import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.domain.repository.CurrencyRepository;
import site.minnan.bookkeeping.domain.repository.WarehouseRepository;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private LedgerService ledgerService;

    /**
     * 创建账户
     *
     * @param dto
     */
    @Override
    public void createWarehouse(AddWarehouseDTO dto) {
        if (warehouseRepository.findOne((root, query, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("warehouseType"), dto.getType()));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("warehouseName"), dto.getWarehouseName()));
            return conjunction;
        }).isPresent()) {
            throw new EntityAlreadyExistException("同名账户已存在");
        }
        Optional<BigDecimal> balance = Optional.ofNullable(dto.getBalance());
        if (balance.isPresent() && !balance.get().equals(BigDecimal.ZERO)) {
            ledgerService.createWarehouse(dto.getLedgerId(), balance.get());
        }
        Currency currency =
                currencyRepository.findById(dto.getCurrencyId()).orElseThrow(() -> new EntityNotExistException("货币存在"));
        Warehouse newWarehouse = Warehouse.of(dto.getWarehouseName(), balance, dto.getType(),
                currency, dto.getLedgerId(), dto.getUserId());
        warehouseRepository.save(newWarehouse);
    }

    /**
     * 修正流水记录
     *
     * @param source
     * @param target
     */
    @Override
    public void correctJournal(Journal source, Journal target) {
        Warehouse sourceWarehouse, targetWarehouse;
        if (!source.getWarehouseId().equals(target.getWarehouseId())) {
            //需要修改账户
            sourceWarehouse = warehouseRepository.findById(source.getWarehouseId()).get();
            targetWarehouse =
                    warehouseRepository.findById(target.getWarehouseId()).orElseThrow(() -> new EntityNotExistException("账户不存在"));
            sourceWarehouse.removeJournal(source);
            targetWarehouse.settleJournal(source);
        } else {
            //不需要修改账户
            sourceWarehouse = warehouseRepository.findById(source.getWarehouseId()).get();
            targetWarehouse = sourceWarehouse;
        }
        //金额有变化时
        if (!source.getAmount().equals(target.getAmount())) {
            targetWarehouse.settleJournal(source, target);
        }
        warehouseRepository.saveAll(Arrays.asList(sourceWarehouse, targetWarehouse));
    }

    /**
     * 将id映射成对应的名字
     *
     * @param ids
     * @return
     */
    @Override
    public Map<Integer, String> mapIdToWarehouseName(Iterable<Integer> ids) {
        return warehouseRepository.findAllNameById(ids).stream()
                .collect(HashMap::new, (map, warehouse) -> map.put(warehouse.getId(), warehouse.getWarehouseName()),
                        HashMap::putAll);
    }

    /**
     * 将货币id映射为货币名称
     *
     * @param ids
     * @return
     */
    @Override
    public Map<Integer, String> mapIdToCurrencyName(Iterable<Integer> ids) {
        return currencyRepository.findAllNameById(ids).stream()
                .collect(HashMap::new, (map, currency) -> map.put(currency.getId(),currency.getName()),
                        HashMap::putAll);
    }
}
