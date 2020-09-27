package site.minnan.bookkeeping.aplication.service;

import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityExistException;
import site.minnan.bookkeeping.userinterface.dto.expensetype.ExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.GetExpenseTypeListDTO;

public interface ExpenseTypeApplicationService {

    void addExpenseType(ExpenseTypeDTO dto) throws EntityExistException;

    QueryVO<ExpenseType> getExpenseTypeList(GetExpenseTypeListDTO dto);
}
