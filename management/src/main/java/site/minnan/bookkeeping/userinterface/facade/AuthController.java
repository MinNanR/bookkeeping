package site.minnan.bookkeeping.userinterface.facade;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import site.minnan.bookkeeping.aplication.service.AdministratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.minnan.bookkeeping.domain.vo.auth.AdministratorInformationVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.userinterface.dto.LoginDTO;
import site.minnan.bookkeeping.userinterface.response.ResponseEntity;

@Slf4j
@RestController
@RequestMapping("management/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private AdministratorService administratorService;

    @PostMapping("login")
    public ResponseEntity<AdministratorInformationVO> createAuthenticationToken(@RequestBody LoginDTO dto) throws Exception {
        log.info("用户登录，登录信息：{}", dto.toString());
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        }catch (DisabledException e){
            throw new Exception("用户被禁用", e);
        }catch (BadCredentialsException e){
            throw new Exception("用户名或密码错误", e);
        }
        AdministratorInformationVO vo = administratorService.getAdministratorInformationByUsername(dto.getUsername());
        return ResponseEntity.success(vo);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("test")
    public ResponseEntity<Authentication> getAdministrator(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.success(authentication);
    }
}
