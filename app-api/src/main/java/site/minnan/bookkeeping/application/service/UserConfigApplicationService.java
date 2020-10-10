package site.minnan.bookkeeping.application.service;

import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.userinterface.dto.AddWarehouseDTO;

public interface UserConfigApplicationService {

    /**
     * 创建金库
     * @param dto
     * @throws EntityAlreadyExistException
     */
    void createWarehouse(AddWarehouseDTO dto) throws EntityAlreadyExistException;


}
