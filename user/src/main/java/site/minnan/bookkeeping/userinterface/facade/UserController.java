package site.minnan.bookkeeping.userinterface.facade;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.minnan.bookkeeping.application.service.UserApplicationService;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.domain.vo.auth.UserInformationVO;
import site.minnan.bookkeeping.infrastructure.annocation.OperateType;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.InvalidVerificationCodeException;
import site.minnan.bookkeeping.userinterface.dto.auth.AddUserDTO;
import site.minnan.bookkeeping.userinterface.dto.auth.LoginDTO;
import site.minnan.bookkeeping.userinterface.dto.auth.RegisterDTO;
import site.minnan.bookkeeping.userinterface.dto.auth.UpdateUserInformationDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserApplicationService userApplicationService;

    /**
     * 注册验证码
     *
     * @return
     */
    @PostMapping("getVerificationCodeForRegister")
    public ResponseEntity<?> getVerificationCodeForRegister(@RequestBody AddUserDTO dto) {
        try {
            userApplicationService.createVerificationCodeForRegister(dto);
            return ResponseEntity.success();
        } catch (EntityAlreadyExistException | ClientException | JsonProcessingException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    /**
     * 创建用户
     *
     * @param dto
     * @return
     */
    @PostMapping("register")
    public ResponseEntity<UserInformationVO> register(@RequestBody @Valid RegisterDTO dto) {
        try {
            UserInformationVO vo = userApplicationService.createUser(dto);
            return ResponseEntity.success(vo);
        } catch (InvalidVerificationCodeException | EntityAlreadyExistException e) {
            return ResponseEntity.fail(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @PostMapping("updateUserInformation")
    public ResponseEntity<?> updateUserInformation(@RequestBody UpdateUserInformationDTO dto){
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        dto.setUserId(jwtUser.getId());
        try {
            userApplicationService.updateUserInformation(dto);
            return ResponseEntity.success();
        } catch (EntityNotExistException e) {
            return ResponseEntity.fail("用户不存在");
        }
    }
}
