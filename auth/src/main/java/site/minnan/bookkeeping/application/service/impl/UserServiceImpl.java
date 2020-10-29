package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.InvalidVerificationCodeException;
import site.minnan.bookkeeping.infrastructure.util.MessageUtil;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;
import site.minnan.bookkeeping.userinterface.dto.VerificationCodeDTO;
import site.minnan.bookkeeping.userinterface.dto.RegisterDTO;
import site.minnan.bookkeeping.userinterface.dto.VerificationCodeLoginDTO;

import java.time.Duration;
import java.util.Optional;

@Service("AuthUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private RedisUtil redisUtil;

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
        Optional<AuthUser> userOptional = authUserService.getAuthUserByUsername(username);
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

    /**
     * 生成注册验证码
     *
     * @param dto
     */
    @Override
    public void getRegisterVerificationCode(VerificationCodeDTO dto) throws ClientException, JsonProcessingException {
        Optional<AuthUser> optional = authUserService.getAuthUserByUsername(dto.getUsername());
        if (optional.isPresent()) {
            throw new EntityAlreadyExistException("用户名已存在");
        }
        String verificationCode = RandomUtil.randomNumbers(6);
        redisUtil.valueSet("registerVerificationCode:" + verificationCode, dto.getUsername(), Duration.ofMinutes(5));
        messageUtil.sendMessageVerificationCode(dto.getUsername(), verificationCode);
    }

    /**
     * 创建用户
     *
     * @param dto
     */
    @Override
    public LoginVO createUser(RegisterDTO dto) {
        String username = (String) redisUtil.getValue("registerVerificationCode:" + dto.getVerificationCode());
        if (!dto.getUsername().equals(username)) {
            throw new InvalidVerificationCodeException("验证码不正确");
        }
        AuthUser createdUser = authUserService.createUser(dto.getUsername(), dto.getPassword());
        JwtUser jwtUser = JwtUser.of(createdUser.getId(), createdUser.getUsername(), createdUser.getPassword(),
                createdUser.getRole().name(), true);
        String token = jwtUtil.generateToken(jwtUser);
        String jwtToken = "Bearer " + token;
        return new LoginVO(jwtToken, createdUser.getRole().name());
    }

    /**
     * 获取登录用的验证码
     *
     * @param dto
     */
    @Override
    public void getLoginVerificationCode(VerificationCodeDTO dto) throws ClientException, JsonProcessingException {
        Optional<AuthUser> userInDB = authUserService.getUserByUsername(dto.getUsername());
        AuthUser user = userInDB.orElseThrow(() -> new EntityNotExistException("用户名不存在"));
        String verificationCode = RandomUtil.randomNumbers(6);
        redisUtil.valueSet("loginVerificationCode:" + verificationCode, user.getUsername(), Duration.ofMinutes(5));
        messageUtil.sendMessageVerificationCode(dto.getUsername(), verificationCode);
    }

    @Override
    public LoginVO login(VerificationCodeLoginDTO dto) {
        String username = (String) redisUtil.getValue("loginVerificationCode:" + dto.getVerificationCode());
        if (!dto.getUsername().equals(username)) {
            throw new InvalidVerificationCodeException("验证码不正确");
        }
        Optional<AuthUser> userOptional = authUserService.getUserByUsername(dto.getUsername());
        AuthUser user = userOptional.orElseThrow(() -> new EntityNotExistException("用户不存在"));
        JwtUser jwtUser = JwtUser.of(user.getId(), user.getUsername(), user.getPassword(),
                user.getRole().name(), true);
        String token = jwtUtil.generateToken(jwtUser);
        String jwtToken = "Bearer " + token;
        return new LoginVO(jwtToken, user.getRole().name());
    }
}
