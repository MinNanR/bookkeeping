package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.domain.aggreates.AuthUser;

import java.util.Optional;

public interface AuthUserService {

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return
     */
    Optional<AuthUser> getUserByUsername(String username);

    /**
     * 创建用户
     * @param username 用户名（手机号码）
     * @param rawPassword （原密码）
     * @return
     */
    AuthUser createUser(String username, String rawPassword);
}
