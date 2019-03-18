package service;

import com.rest.template.Application;
import com.rest.template.service.RestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.URISyntaxException;

/**
 * @author vector
 * @date: 2019/3/15 0015 9:31
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RestServiceTest {

    @Resource
    private RestService restService;

    @Test
    public void testGet() throws URISyntaxException {
        restService.get();
    }

    @Test
    public void testPost() throws URISyntaxException {
        restService.post();
    }

    @Test
    public void testPost4Form() {
        restService.post4Form();
    }

}
