package site.minnan.bookkeeping.domain.service.impl;

import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Currency;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.domain.repository.CurrencyRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.repository.WarehouseRepository;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.domain.service.WarehouseService;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
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
}
