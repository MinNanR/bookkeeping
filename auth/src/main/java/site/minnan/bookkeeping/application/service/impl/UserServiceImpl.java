package site.minnan.bookkeeping.application.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.message.ObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.UserService;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;
import site.minnan.bookkeeping.domain.entity.JwtUser;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.repository.UserRepository;
import site.minnan.bookkeeping.domain.vo.UserInformationVO;
import site.minnan.bookkeeping.infrastructure.enumeration.Role;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.infrastructure.util.MessageUtil;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;
import site.minnan.bookkeeping.userinterface.dto.AddAdminDTO;
import site.minnan.bookkeeping.userinterface.dto.LoginCodeDTO;

import javax.persistence.criteria.Predicate;
import java.time.Duration;
import java.util.Optional;
import java.util.StringJoiner;

@Service(value = "CustomUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageUtil messageUtil;

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
        CustomUser customUser = getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户名不存在"));
        return JwtUser.of(customUser.getId(), customUser.getUsername(), customUser.getPassword(),
                customUser.getRole().name(), true);
    }


    /**
     * 根据用户名在缓存或数据库中获取用户
     *
     * @param username
     * @return
     */
    private Optional<CustomUser> getUserByUsername(String username) {
        CustomUser administrator = (CustomUser) redisUtil.getValue(StrUtil.format("user:{}",
                username));
        if (administrator != null) {
            return Optional.of(administrator);
        }
        Optional<CustomUser> administratorInDB =
                userRepository.findOne(SpecificationGenerator.equal("username", username));
        administratorInDB.ifPresent(value -> redisUtil.valueSet("user:" + username, value,
                Duration.ofDays(1)));
        return administratorInDB;
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public UserInformationVO getUserInformationByUsername(String username) {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = StrUtil.format("Bearer {}", jwtUtil.generateToken(user));
        StringJoiner joiner = new StringJoiner(",");
        for (GrantedAuthority authority : user.getAuthorities()) {
            joiner.add(authority.getAuthority());
        }
        return new UserInformationVO(token, joiner.toString().toLowerCase());
    }

    @Override
    public void createLoginVerificationCode(LoginCodeDTO dto) throws JsonProcessingException, ClientException {
        Optional<CustomUser> userInDB = userRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("username"), dto.getUsername()));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("role"), Role.USER));
            return conjunction;
        });
        CustomUser user = userInDB.orElseThrow(() -> new EntityNotExistException("用户不存在"));
        String verificationCode = RandomUtil.randomNumbers(6);
        redisUtil.putBeanAsJsonString(StrUtil.format("loginVerificationCode:{}:{}", dto.getUsername(), verificationCode), user,
                new ObjectMapper(), Duration.ofMinutes(5));
        messageUtil.sendMessageVerificationCode(dto.getUsername(), verificationCode);
    }
}
