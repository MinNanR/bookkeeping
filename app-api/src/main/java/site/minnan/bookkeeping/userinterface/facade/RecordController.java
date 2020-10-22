package site.minnan.bookkeeping.userinterface.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.RecordService;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.*;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("app/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addExpense")
    public ResponseEntity<?> addExpense(@RequestBody @Valid AddJournalDTO dto) throws EntityNotExistException {
        recordService.addJournal(dto, JournalDirection.EXPENSE);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addIncome")
    public ResponseEntity<?> addIncome(@RequestBody @Valid AddJournalDTO dto) throws EntityNotExistException {
        recordService.addJournal(dto, JournalDirection.INCOME);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("modifyExpense")
    public ResponseEntity<?> modifyExpense(@RequestBody @Valid ModifyJournalDTO dto) throws EntityNotExistException,
            JsonProcessingException {
        recordService.modifyJournal(dto, JournalDirection.EXPENSE);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("modifyIncome")
    public ResponseEntity<?> modifyIncome(@RequestBody @Valid ModifyJournalDTO dto) throws EntityNotExistException,
            JsonProcessingException {
        recordService.modifyJournal(dto, JournalDirection.INCOME);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("deleteExpense")
    public ResponseEntity<?> deleteExpense(@RequestBody @Valid DeleteExpenseDTO dto) {
        recordService.deleteExpense(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("deleteIncome")
    public ResponseEntity<?> deleteIncome(@RequestBody @Valid DeleteIncomeDTO dto) {
        recordService.deleteIncome(dto);
        return ResponseEntity.success();
    }

}
