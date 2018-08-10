import com.alibaba.fastjson.JSON;
import com.multi.test.Application;
import com.multi.test.service.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author vector
 * @Data 2018/8/10 0010
 * @Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CityServiceTest {

	@Resource
	private CityService cityService;

	@Test
	public void testGetCity() {
		System.out.println(JSON.toJSONString(cityService.getCity(1)));
		/**
		 * {"countrycode":"AFG","district":"Kabol","id":1,"name":"Kabul","population":1780000}
		 */
	}
}
