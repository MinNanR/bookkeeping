package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Administrator;
import site.minnan.bookkeeping.domain.repository.AdministratorRepository;
import site.minnan.bookkeeping.domain.repository.SpecificationGenerator;
import site.minnan.bookkeeping.domain.service.AdministratorService;
import site.minnan.bookkeeping.infrastructure.exception.UserNotExistException;
import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;
import site.minnan.bookkeeping.infrastructure.utils.RedisUtil;

import java.util.Optional;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 创建管理员
     *
     * @param username 用户名
     * @param password 密码
     * @param nickName 昵称
     */
    @Override
    public void createAdministrator(String username, String password, String nickName) throws UsernameExistException {
        Optional<Administrator> administrator =
                administratorRepository.findOne(SpecificationGenerator.equal("username", username));
        if (administrator.isPresent()) {
            throw new UsernameExistException("用户名已存在");
        }
        password = passwordEncoder.encode(password);
        Administrator newAdmin = Administrator.of(username, password, nickName);
        administratorRepository.save(newAdmin);
    }

    @Override
    public void changePassword(Integer administratorId, String oldPassword, String newPassword) throws UserNotExistException, BadCredentialsException {
        Optional<Administrator> administrator = administratorRepository.findById(administratorId);
        Administrator admin = administrator.orElseThrow(() -> new UserNotExistException("用户不存在"));
        if (passwordEncoder.matches(oldPassword, admin.getPassword())) {
            admin.changeInformation(Optional.empty(), Optional.of(passwordEncoder.encode(newPassword)));
            administratorRepository.save(admin);
            redisUtil.delete("administrator:" + admin.getUsername());
        } else {
            throw new BadCredentialsException("原密码错误");
        }
    }
}
