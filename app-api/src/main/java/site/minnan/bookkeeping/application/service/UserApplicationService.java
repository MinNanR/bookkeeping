package site.minnan.bookkeeping.application.service;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;
import site.minnan.bookkeeping.domain.vo.auth.UserInformationVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.InvalidVerificationCodeException;
import site.minnan.bookkeeping.userinterface.dto.AddUserDTO;
import site.minnan.bookkeeping.userinterface.dto.RegisterDTO;

public interface UserApplicationService extends UserDetailsService {

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    void createUser(RegisterDTO user) throws InvalidVerificationCodeException, EntityAlreadyExistException;

    /**
     * 获取用户信息
     *
     * @param
     * @param username
     * @return
     */
    UserInformationVO getUserInformationByUsername(String username);

    /**
     * 发送注册验证码
     *
     * @param dto
     */
    void createVerificationCodeForRegister(AddUserDTO dto) throws EntityAlreadyExistException, ClientException, JsonProcessingException;
}
