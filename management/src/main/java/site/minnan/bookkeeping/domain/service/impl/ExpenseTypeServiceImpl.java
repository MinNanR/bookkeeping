package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.repository.ExpenseTypeRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.ExpenseTypeService;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

import java.util.Optional;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Override
    public void addExpenseType(String typeName, Integer userId) throws EntityAlreadyExistException {
        Optional<ExpenseType> expenseType = expenseTypeRepository.findOne(SpecificationGenerator.equal("typeName",
                typeName));
        if (expenseType.isPresent()) {
            throw new EntityAlreadyExistException("该名称已存在");
        }
        ExpenseType newExpenseType = ExpenseType.of(typeName, userId);
        expenseTypeRepository.save(newExpenseType);
    }

    /**
     * 修改类型名称
     *
     * @param id
     * @param newTypeName
     * @param userId
     * @throws EntityAlreadyExistException 支出类型名称已存在
     */
    @Override
    public void changeExpenseTypeName(Integer id, String newTypeName, Integer userId) throws EntityAlreadyExistException
            ,EntityNotExistException {
        Optional<ExpenseType> expenseTypeInDB = expenseTypeRepository.findById(id);
        ExpenseType expenseType = expenseTypeInDB.orElseThrow(() -> new EntityNotExistException("该id支出类型不存在"));
        Optional<ExpenseType> findByTypeName = expenseTypeRepository.findOne(SpecificationGenerator.equal("typeName",
                newTypeName));
        if (findByTypeName.isPresent()) {
            throw new EntityAlreadyExistException("该名称支出类型已存在");
        }
        expenseType.changeTypeName(newTypeName, userId);
        expenseTypeRepository.save(expenseType);
    }
}
