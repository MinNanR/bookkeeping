package site.minnan.bookkeeping.application.service;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.vo.UserInformationVO;
import site.minnan.bookkeeping.userinterface.dto.AddAdminDTO;
import site.minnan.bookkeeping.userinterface.dto.LoginCodeDTO;

public interface UserService extends UserDetailsService {

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    UserInformationVO getUserInformationByUsername(String username);

    /**
     * 为用户生成用于登录的验证码
     * @param dto
     */
    void createLoginVerificationCode(LoginCodeDTO dto) throws JsonProcessingException, ClientException;
}
