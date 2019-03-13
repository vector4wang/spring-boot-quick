import com.dynamic.bean.Application;
import com.dynamic.bean.box.AnonymousComponent;
import com.dynamic.bean.box.Person;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author wangxc
 * @date: 2019/3/12 下午11:00
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DynamicTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void testDynamicAttr() {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		System.out.println("================================================================================");
		Person person_1 = (Person) context.getBean("person_1");
		Person person_2 = (Person) context.getBean("person_2");
		Person person_3 = (Person) context.getBean("person_3");
		Person person_4 = (Person) context.getBean("person_4");
		Person person_5 = (Person) context.getBean("person_5");
		System.out.println("person_1===> "+ person_1);
		System.out.println("person_2===> "+ person_2);
		System.out.println("person_3===> "+ person_3);
		System.out.println("person_4===> "+ person_4);
		System.out.println("person_5===> "+ person_5);
		/**
		 * person_1===> com.dynamic.bean.box.Person@41fed14f
		 * person_2===> com.dynamic.bean.box.Person@4d6ee47
		 * person_2===> com.dynamic.bean.box.Person@4d6ee47
		 * person_3===> com.dynamic.bean.box.Person@a33b4e3
		 * person_4===> com.dynamic.bean.box.Person@c6da8bb
		 * person_5===> com.dynamic.bean.box.Person@3bae64d0
		 */

	}

	@Test
	public void testDynamicAnnotation() {

	}
}
