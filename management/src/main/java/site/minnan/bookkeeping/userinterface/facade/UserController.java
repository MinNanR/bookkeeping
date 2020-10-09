package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.aplication.service.UserApplicationService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.user.UserVO;
import site.minnan.bookkeeping.userinterface.dto.GetUserListDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("management/user")
public class UserController {

    @Autowired
    private UserApplicationService userApplicationService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getUserList")
    public ResponseEntity<QueryVO<UserVO>> getUserList(@RequestBody @Valid GetUserListDTO dto){
        QueryVO<UserVO> vo = userApplicationService.getUserList(dto);
        return ResponseEntity.success(vo);
    }
}
