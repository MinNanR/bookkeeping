package site.minnan.bookkeeping.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.minnan.bookkeeping.domain.aggreates.Administrator;
import site.minnan.bookkeeping.domain.repository.AdministratorRepository;
import site.minnan.bookkeeping.domain.service.AdministratorService;
import site.minnan.bookkeeping.infrastructure.exception.UsernameExistException;

import java.util.Optional;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                administratorRepository.findOne((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(
                "username"), username));
        if (administrator.isPresent()) {
            throw new UsernameExistException("用户名已存在");
        }
        password = passwordEncoder.encode(password);
        Administrator newAdmin = Administrator.of(username, password, nickName);
        administratorRepository.save(newAdmin);
    }

}
