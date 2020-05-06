package com.quick.mail.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author wangxc
 * @date: 2020/5/6 下午9:46
 *
 */
@Service
public class SendEmailService {


	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	/**
	 * 文本邮件
	 */
	public void sendEmail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("772704457@qq.com");
		msg.setTo("vector4wang@gmail.com","wdc43101289217@126.com");
		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello World \n Spring Boot Email");
		javaMailSender.send(msg);
	}

	/**
	 * 带图片
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 */
	public void sendImageEmail() throws MessagingException, FileNotFoundException {
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("772704457@qq.com");
		helper.setTo(new String[]{"vector4wang@gmail.com","wdc43101289217@126.com"});
		helper.setSubject("Testing from Spring Boot");
		String rscId = "id";
		helper.setText("<html><body>这是有图片的邮件<br/><img src='"+rscId+"' ></body></html>", true);
		FileSystemResource res = new FileSystemResource(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "bang.jpeg"));
		helper.addInline(rscId, res);
		javaMailSender.send(message);
	}

	/**
	 * 带有html的邮件
	 */
	public void sendHtmlEmail() throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();

		//true表示需要创建一个multipart message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("772704457@qq.com");
		helper.setTo(new String[]{"vector4wang@gmail.com","wdc43101289217@126.com"});
		helper.setSubject("Testing from Spring Boot");
		String htmlStr = new StringBuilder()
				.append("<body style=\"text-align: center;margin-left: auto;margin-right: auto;\">\n")
				.append("	<div id=\"welcome\" style=\"text-align: center;position: absolute;\" >\n")
				.append("		<h3>恭喜你中奖了！</h3>\n")
				.append("		<span>https://github.com/vector4wang/spring-boot-quick</span>").append("		<div\n")
				.append("			style=\"text-align: center; padding: 10px\"><a style=\"text-decoration: none;\" href=\"https://github.com/vector4wang/spring-boot-quick\" target=\"_bank\" ><strong>您的鼓励是我前进的最大动力！</strong></a></div>\n")
				.append("		<div\n")
				.append("			style=\"text-align: center; padding: 4px\">如果对你有帮助,请点赞收藏</div>\n")
				.append("	</div>\n").append("</body>").toString();
		helper.setText(htmlStr, true);
		javaMailSender.send(message);
	}

	public void sendAttachmentMail() throws MessagingException, FileNotFoundException {
		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("772704457@qq.com");
		helper.setTo(new String[]{"vector4wang@gmail.com","wdc43101289217@126.com"});
		helper.setSubject("Testing from Spring Boot");
		helper.setText("带有附件的邮件", true);
		File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "bang.jpeg");
		FileSystemResource fileSystemResource = new FileSystemResource(file);
		helper.addAttachment(file.getName(), fileSystemResource);

		javaMailSender.send(message);
	}

	public void sendTemplateEmail() throws MessagingException, FileNotFoundException {
		Context context = new Context();
		context.setVariable("project", "quick-springbooot");
		context.setVariable("author", "vector4wang");
		context.setVariable("url", "https://github.com/vector4wang/spring-boot-quick");
		String emailContent = templateEngine.process("wel", context);

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("772704457@qq.com");
		helper.setTo(new String[]{"vector4wang@gmail.com","wdc43101289217@126.com"});
		helper.setSubject("Testing from Spring Boot");
		helper.setText(emailContent, true);
		helper.addInline("bang", ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "bang.jpeg"));
		javaMailSender.send(message);

	}
}
