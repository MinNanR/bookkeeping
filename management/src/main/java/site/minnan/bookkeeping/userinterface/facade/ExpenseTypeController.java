package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.ExpenseTypeApplicationService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.domain.vo.expensetype.ExpenseTypeVO;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.expensetype.DeleteExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.AddExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.GetExpenseTypeListDTO;
import site.minnan.bookkeeping.userinterface.dto.expensetype.UpdateExpenseTypeDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("management/expenseType")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeApplicationService expenseTypeApplicationService;

    /**
     * 添加支出类型
     *
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.ADD, module = "支出类型", content = "添加支出类型")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("addExpenseType")
    public ResponseEntity<?> addExpenseType(@RequestBody @Valid AddExpenseTypeDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        try {
            expenseTypeApplicationService.addExpenseType(dto);
            return ResponseEntity.success();
        } catch (EntityAlreadyExistException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    /**
     * 获取支出类型
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getExpenseTypeList")
    public ResponseEntity<QueryVO<ExpenseTypeVO>> getExpenseTypeList(@RequestBody GetExpenseTypeListDTO dto) {
        QueryVO<ExpenseTypeVO> expenseTypeList = expenseTypeApplicationService.getExpenseTypeList(dto);
        return ResponseEntity.success(expenseTypeList);
    }

    /**
     * 修改支出类型
     *
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.UPDATE, module = "支出类型", content = "修改支出类型")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("updateExpenseType")
    public ResponseEntity<?> updateExpenseType(@RequestBody @Valid UpdateExpenseTypeDTO dto) throws EntityNotExistException, EntityAlreadyExistException {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        expenseTypeApplicationService.updateExpenseType(dto);
        return ResponseEntity.success();
    }


    @OperateLog(operation = Operation.DELETE, module = "支出类型", content = "删除支出类型")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("deleteExpenseType")
    public ResponseEntity<?> deleteExpenseType(@RequestBody @Valid DeleteExpenseTypeDTO dto) {
        try {
            expenseTypeApplicationService.deleteExpenseType(dto);
            return ResponseEntity.success();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.fail("该id支出类型不存在");
        }
    }
}
