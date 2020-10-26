package site.minnan.bookkeeping.application.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.vo.QueryVO;
import site.minnan.bookkeeping.domain.vo.auth.AdministratorVO;
import site.minnan.bookkeeping.domain.vo.auth.LoginVO;
import site.minnan.bookkeeping.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.bookkeeping.infrastructure.exception.EntityNotExistException;
import site.minnan.bookkeeping.userinterface.dto.auth.*;

public interface AdministratorApplicationService extends UserDetailsService {

    /**
     * 登录时获取用户信息
     *
     * @param username
     * @return
     */
    LoginVO getAdministratorInformationByUsername(String username);

    /**
     * 退出登录
     */
    void logout();


    /**
     * 创建管理员
     *
     * @param dto
     */
    void createAdministrator(AddAdministratorDTO dto) throws EntityAlreadyExistException;

    /**
     * 修改管理员信息
     *
     * @param dto
     */
    void updateAdministrator(UpdateAdministratorDTO dto) throws EntityNotExistException;

    /**
     * 修改管理员密码
     *
     * @param dto
     */
    void changePassword(UpdatePasswordDTO dto) throws EntityNotExistException, BadCredentialsException;

    /**
     * 获取管理员列表
     *
     * @param dto
     * @return
     */
    QueryVO<AdministratorVO> getAdministratorList(GetAdministratorListDTO dto);

    /**
     * 删除管理员（根据id）
     *
     * @param dto
     */
    void deleteAdministrator(DeleteAdministratorDTO dto) throws EmptyResultDataAccessException;

}
