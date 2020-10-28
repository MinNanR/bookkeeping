package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.AuthUser;
import site.minnan.bookkeeping.domain.repository.AuthUserRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.AuthUserService;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    @Cacheable("user")
    public Optional<AuthUser> getUserByUsername(String username) {
        return authUserRepository.findOne(SpecificationGenerator.equal("username", username));
    }
}
