package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.aplication.service.ExpenseTypeApplicationService;
import site.minnan.bookkeeping.domain.entity.ExpenseType;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.infrastructure.exception.EntityExistException;
import site.minnan.bookkeeping.userinterface.dto.expensetype.ExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.GetExpenseTypeListDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("management/expenseType")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeApplicationService expenseTypeApplicationService;

    @OperateLog(operation = Operation.ADD, module = "支付类型", content = "添加支付类型")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("addExpenseType")
    public ResponseEntity<?> addExpenseType(@RequestBody @Valid ExpenseTypeDTO dto){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        try {
            expenseTypeApplicationService.addExpenseType(dto);
            return ResponseEntity.success();
        } catch (EntityExistException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getExpenseTypeList")
    public ResponseEntity<QueryVO<ExpenseType>> getExpenseTypeList(@RequestBody @Valid GetExpenseTypeListDTO dto){
        QueryVO<ExpenseType> expenseTypeList = expenseTypeApplicationService.getExpenseTypeList(dto);
        return ResponseEntity.success(expenseTypeList);
    }
}
