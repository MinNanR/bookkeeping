package site.minnan.bookkeeping.userinterface.facade;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import site.minnan.bookkeeping.application.service.UserService;
import site.minnan.bookkeeping.domain.vo.UserInformationVO;
import site.minnan.bookkeeping.userinterface.dto.LoginCodeDTO;
import site.minnan.bookkeeping.userinterface.dto.PasswordLoginDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid PasswordLoginDTO dto) throws Exception {
        log.info("用户登录，登录信息：{}", dto.toString());
        try {
            Authentication authentication =
                    manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new Exception("用户被禁用", e);
        } catch (BadCredentialsException e) {
            throw new Exception(("用户名或密码错误"));
        }
        UserInformationVO vo = userService.getUserInformationByUsername(dto.getUsername());
        return ResponseEntity.success(vo);
    }

    @PostMapping("/getVerificationCode")
    public ResponseEntity<?> getLoginVerificationCode(@RequestBody @Valid LoginCodeDTO dto) throws JsonProcessingException, ClientException {
        userService.createLoginVerificationCode(dto);
        return ResponseEntity.success();
    }
}
