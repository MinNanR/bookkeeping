package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.aplication.service.IncomeTypeApplicationService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.domain.vo.expensetype.ExpenseTypeVO;
import site.minnan.bookkeeping.domain.vo.incometype.IncomeTypeVO;
import site.minnan.bookkeeping.infrastructure.annocation.OperateLog;
import site.minnan.bookkeeping.infrastructure.annocation.Operation;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.incometype.AddIncomeTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.DeleteIncomeTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.GetIncomeTypeListDTO;
import site.minnan.bookkeeping.userinterface.dto.incometype.UpdateIncomeTypeDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("management/incomeType")
public class IncomeTypeController {

    @Autowired
    private IncomeTypeApplicationService incomeTypeApplicationService;

    /**
     * 添加收入类型
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.ADD, module = "收入类型", content = "添加收入类型")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("addIncomeType")
    public ResponseEntity<?> addIncomeType(@RequestBody @Valid AddIncomeTypeDTO dto){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        try {
            incomeTypeApplicationService.addIncomeType(dto);
            return ResponseEntity.success();
        } catch (EntityAlreadyExistException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    /**
     * 查询收入类型
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getIncomeTypeList")
    public ResponseEntity<QueryVO<IncomeTypeVO>> getIncomeTypeList(@RequestBody @Valid GetIncomeTypeListDTO dto){
        QueryVO<IncomeTypeVO> expenseTypeList = incomeTypeApplicationService.getIncomeTypeList(dto);
        return ResponseEntity.success(expenseTypeList);
    }

    /**
     * 修改收入类型
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.UPDATE, module = "收入类型", content = "修改收入类型")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("updateIncomeType")
    public ResponseEntity<?> updateIncomeType(@RequestBody @Valid UpdateIncomeTypeDTO dto){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        try {
            incomeTypeApplicationService.updateIncomeType(dto);
            return ResponseEntity.success();
        } catch (EntityNotExistException | EntityAlreadyExistException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    /**
     * 删除收入类型
     * @param dto
     * @return
     */
    @OperateLog(operation = Operation.DELETE, module = "收入类型", content = "删除收入类型")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("deleteIncomeType")
    public ResponseEntity<?> deleteIncomeType(@RequestBody @Valid DeleteIncomeTypeDTO dto){
        try {
            incomeTypeApplicationService.deleteIncomeType(dto);
            return ResponseEntity.success();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.fail("该id收入类型不存在");
        }
    }
}
