package site.minnan.bookkeeping.userinterface.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.RecordService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.journal.JournalVO;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.journal.AddJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.DeleteJournalDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.GetJournalListDTO;
import site.minnan.bookkeeping.userinterface.dto.journal.ModifyJournalDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("bookkeeping/record")
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
    @PostMapping("modifyJournal")
    public ResponseEntity<?> modifyJournal(@RequestBody @Valid ModifyJournalDTO dto) throws EntityNotExistException,
            JsonProcessingException {
        recordService.modifyJournal(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("deleteJournal")
    public ResponseEntity<?> deleteJournal(@RequestBody @Valid DeleteJournalDTO dto) throws JsonProcessingException {
        recordService.deleteJournal(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("getJournalList")
    public ResponseEntity<QueryVO<?>> getJournalList(@RequestBody @Valid GetJournalListDTO dto){
        QueryVO<JournalVO> vo = recordService.getJournalList(dto);
        return ResponseEntity.success(vo);
    }

}
