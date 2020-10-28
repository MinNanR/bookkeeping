package site.minnan.bookkeeping.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.UserService;
import site.minnan.bookkeeping.domain.aggreates.AuthUser;
import site.minnan.bookkeeping.domain.entity.JwtUser;
import site.minnan.bookkeeping.domain.service.AuthUserService;
import site.minnan.bookkeeping.domain.vo.auth.LoginVO;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;

import java.util.Optional;

@Service("AuthUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> userOptional = authUserService.getUserByUsername(username);
        AuthUser user = userOptional.orElseThrow(() -> new UsernameNotFoundException("用户名不存在"));
        return JwtUser.of(user.getId(), user.getUsername(), user.getPassword(), user.getRole().name(), true);
    }

    /**
     * 生成登录信息
     *
     * @return
     */
    @Override
    public LoginVO getLoginInformation() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtUtil.generateToken(jwtUser);
        String jwtToken = "Bearer " + token;
        StringBuilder roleBuilder = new StringBuilder();
        for (GrantedAuthority authority : jwtUser.getAuthorities()) {
            roleBuilder.append(authority.getAuthority());
        }
        return new LoginVO(jwtToken, roleBuilder.toString());
    }
}
