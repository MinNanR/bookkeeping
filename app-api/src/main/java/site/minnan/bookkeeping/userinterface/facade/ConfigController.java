package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.UserConfigApplicationService;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.userinterface.dto.AddWarehouseDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("app/config")
public class ConfigController {

    @Autowired
    private UserConfigApplicationService userConfigApplicationService;


    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("addWarehouse")
    public ResponseEntity<?> createWarehouse(@RequestBody @Valid AddWarehouseDTO dto) throws EntityAlreadyExistException {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        userConfigApplicationService.createWarehouse(dto);
        return ResponseEntity.success();
    }

}
