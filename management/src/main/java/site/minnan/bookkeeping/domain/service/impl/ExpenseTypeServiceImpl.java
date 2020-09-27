package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.repository.ExpenseTypeRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.ExpenseTypeService;
import site.minnan.bookkeeping.infrastructure.exception.EntityExistException;

import java.util.Optional;

@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Override
    public void addExpenseType(String typeName, Integer userId) throws EntityExistException {
        Optional<ExpenseType> expenseType = expenseTypeRepository.findOne(SpecificationGenerator.equal("typeName",
                typeName));
        if (expenseType.isPresent()) {
            throw new EntityExistException("该名称已存在");
        }
        ExpenseType newExpenseType = ExpenseType.of(typeName, userId);
        expenseTypeRepository.save(newExpenseType);
    }
}
