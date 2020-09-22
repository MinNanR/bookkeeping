package site.minnan.bookkeeping.aplication.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.AdministratorService;
import site.minnan.bookkeeping.domain.aggreates.Administrator;
import site.minnan.bookkeeping.domain.repository.AdministratorRepository;
import site.minnan.bookkeeping.domain.vo.auth.AdministratorInformationVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;
import site.minnan.bookkeeping.userinterface.dto.AddAdministratorDTO;

import java.time.Duration;

@Service("AdministratorService")
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdministratorRepository administratorRepository;



    /**
     * 根据用户名查找管理员
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator administrator = getAdministratorByUsername(username);
        if (administrator == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return JwtUser.of(administrator.getId(), administrator.getUsername(), administrator.getPassword(),
                administrator.getRole(), true);
    }

    @Override
    public AdministratorInformationVO getAdministratorInformationByUsername(String username) {
        Administrator administrator = getAdministratorByUsername(username);
        String token = jwtUtil.generateToken(administrator);
        return new AdministratorInformationVO(token);
    }

    /**
     * 创建管理员
     *
     * @param dto
     */
    @Override
    public void createAdministrator(AddAdministratorDTO dto) {
        String password = passwordEncoder.encode(dto.getPassword());
        Administrator administrator = Administrator.of(dto.getUsername(), password, dto.getRole());
        administratorRepository.save(administrator);
    }

    /**
     * 根据用户名在数据库或缓存中查找管理员
     * @param username
     * @return
     */
    @Cacheable("administrator")
    public Administrator getAdministratorByUsername(String username){
        Administrator administrator = (Administrator) redisUtil.getValue("administrator:" + username);
        if (administrator != null) {
            return administrator;
        }
        Administrator administratorInDB = administratorRepository.findFirstByUsername(username);
        if (administratorInDB != null) {
            redisUtil.valueSet("administrator:" + username, administratorInDB, Duration.ofDays(7));
        }
        return administratorInDB;
    }
}
