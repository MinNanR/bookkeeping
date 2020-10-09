package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.entity.IncomeType;
import site.minnan.bookkeeping.domain.repository.IncomeTypeRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.IncomeTypeService;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

import java.util.Optional;

@Service
public class IncomeTypeServiceImpl implements IncomeTypeService {

    @Autowired
    private IncomeTypeRepository incomeTypeRepository;

    /**
     * 添加收入类型
     * @param typeName
     * @param userId
     * @throws EntityAlreadyExistException 名称已存在
     */
    @Override
    public void addIncomeType(String typeName, Integer userId) throws EntityAlreadyExistException {
        Optional<IncomeType> incomeType = incomeTypeRepository.findOne(SpecificationGenerator.equal("typeName",
                typeName));
        if (incomeType.isPresent()) {
            throw new EntityAlreadyExistException("该名称已存在");
        }
        IncomeType newIncomeType = IncomeType.of(typeName, userId);
        incomeTypeRepository.save(newIncomeType);
    }

    /**
     * 修改收入类型
     *
     * @param id
     * @param newTypeName
     * @param userId
     */
    @Override
    public void updateIncomeType(Integer id, String newTypeName, Integer userId) throws EntityNotExistException, EntityAlreadyExistException {
        Optional<IncomeType> incomeTypeInDB = incomeTypeRepository.findById(id);
        IncomeType incomeType = incomeTypeInDB.orElseThrow(() -> new EntityNotExistException("该id收入类型不存在"));
        Optional<IncomeType> findByTypeName = incomeTypeRepository.findOne(SpecificationGenerator.equal("typeName",
                newTypeName));
        if (findByTypeName.isPresent()) {
            throw new EntityAlreadyExistException("该名称收入类型已存在");
        }
        incomeType.changeTypeName(newTypeName, userId);
        incomeTypeRepository.save(incomeType);
    }
}
