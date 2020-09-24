package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import site.minnan.bookkeeping.aplication.service.AdministratorApplicationService;
import site.minnan.bookkeeping.userinterface.dto.out.AdministratorVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.infrastructure.exception.UserNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;
import site.minnan.bookkeeping.userinterface.dto.in.AddAdministratorDTO;
import site.minnan.bookkeeping.userinterface.dto.in.GetAdministratorListDTO;
import site.minnan.bookkeeping.userinterface.dto.in.UpdateAdministratorDTO;
import site.minnan.bookkeeping.userinterface.dto.in.UpdatePasswordDTO;
import site.minnan.bookkeeping.userinterface.dto.out.GetAdministratorListVO;
import site.minnan.bookkeeping.userinterface.response.ResponseCode;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Controller
@RequestMapping("management/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorApplicationService administratorApplicationService;

    /**
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("createAdministrator")
    public ResponseEntity<?> createAdministrator(@RequestBody AddAdministratorDTO dto) {
        try {
            administratorApplicationService.createAdministrator(dto);
            return ResponseEntity.success();
        } catch (UsernameExistException e) {
            return ResponseEntity.fail(ResponseCode.USERNAME_EXIST);
        }
    }


    /**
     * 更新用户信息
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("updateAdministrator")
    public ResponseEntity<?> updateAdministrator(@RequestBody UpdateAdministratorDTO dto) {
        JwtUser principal = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setId(principal.getId());
        try {
            administratorApplicationService.updateAdministrator(dto);
            return ResponseEntity.success();
        } catch (UserNotExistException e) {
            return ResponseEntity.fail("用户不存在");
        }
    }

    /**
     * 修改密码
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("changePassword")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordDTO dto) {
        try {
            JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            dto.setId(user.getId());
            administratorApplicationService.changePassword(dto);
            return ResponseEntity.success();
        } catch (UserNotExistException | BadCredentialsException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    /**
     * 查询用户
     * @param dto
     * @return
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("getAdministratorList")
    public ResponseEntity<GetAdministratorListVO> getAdministratorList(@RequestBody @Valid GetAdministratorListDTO dto){
        GetAdministratorListVO vo = administratorApplicationService.getAdministratorList(dto);
        return ResponseEntity.success(vo);
    }
}
