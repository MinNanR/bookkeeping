package site.minnan.bookkeeping.aplication.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.vo.auth.AdministratorInformationVO;
import site.minnan.bookkeeping.userinterface.dto.AddAdministratorDTO;

public interface AdministratorService extends UserDetailsService {

    AdministratorInformationVO getAdministratorInformationByUsername(String username);

    /**
     * 创建管理员
     * @param dto
     */
    void createAdministrator(AddAdministratorDTO dto);
}
