package site.minnan.bookkeeping.aplication.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.vo.auth.AdministratorInformationVO;
import site.minnan.bookkeeping.infrastructure.exception.UserNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;
import site.minnan.bookkeeping.userinterface.dto.AddAdministratorDTO;
import site.minnan.bookkeeping.userinterface.dto.UpdateAdministratorDTO;

public interface AdministratorApplicationService extends UserDetailsService {

    AdministratorInformationVO getAdministratorInformationByUsername(String username);

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
}
