package site.minnan.bookkeeping.aplication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.ExpenseTypeApplicationService;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.repository.ExpenseTypeRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.ExpenseTypeService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityExistException;
import site.minnan.bookkeeping.userinterface.dto.expensetype.ExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.GetExpenseTypeListDTO;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseTypeApplicationServiceImpl implements ExpenseTypeApplicationService {

    @Autowired
    private ExpenseTypeService expenseTypeService;

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    @Override
    public void addExpenseType(ExpenseTypeDTO dto) throws EntityExistException {
        expenseTypeService.addExpenseType(dto.getTypeName(), dto.getUserId());
    }

    @Override
    public QueryVO<ExpenseType> getExpenseTypeList(GetExpenseTypeListDTO dto) {
        String typeName = Optional.ofNullable(dto.getTypeName()).orElse("");
        Page<ExpenseType> expenseTypePage = expenseTypeRepository.findAll(SpecificationGenerator.like("typeName",
                typeName), PageRequest.of(dto.getPageIndex() - 1, dto.getPageSize(), Sort.by(Sort.Direction.DESC,
                "updateTime")));
        List<ExpenseType> list = expenseTypePage.getContent();
        return new QueryVO<>(list, expenseTypePage.getTotalElements());
    }
}
