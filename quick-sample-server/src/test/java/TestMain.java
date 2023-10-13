import com.quick.CustomApplication;
import com.quick.entity.CtKeyPoolEntity;
import com.quick.service.CtKeyPoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(classes = CustomApplication.class)
public class TestMain {
    @Autowired
    private CtKeyPoolService ctKeyPoolService;
    @Test
    public void testSelect() {
        List<CtKeyPoolEntity> list = ctKeyPoolService.list();
        Map<String, CtKeyPoolEntity> listMap = list.stream()
                .collect(Collectors.toMap(CtKeyPoolEntity::getApiKey, item -> item, (o1, o2) -> o1));

        System.out.println(list);
    }
}
