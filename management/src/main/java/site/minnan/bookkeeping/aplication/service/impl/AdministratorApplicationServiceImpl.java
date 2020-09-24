package site.minnan.bookkeeping.aplication.service.impl;

import com.sun.javafx.binding.BidirectionalBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.AdministratorApplicationService;
import site.minnan.bookkeeping.domain.aggreates.Administrator;
import site.minnan.bookkeeping.domain.repository.AdministratorRepository;
import site.minnan.bookkeeping.domain.service.AdministratorService;
import site.minnan.bookkeeping.userinterface.dto.in.GetAdministratorListDTO;
import site.minnan.bookkeeping.userinterface.dto.out.AdministratorVO;
import site.minnan.bookkeeping.userinterface.dto.out.GetAdministratorListVO;
import site.minnan.bookkeeping.userinterface.dto.out.LoginVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.infrastructure.exception.UserNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;
import site.minnan.bookkeeping.userinterface.dto.in.AddAdministratorDTO;
import site.minnan.bookkeeping.userinterface.dto.in.UpdateAdministratorDTO;
import site.minnan.bookkeeping.userinterface.dto.in.UpdatePasswordDTO;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("AdministratorApplicationService")
public class AdministratorApplicationServiceImpl implements AdministratorApplicationService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private AdministratorService administratorService;

    /**
     * 根据用户名查找管理员
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator administrator =
                getAdministratorByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户名不存在"));
        return JwtUser.of(administrator.getId(), administrator.getUsername(), administrator.getPassword(),
                administrator.getRole(), true);
    }

    @Override
    public LoginVO getAdministratorInformationByUsername(String username) {
        Optional<Administrator> administrator = getAdministratorByUsername(username);
        String token = jwtUtil.generateToken(administrator.get());
        return new LoginVO(token);
    }

    /**
     * 创建管理员
     *
     * @param dto
     */
    @Override
    public void createAdministrator(AddAdministratorDTO dto) throws UsernameExistException {
        administratorService.createAdministrator(dto.getUsername(), dto.getPassword(), dto.getNickName());
    }

    /**
     * 根据用户名在数据库或缓存中查找管理员
     *
     * @param username
     * @return
     */
    public Optional<Administrator> getAdministratorByUsername(String username) {
        Administrator administrator = (Administrator) redisUtil.getValue("administrator:" + username);
        if (administrator != null) {
            return Optional.of(administrator);
        }
        Optional<Administrator> administratorInDB =
                administratorRepository.findOne((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("username"), username));
        administratorInDB.ifPresent(value -> redisUtil.valueSet("administrator:" + username, value,
                Duration.ofDays(1)));
        return administratorInDB;
    }

    /**
     * 修改管理员信息
     *
     * @param dto
     */
    @Override
    public void updateAdministrator(UpdateAdministratorDTO dto) throws UserNotExistException {
        Optional<Administrator> administratorInDB = administratorRepository.findById(dto.getId());
        Administrator administrator = administratorInDB.orElseThrow(UserNotExistException::new);
        administrator.changeInformation(dto.getNickName(), Optional.empty());
        administratorRepository.save(administrator);
    }

    @Override
    public void changePassword(UpdatePasswordDTO dto) throws UserNotExistException, BadCredentialsException {
        administratorService.changePassword(dto.getId(), dto.getOldPassword(), dto.getNewPassword());
    }

    /**
     * 获取管理员列表
     *
     * @param dto
     * @return
     */
    @Override
    public GetAdministratorListVO getAdministratorList(GetAdministratorListDTO dto) {
        Page<Administrator> administratorPage = administratorRepository.findAll(((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("username"), dto.getUsername())), PageRequest.of(dto.getPageIndex(),
                dto.getPageSize(), Sort.by(Sort.Direction.DESC, "createTime")));
        List<AdministratorVO> administratorVOList = administratorPage.get()
                .map(AdministratorVO::new)
                .collect(Collectors.toList());
        return new GetAdministratorListVO(administratorVOList, administratorPage.getTotalElements());
    }
}
