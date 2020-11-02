package site.minnan.bookkeeping.userinterface.fascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.CustomConfigService;
import site.minnan.bookkeeping.domain.entity.JwtUser;
import site.minnan.bookkeeping.infrastructure.enumeration.JournalDirection;
import site.minnan.bookkeeping.userinterface.dto.config.AddJournalTypeDTO;
import site.minnan.bookkeeping.userinterface.dto.config.AddLedgerDTO;
import site.minnan.bookkeeping.userinterface.dto.config.AddWarehouseDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;
import java.sql.ResultSet;

@RestController
@RequestMapping("user/CustomConfig")
public class CustomConfigController {

    @Autowired
    private CustomConfigService customConfigService;

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("createLedger")
    public ResponseEntity<?> createLedger(@RequestBody @Valid AddLedgerDTO dto){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        customConfigService.createLedger(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("createWarehouse")
    public ResponseEntity<?> createWarehouse(@RequestBody @Valid AddWarehouseDTO dto){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        customConfigService.createWarehouse(dto);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("createExpenseType")
    public ResponseEntity<?> createExpenseType(@RequestBody @Valid AddJournalTypeDTO dto){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        customConfigService.createJournalType(dto, JournalDirection.EXPENSE);
        return ResponseEntity.success();
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("createIncomeType")
    public ResponseEntity<?> createIncomeType(@RequestBody @Valid AddJournalTypeDTO dto){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        customConfigService.createJournalType(dto, JournalDirection.INCOME);
        return ResponseEntity.success();
    }

}
