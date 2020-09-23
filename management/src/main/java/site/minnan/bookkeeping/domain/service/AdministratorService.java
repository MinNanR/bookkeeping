package site.minnan.bookkeeping.domain.service;

import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;

public interface AdministratorService {

    /**
     * 创建管理员
     * @param username 用户名
     * @param password 密码
     * @param nickName 昵称
     */
    void createAdministrator(String username, String password, String nickName) throws UsernameExistException;
}
