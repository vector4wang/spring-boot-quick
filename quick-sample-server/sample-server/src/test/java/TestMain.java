import com.quick.CustomApplication;
import com.quick.entity.SampleTable;
import com.quick.service.SampleTableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(classes = CustomApplication.class)
public class TestMain {
    @Autowired
    private SampleTableService sampleTableService;

    @Test
    public void testSelect() {
        List<SampleTable> list = sampleTableService.list();
        Map<String, SampleTable> listMap = list.stream().collect(Collectors.toMap(SampleTable::getUserCode, item -> item, (o1, o2) -> o1));

        System.out.println(listMap);
    }
}
