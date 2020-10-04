package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.RecordApplicationService;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.AddExpenseDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

@RestController
@RequestMapping("app/record")
public class RecordController {

    @Autowired
    RecordApplicationService recordApplicationService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addExpense")
    public ResponseEntity<?> recordExpense(@RequestBody AddExpenseDTO dto){
        try {
            recordApplicationService.addExpense(dto);
            return ResponseEntity.success();
        } catch (EntityNotExistException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }
}
