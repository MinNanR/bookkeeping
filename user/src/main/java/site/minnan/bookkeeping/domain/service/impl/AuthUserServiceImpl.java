package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.AuthUser;
import site.minnan.bookkeeping.domain.repository.UserRepository;
import site.minnan.bookkeeping.domain.service.AuthUserService;
import site.minnan.bookkeeping.infrastructure.enumeration.Role;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据用户名获取用户（普通用户）
     *
     * @param username
     * @return
     */
    @Override
    @Cacheable(value = "user")
    public Optional<AuthUser> getUserByUsername(String username) {
        return userRepository.findOne((root, criteriaQuery, criteriaBuilder) -> {
            Predicate conjunction = criteriaBuilder.conjunction();
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("role"), Role.USER));
            conjunction.getExpressions().add(criteriaBuilder.equal(root.get("username"), username));
            return conjunction;
        });
    }
}
