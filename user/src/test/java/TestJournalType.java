import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.minnan.bookkeeping.UserApplication;
import site.minnan.bookkeeping.domain.service.JournalTypeService;

import java.util.Arrays;
import java.util.Map;

@SpringBootTest(classes = UserApplication.class)
public class TestJournalType {

    @Autowired
    private JournalTypeService service;

    @Test
    public void testSelect(){
        Map<Integer, String> map = service.mapIdToName(Arrays.asList(1, 2));
        System.out.println(map);
    }
}
