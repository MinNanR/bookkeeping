package site.minnan.bookkeeping.userinterface.fascade;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.UserService;
import site.minnan.bookkeeping.domain.vo.auth.LoginVO;
import site.minnan.bookkeeping.userinterface.dto.PasswordLoginDTO;
import site.minnan.bookkeeping.userinterface.dto.VerificationCodeDTO;
import site.minnan.bookkeeping.userinterface.dto.RegisterDTO;
import site.minnan.bookkeeping.userinterface.dto.VerificationCodeLoginDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    @Qualifier(value = "AuthUserService")
    private UserService userService;

    @PostMapping("login/password")
    public ResponseEntity<LoginVO> loginPassword(@RequestBody @Valid PasswordLoginDTO dto) throws AuthenticationException {
        log.info("用户登录，登录信息：{}", dto.toString());
        try {
            Authentication authentication =
                    manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new DisabledException("用户被禁用", e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("用户名或密码错误", e);
        }
        LoginVO vo = userService.getLoginInformation();
        return ResponseEntity.success(vo);
    }

    @PostMapping("login/verificationCode")
    public ResponseEntity<LoginVO> loginVerificationCode(@RequestBody @Valid VerificationCodeLoginDTO dto) {
        LoginVO vo = userService.login(dto);
        return ResponseEntity.success(vo);
    }

    @PostMapping("verificationCode/register")
    public ResponseEntity<?> getRegisterVerificationCode(@RequestBody @Valid VerificationCodeDTO dto) {
        try {
            userService.getRegisterVerificationCode(dto);
            return ResponseEntity.success("短息发送成功，有效期五分钟");
        } catch (ClientException | JsonProcessingException e) {
            return ResponseEntity.fail("短信发送失败");
        }
    }

    @PostMapping("verificationCode/login")
    public ResponseEntity<?> getLoginVerificationCode(@RequestBody @Valid VerificationCodeDTO dto) {
        try {
            userService.getLoginVerificationCode(dto);
            return ResponseEntity.success("短息发送成功，有效期五分钟");
        } catch (ClientException | JsonProcessingException e) {
            return ResponseEntity.fail("短信发送失败");
        }
    }

    @PostMapping("register")
    public ResponseEntity<LoginVO> register(@RequestBody @Valid RegisterDTO dto) {
        LoginVO vo = userService.createUser(dto);
        return ResponseEntity.success(vo);
    }
}
