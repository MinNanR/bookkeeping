package site.minnan.bookkeeping.application.service;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.vo.auth.LoginVO;
import site.minnan.bookkeeping.userinterface.dto.VerificationCodeDTO;
import site.minnan.bookkeeping.userinterface.dto.RegisterDTO;
import site.minnan.bookkeeping.userinterface.dto.VerificationCodeLoginDTO;

public interface UserService extends UserDetailsService {

    /**
     * 生成登录信息
     *
     * @return
     */
    LoginVO getLoginInformation();

    /**
     * 生成注册验证码
     *
     * @param dto
     */
    void getRegisterVerificationCode(VerificationCodeDTO dto) throws ClientException, JsonProcessingException;

    /**
     * 创建用户
     *
     * @param dto
     */
    LoginVO createUser(RegisterDTO dto);

    /**
     * 获取登录用的验证码
     *
     * @param dto
     */
    void getLoginVerificationCode(VerificationCodeDTO dto) throws ClientException, JsonProcessingException;

    LoginVO login(VerificationCodeLoginDTO dto);
}
