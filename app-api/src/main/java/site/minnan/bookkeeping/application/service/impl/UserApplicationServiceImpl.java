package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.UserApplicationService;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.repository.UserRepository;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.domain.vo.auth.UserInformationVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.InvalidVerificationCodeException;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;
import site.minnan.bookkeeping.infrastructure.utils.MessageUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;
import site.minnan.bookkeeping.userinterface.dto.AddUserDTO;
import site.minnan.bookkeeping.userinterface.dto.RegisterDTO;

import java.time.Duration;
import java.util.Optional;

@Service("CustomUserService")
public class UserApplicationServiceImpl implements UserApplicationService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MessageUtil messageUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = getUserByUsername(username);
        if (customUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return JwtUser.of(customUser.getId(), customUser.getUsername(), customUser.getPassword(),
                customUser.getRole(), true);
    }

    @Override
    public void createUser(RegisterDTO dto) throws InvalidVerificationCodeException {
        String key = redisUtil.scanOne(StrUtil.format("registerVerificationCode:{}:{}", dto.getUsername(),
                dto.getVerificationCode()));
        if (key == null) {
            throw new InvalidVerificationCodeException("验证码错误");
        }
        String password = StrUtil.sub(dto.getUsername(), -7, -1);
        password = passwordEncoder.encode(password);
        CustomUser newUser = CustomUser.of(dto.getUsername(), password, "USER");
        userRepository.save(newUser);
    }

    private CustomUser getUserByUsername(String username) {
        CustomUser userInformation = (CustomUser) redisUtil.getValue("user:" + username);
        if (userInformation != null) {
            return userInformation;
        }
        CustomUser customUserInDB = userRepository.findFirstByUsername(username);
        if (customUserInDB != null) {
            redisUtil.valueSet("user:" + username, customUserInDB, Duration.ofDays(7));
        }
        return customUserInDB;
    }

    @Override
    public UserInformationVO getUserInformationByUsername(String username) {

        CustomUser user = getUserByUsername(username);
        String token = jwtUtil.generateToken(user);
        return new UserInformationVO(token);
    }

    /**
     * 发送注册验证码
     *
     * @param dto
     */
    @Override
    public void createVerificationCodeForRegister(AddUserDTO dto) throws EntityAlreadyExistException, ClientException
            , JsonProcessingException {
        Optional<CustomUser> userInDB = userRepository.findOne(SpecificationGenerator.equal("username",
                dto.getUsername()));
        if (userInDB.isPresent()) {
            throw new EntityAlreadyExistException("用户名已存在");
        }
        String verificationCode = RandomUtil.randomNumbers(6);
        redisUtil.valueSet(StrUtil.format("registerVerificationCode:{}:{}", dto.getUsername(), verificationCode), 1,
                Duration.ofMinutes(5));
        messageUtil.sendMessage(dto.getUsername());
    }
}
