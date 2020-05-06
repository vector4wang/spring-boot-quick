package service;

import com.EmailApplication;
import com.quick.mail.service.SendEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.FileNotFoundException;

/**
 *
 * @author wangxc
 * @date: 2020/5/6 下午9:52
 *
 */
@SpringBootTest(classes = EmailApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SendEmailServiceTest {

	@Resource
	private SendEmailService sendEmailService;



	@Test
	public void testSendTxtEmail() {
		sendEmailService.sendEmail();
	}

	@Test
	public void testSendImageEmail() throws MessagingException, FileNotFoundException {
		sendEmailService.sendImageEmail();
	}

	@Test
	public void testSendHtmlEmail() throws MessagingException {
		sendEmailService.sendHtmlEmail();
	}

	@Test
	public void testSendAttachmentMail() throws FileNotFoundException, MessagingException {
		sendEmailService.sendAttachmentMail();
	}

	@Test
	public void testTemplateEmail() throws FileNotFoundException, MessagingException {
		sendEmailService.sendTemplateEmail();

	}
}
