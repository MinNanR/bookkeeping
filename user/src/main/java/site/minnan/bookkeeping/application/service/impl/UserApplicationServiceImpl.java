package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.UserApplicationService;
import site.minnan.bookkeeping.domain.aggreates.Account;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;
import site.minnan.bookkeeping.domain.repository.AccountRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.repository.UserRepository;
import site.minnan.bookkeeping.domain.service.LedgerService;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.domain.vo.auth.UserInformationVO;
import site.minnan.bookkeeping.infrastructure.enumeration.Role;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.InvalidVerificationCodeException;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;
import site.minnan.bookkeeping.infrastructure.utils.MessageUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;
import site.minnan.bookkeeping.userinterface.dto.auth.AddUserDTO;
import site.minnan.bookkeeping.userinterface.dto.auth.RegisterDTO;
import site.minnan.bookkeeping.userinterface.dto.auth.UpdateUserInformationDTO;

import java.time.Duration;
import java.util.Optional;
import java.util.StringJoiner;

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
    private AccountRepository accountRepository;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser customUser = getUserByUsername(username);
        if (customUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return JwtUser.of(customUser.getId(), customUser.getUsername(), customUser.getPassword(),
                customUser.getRole().name(), true);
    }

    @Override
    public UserInformationVO createUser(RegisterDTO dto) throws InvalidVerificationCodeException, EntityAlreadyExistException {
        String key = redisUtil.scanOne(StrUtil.format("registerVerificationCode:{}:{}", dto.getUsername(),
                dto.getVerificationCode()));
        if (key == null) {
            throw new InvalidVerificationCodeException("验证码错误");
        }
        Optional<CustomUser> userInDB = userRepository.findOne(SpecificationGenerator.equal("username",
                dto.getUsername()));
        if (userInDB.isPresent()) {
            throw new EntityAlreadyExistException("用户名已存在");
        }
        String password = StrUtil.sub(dto.getUsername(), 5 ,11);
        password = passwordEncoder.encode(password);
        CustomUser newUser = CustomUser.of(dto.getUsername(), password, Role.USER);
        CustomUser savedUser = userRepository.save(newUser);
        Account account = Account.of(savedUser.getId());
        Account savedAccount = accountRepository.save(account);
        ledgerService.createLedger(savedAccount.getId());
        JwtUser jwtUser = JwtUser.of(savedUser.getId(), savedUser.getUsername(), savedUser.getPassword(),
                savedUser.getRole().name(), true);
        String token = StrUtil.format("Bearer {}", jwtUtil.generateToken(jwtUser));
        return new UserInformationVO(token);
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
        messageUtil.sendMessageVerificationCode(dto.getUsername(), verificationCode);
    }

    /**
     * 更新用户信息
     *
     * @param dto
     */
    @Override
    public void updateUserInformation(UpdateUserInformationDTO dto) throws EntityNotExistException {
        Optional<CustomUser> userOptional = userRepository.findById(dto.getUserId());
        CustomUser user = userOptional.orElseThrow(() -> new EntityNotExistException("用户不存在"));
        user.changeName(Optional.ofNullable(dto.getNickName()));
        user.changeUserType(Optional.ofNullable(dto.getUserType()));
        userRepository.save(user);
    }
}
