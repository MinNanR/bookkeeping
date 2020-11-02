package site.minnan.bookkeeping.userinterface.fascade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.JournalApplicationService;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.userinterface.dto.UpdateJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("user/journal")
public class JournalController {

    @Autowired
    private JournalApplicationService service;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addExpense")
    public ResponseEntity<?> addExpense(@RequestBody @Valid AddJournalDTO dto) {
        service.addJournal(dto, JournalDirection.EXPENSE);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addIncome")
    public ResponseEntity<?> addIncome(@RequestBody @Valid AddJournalDTO dto) {
        service.addJournal(dto, JournalDirection.INCOME);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("modifyJournal")
    public ResponseEntity<?> updateJournal(@RequestBody @Valid UpdateJournalDTO dto) throws JsonProcessingException {
        service.modifyJournal(dto);
        return ResponseEntity.success();
    }
}
