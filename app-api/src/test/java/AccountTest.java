import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.minnan.bookkeeping.ApiApplication;
import site.minnan.bookkeeping.domain.repository.AccountRepository;

@SpringBootTest(classes = ApiApplication.class)
public class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testQueryId(){
        Integer accountIdFirstByUserId = accountRepository.findAccountIdFirstByUserId(2);
        System.out.println(accountIdFirstByUserId);
    }
}
