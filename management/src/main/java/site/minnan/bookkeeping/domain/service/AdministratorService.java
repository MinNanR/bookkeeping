package site.minnan.bookkeeping.domain.service;

import org.springframework.security.authentication.BadCredentialsException;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;

import java.util.Collection;
import java.util.Map;

public interface AdministratorService {

    /**
     * 创建管理员
     *
     * @param username 用户名
     * @param password 密码
     * @param nickName 昵称
     */
    void createAdministrator(String username, String password, String nickName) throws EntityAlreadyExistException;

    /**
     * 修改密码
     *
     * @param administratorId 管理员id
     * @param oldPassword     旧密码
     * @param newPassword     新密码
     */
    void changePassword(Integer administratorId, String oldPassword, String newPassword) throws EntityNotExistException
            , BadCredentialsException;

    /**
     * 将用户id映射成用户名
     *
     * @param ids
     * @return
     */
    Map<Integer, String> mapAdministratorIdToUsername(Collection<Integer> ids);
}
