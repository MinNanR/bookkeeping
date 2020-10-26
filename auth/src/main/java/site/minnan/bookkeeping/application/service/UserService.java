package site.minnan.bookkeeping.application.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.vo.UserInformationVO;
import site.minnan.bookkeeping.userinterface.dto.AddAdminDTO;

public interface UserService extends UserDetailsService {

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    UserInformationVO getUserInformationByUsername(String username);

    /**
     * 添加管理员
     *
     * @param dto
     */
    void addAdmin(AddAdminDTO dto);
}
