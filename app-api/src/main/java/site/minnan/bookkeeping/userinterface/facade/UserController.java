package site.minnan.bookkeeping.userinterface.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.application.service.UserService;
import site.minnan.bookkeeping.domain.vo.auth.UserInformationVO;
import site.minnan.bookkeeping.infrastructure.annocation.OperateType;
import site.minnan.bookkeeping.userinterface.dto.LoginDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @OperateType("登录")
    @PostMapping("")
    public ResponseEntity login(@RequestBody LoginDTO dto) throws Exception {
        log.info("用户登录，登录信息：{}", dto.toString());
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("用户被禁用", e);
        } catch (BadCredentialsException e){
            throw new Exception(("用户名或密码错误"));
        }
        UserInformationVO vo = userService.getUserInformation();
        return ResponseEntity.success(vo);
    }
}
