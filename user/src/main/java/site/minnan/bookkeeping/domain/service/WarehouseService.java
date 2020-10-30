package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;

public interface WarehouseService {

    /**
     * 创建账户
     *
     * @param dto
     */
    void createWarehouse(AddWarehouseDTO dto);
}
