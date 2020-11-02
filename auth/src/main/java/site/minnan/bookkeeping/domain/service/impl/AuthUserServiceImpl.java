package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.AuthUser;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.repository.UserRepository;
import site.minnan.bookkeeping.domain.service.AuthUserService;
import site.minnan.bookkeeping.infrastructure.enumeration.Role;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private UserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return
     */
    @Override
    @Cacheable(value = "user")
    public Optional<AuthUser> getAuthUserByUsername(String username) {
        return authUserRepository.findOne(SpecificationGenerator.equal("username", username));
    }

    /**
     * 根据用户名查找普通用户
     *
     * @param username
     * @return
     */
    @Override
    @Cacheable(value = "user")
    public Optional<AuthUser> getUserByUsername(String username) {
        return authUserRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("role"), Role.USER));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("username"), username));
            return conjunction;
        });
    }

    /**
     * 创建用户
     *
     * @param username 用户名（手机号码）
     * @param rawPassword （原密码）
     * @return
     */
    @Override
    public AuthUser createUser(String username, String rawPassword) {
        if (getAuthUserByUsername(username).isPresent()) {
            throw new EntityAlreadyExistException("用户名已存在");
        }
        AuthUser newUser = AuthUser.of(username, passwordEncoder.encode(rawPassword), Role.USER);
        return authUserRepository.save(newUser);
    }
}
