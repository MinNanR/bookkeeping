import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.minnan.bookkeeping.AuthApplication;
import site.minnan.bookkeeping.domain.aggreates.CustomUser;
import site.minnan.bookkeeping.domain.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = AuthApplication.class)
public class testSelectEnum {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testSelectEnum(){
        List<CustomUser> customUsers = userRepository.findAdministratorsById(Arrays.asList(1,2,3));
        System.out.println(customUsers);
    }
}
