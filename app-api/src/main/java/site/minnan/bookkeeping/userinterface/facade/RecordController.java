package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.RecordService;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.AddExpenseDTO;
import site.minnan.bookkeeping.userinterface.dto.AddIncomeDTO;
import site.minnan.bookkeeping.userinterface.dto.ModifyExpenseDTO;
import site.minnan.bookkeeping.userinterface.dto.UpdateExpenseDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("app/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addExpense")
    public ResponseEntity<?> addExpense(@RequestBody @Valid AddExpenseDTO dto){
        try {
            recordService.addExpense(dto);
            return ResponseEntity.success();
        } catch (EntityNotExistException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addIncome")
    public ResponseEntity<?> addIncome(@RequestBody @Valid AddIncomeDTO dto){
        try {
            recordService.addIncome(dto);
            return ResponseEntity.success();
        } catch (EntityNotExistException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    public void modifyExpense(ModifyExpenseDTO dto){

    }
}
