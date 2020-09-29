package site.minnan.bookkeeping.userinterface.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import site.minnan.bookkeeping.application.service.UserService;
import site.minnan.bookkeeping.domain.vo.auth.UserInformationVO;
import site.minnan.bookkeeping.infrastructure.annocation.OperateType;
import site.minnan.bookkeeping.userinterface.dto.LoginDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

@Slf4j
@RestController
@RequestMapping("app/auth")
public class UserController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @OperateType("登录")
    @PostMapping("login")
    public ResponseEntity<UserInformationVO> createAuthenticationToken(@RequestBody LoginDTO dto) throws Exception {
        log.info("用户登录，登录信息：{}", dto.toString());
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("用户被禁用", e);
        } catch (BadCredentialsException e) {
            throw new Exception(("用户名或密码错误"));
        }
        UserInformationVO vo = userService.getUserInformationByUsername(dto.getUsername());
        return ResponseEntity.success(vo);
    }

    /**
     * 登录验证码
     *
     * @return
     */
    public ResponseEntity<?> getVerificationCodeForRegister(LoginDTO dto){
        return ResponseEntity.success();
    }

}
