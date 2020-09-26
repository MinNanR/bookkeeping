package site.minnan.bookkeeping.aplication.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.aplication.service.AdministratorApplicationService;
import site.minnan.bookkeeping.domain.aggreates.Administrator;
import site.minnan.bookkeeping.domain.repository.AdministratorRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.AdministratorService;
import site.minnan.bookkeeping.domain.vo.auth.AdministratorVO;
import site.minnan.bookkeeping.domain.vo.auth.GetAdministratorListVO;
import site.minnan.bookkeeping.domain.vo.auth.JwtUser;
import site.minnan.bookkeeping.domain.vo.auth.LoginVO;
import site.minnan.bookkeeping.infrastructure.exception.UserNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;
import site.minnan.bookkeeping.infrastructure.utils.JwtUtil;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;
import site.minnan.bookkeeping.userinterface.dto.*;

import javax.persistence.criteria.Predicate;
import java.sql.Timestamp;
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

    /**
     * 登录时获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public LoginVO getAdministratorInformationByUsername(String username) {
        Optional<Administrator> administrator = getAdministratorByUsername(username);
        String token = StrUtil.format("Bearer {}", jwtUtil.generateToken(administrator.get()));
        return new LoginVO(token);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redisUtil.delete(StrUtil.format("administrator:{}", jwtUser.getUsername()));
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
        Administrator administrator = (Administrator) redisUtil.getValue(StrUtil.format("administrator:{}",
                username));
        if (administrator != null) {
            return Optional.of(administrator);
        }
        Optional<Administrator> administratorInDB =
                administratorRepository.findOne(SpecificationGenerator.equal("username", username));
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
        Optional<String> nickName = Optional.ofNullable(dto.getNickName());
        administrator.changeInformation(nickName, Optional.empty());
        administratorRepository.save(administrator);
    }

    /**
     * 修改管理员密码
     *
     * @param dto
     * @throws UserNotExistException
     * @throws BadCredentialsException
     */
    @Override
    public void changePassword(UpdatePasswordDTO dto) throws UserNotExistException, BadCredentialsException {
        administratorService.changePassword(dto.getId(), dto.getOriginalPassword(), dto.getNewPassword());
    }

    /**
     * 获取管理员列表
     *
     * @param dto
     * @return
     */
    @Override
    public GetAdministratorListVO getAdministratorList(GetAdministratorListDTO dto) {
        String username = Optional.ofNullable(dto.getUsername()).orElse("");
        Page<Administrator> administratorPage =
                administratorRepository.findAll(SpecificationGenerator.like("username", username),
                        PageRequest.of(dto.getPageIndex() - 1,
                                dto.getPageSize(), Sort.by(Sort.Direction.DESC, "createTime")));
        List<AdministratorVO> administratorVOList = administratorPage.get()
                .map(AdministratorVO::new)
                .collect(Collectors.toList());
        return new GetAdministratorListVO(administratorVOList, administratorPage.getTotalElements());
    }

    /**
     * 删除管理员（根据id）
     *
     * @param dto
     */
    @Override
    public void deleteAdministrator(DeleteAdministratorDTO dto) throws EmptyResultDataAccessException {
        administratorRepository.deleteById(dto.getId());
    }
}
