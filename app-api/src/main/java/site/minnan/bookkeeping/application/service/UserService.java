package site.minnan.bookkeeping.application.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;
import site.minnan.bookkeeping.domain.vo.auth.UserInformationVO;

public interface UserService extends UserDetailsService {

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    Integer createUser(CustomUser user);

    /**
     * 获取用户信息
     *
     * @param
     * @return
     */
    UserInformationVO getUserInformation();
}
