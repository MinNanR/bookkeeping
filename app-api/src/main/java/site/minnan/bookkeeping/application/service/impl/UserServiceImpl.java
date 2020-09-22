package site.minnan.bookkeeping.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.application.service.UserService;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;
import site.minnan.bookkeeping.domain.repository.UserRepository;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.domain.vo.auth.UserInformationVO;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;

import java.time.Duration;

@Service("CustomUserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil redisUtil;

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
    public Integer createUser(CustomUser user) {
        CustomUser userInDB = userRepository.findFirstByUsername(user.getUsername());
        if (userInDB != null) {
            return 0;
        }
        user.setRole("USER");
        CustomUser newUser = userRepository.save(user);
        return 1;
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
}
