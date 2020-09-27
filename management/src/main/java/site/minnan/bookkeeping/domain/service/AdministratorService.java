package site.minnan.bookkeeping.domain.service;

import org.springframework.security.authentication.BadCredentialsException;
import site.minnan.bookkeeping.infrastructure.exception.UserNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityExistException;

public interface AdministratorService {

    /**
     * 创建管理员
     * @param username 用户名
     * @param password 密码
     * @param nickName 昵称
     */
    void createAdministrator(String username, String password, String nickName) throws EntityExistException;

    /**
     * 修改密码
     * @param administratorId 管理员id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer administratorId, String oldPassword, String newPassword) throws UserNotExistException, BadCredentialsException;
}
