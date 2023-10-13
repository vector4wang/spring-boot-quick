import com.quick.CustomApplication;
import com.quick.entity.Sample;
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
        List<Sample> list = ctKeyPoolService.list();
        Map<String, Sample> listMap = list.stream()
                .collect(Collectors.toMap(Sample::getApiKey, item -> item, (o1, o2) -> o1));

        System.out.println(list);
    }
}
