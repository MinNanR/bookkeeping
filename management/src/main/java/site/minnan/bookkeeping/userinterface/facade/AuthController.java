package site.minnan.bookkeeping.userinterface.facade;

import cn.hutool.core.lang.Console;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.aplication.service.AdministratorApplicationService;
import site.minnan.bookkeeping.domain.vo.auth.AdministratorInformationVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.infrastructure.exception.UserNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;
import site.minnan.bookkeeping.userinterface.dto.*;
import site.minnan.bookkeeping.userinterface.response.ResponseCode;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("management/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private AdministratorApplicationService administratorApplicationService;

    /**
     * 登录
     *
     * @param dto
     * @return
     * @throws Exception
     */
    @PostMapping("login")
    public ResponseEntity<AdministratorInformationVO> createAuthenticationToken(@RequestBody LoginDTO dto) throws Exception {
        log.info("用户登录，登录信息：{}", dto.toString());
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("用户被禁用", e);
        } catch (BadCredentialsException e) {
            throw new Exception("用户名或密码错误", e);
        }
        AdministratorInformationVO vo =
                administratorApplicationService.getAdministratorInformationByUsername(dto.getUsername());
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("test")
    public ResponseEntity<String> getAdministrator(@RequestBody OptionalDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Console.log(authentication.getPrincipal());
        return ResponseEntity.success(dto.getNickName().orElse("is null"));
    }

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
}
