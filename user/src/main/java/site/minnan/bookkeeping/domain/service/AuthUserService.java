package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.AuthUser;

import java.util.Optional;

public interface AuthUserService {

    /**
     * 根据用户名获取用户（普通用户）
     *
     * @param username
     * @return
     */
    Optional<AuthUser> getUserByUsername(String username);
}
