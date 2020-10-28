package site.minnan.bookkeeping.application.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.vo.auth.LoginVO;

public interface UserService extends UserDetailsService {

    /**
     * 生成登录信息
     * @return
     */
    LoginVO getLoginInformation();
}
