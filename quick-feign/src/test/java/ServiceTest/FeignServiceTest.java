package ServiceTest;

import com.quick.feign.Application;
import com.quick.feign.entity.BaseResp;
import com.quick.feign.entity.DomainDetail;
import com.quick.feign.service.FeignService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class FeignServiceTest {

	@Resource
	private FeignService feignService;

	@Test
	public void testGetWeather() {
		BaseResp<DomainDetail> weatherResp = feignService.getDomain("sina.com.cn");
		if (weatherResp.getStatus() == 200) {
			DomainDetail data = weatherResp.getData();
			System.out.println(data);
		}else{
			System.out.println(weatherResp.getMessage());
		}
	}
}
