package site.minnan.bookkeeping.userinterface.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddExpenseDTO{

    private Integer warehouseId;

    private BigDecimal amount;

    private Integer expenseTypeId;
}
