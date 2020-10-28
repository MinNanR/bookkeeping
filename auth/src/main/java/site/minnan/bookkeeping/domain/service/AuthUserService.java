package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.AuthUser;

import java.util.Optional;

public interface AuthUserService {

    Optional<AuthUser> getUserByUsername(String username);
}
