package site.minnan.bookkeeping.aplication.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.userinterface.dto.in.GetAdministratorListDTO;
import site.minnan.bookkeeping.userinterface.dto.out.GetAdministratorListVO;
import site.minnan.bookkeeping.userinterface.dto.out.LoginVO;
import site.minnan.bookkeeping.infrastructure.exception.UserNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;
import site.minnan.bookkeeping.userinterface.dto.in.AddAdministratorDTO;
import site.minnan.bookkeeping.userinterface.dto.in.UpdateAdministratorDTO;
import site.minnan.bookkeeping.userinterface.dto.in.UpdatePasswordDTO;

public interface AdministratorApplicationService extends UserDetailsService {

    LoginVO getAdministratorInformationByUsername(String username);

    /**
     * 创建管理员
     * @param dto
     */
    void createAdministrator(AddAdministratorDTO dto) throws UsernameExistException;

    /**
     * 修改管理员信息
     * @param dto
     */
    void updateAdministrator(UpdateAdministratorDTO dto) throws UserNotExistException;

    /**
     * 修改管理员密码
     * @param dto
     */
    void changePassword(UpdatePasswordDTO dto)throws UserNotExistException, BadCredentialsException;

    /**
     * 获取管理员列表
     * @param dto
     * @return
     */
    GetAdministratorListVO getAdministratorList(GetAdministratorListDTO dto);
}
