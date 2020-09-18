package site.minnan.bookkeeping.application.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;

public interface UserService extends UserDetailsService {

    CustomUser getUserByUsername(String username);

    Integer createUser(CustomUser user);
}
