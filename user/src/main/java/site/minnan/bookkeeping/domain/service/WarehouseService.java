package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.Journal;
import site.minnan.bookkeeping.domain.aggreates.Warehouse;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;

import java.math.BigDecimal;
import java.util.Optional;

public interface WarehouseService {

    /**
     * 创建金库
     *
     * @param accountId
     * @param warehouseNam
     * @param balance
     * @return
     * @throws EntityAlreadyExistException
     */
    Warehouse createWarehouse(Integer accountId, String warehouseNam, Optional<BigDecimal> balance) throws EntityAlreadyExistException;

    /**
     * 修改流水所属金库
     *
     * @param source
     * @param target
     */
    void correctJournal(Journal source, Journal target);

    /**
     * 金库id
     *
     * @param warehouseId
     */
    Integer getAccountId(Integer warehouseId);

    /**
     * 将id转换为名称
     * @param id
     * @return
     */
    String mapIdToName(Integer id);
}
