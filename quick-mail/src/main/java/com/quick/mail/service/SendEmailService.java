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
//		String htmlStr = new StringBuilder()
//				.append("<body style=\"text-align: center;margin-left: auto;margin-right: auto;\">\n")
//				.append("	<div id=\"welcome\" style=\"text-align: center;position: absolute;\" >\n")
//				.append("		<h3>恭喜你中奖了！</h3>\n")
//				.append("		<span>https://github.com/vector4wang/spring-boot-quick</span>").append("		<div\n")
//				.append("			style=\"text-align: center; padding: 10px\"><a style=\"text-decoration: none;\" href=\"https://github.com/vector4wang/spring-boot-quick\" target=\"_bank\" ><strong>您的鼓励是我前进的最大动力！</strong></a></div>\n")
//				.append("		<div\n")
//				.append("			style=\"text-align: center; padding: 4px\">如果对你有帮助,请点赞收藏</div>\n")
//				.append("	</div>\n").append("</body>").toString();
		String htmlContent = getHtmlContent();
		helper.setText(htmlContent, true);
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

	public String getHtmlContent() {
		String str = new StringBuilder().append("<!DOCTYPE html>\n").append("<html lang=\"en\">\n").append("<head>\n")
				.append("  <meta charset=\"UTF-8\">\n")
				.append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
				.append("  <title>邮件推送</title>\n").append("</head>\n").append("<style>\n").append("  body {\n")
				.append("    margin: 0px;\n").append("  }\n").append("  .header {\n").append("    width: 100%;\n")
				.append("    height: 50px;\n").append("    background-color: #222222;\n")
				.append("    flex: 0 1 auto;\n").append("  }\n").append("  .header-logo {\n")
				.append("    height: 50px;\n").append("    line-height: 50px;\n").append("    padding-left: 24px;\n")
				.append("    font-size: 18px;\n").append("    font-weight: 600;\n").append("    color: #ffffff;\n")
				.append("  }\n").append("  .wrapper {\n").append("    padding: 20px;\n")
				.append("    background-color: #eeeeee;\n").append("  }\n").append("  .content {\n")
				.append("    width: 75%;\n").append("    padding: 12px 24px;\n").append("    margin: 0 auto;\n")
				.append("    border: 1px solid #dcdcdc;\n").append("    background-color: #fff;\n").append("  }\n")
				.append("  .row {\n").append("    height: 48px;\n").append("    line-height: 48px;\n").append("  }\n")
				.append("  .title {\n").append("    font-size: 18px;\n").append("    font-weight: 600;\n")
				.append("  }\n").append("  .info {\n").append("    display: flex;\n")
				.append("    justify-content: space-between;\n").append("  }\n").append("  .card {\n")
				.append("    border: 1px solid #f0f0f0;\n").append("  }\n").append("  .card-title {\n")
				.append("    padding: 0 24px;\n").append("    background-color: #fafafa;\n")
				.append("    min-height: 48px;\n").append("    border: 1px solid #f0f0f0;\n")
				.append("    border-radius: 2px;\n").append("  }\n").append("  .card-title span {\n")
				.append("    height: 48px;\n").append("    line-height: 48px;\n").append("  }\n")
				.append("  .card-body {\n").append("    padding: 12px 24px;\n").append("  }\n")
				.append("  .card-row-1 {\n").append("    display: flex;\n").append("    justify-content: flex-start;\n")
				.append("    margin-bottom: 8px;\n").append("  }\n").append("</style>\n").append("<body>\n")
				.append("  <div class=\"header\">\n").append("    <span class=\"header-logo\">月台安全</span>\n")
				.append("  </div>\n").append("  <div class=\"wrapper\">\n").append("    <div class=\"content\">\n")
				.append("      <section class=\"title row\">事件详情</section>\n")
				.append("      <section class=\"info row\">\n").append("        <div>一号仓</div>\n")
				.append("        <div>\n").append("          <span>事件</span>\n")
				.append("          <span style=\"color: #40a9ff\">人员告警</span>\n").append("          <span>|</span>\n")
				.append("          <span>作业车辆</span>\n").append("          <span style=\"color: #40a9ff\">无</span>\n")
				.append("        </div>\n").append("      </section>\n").append("      <section class='card'>\n")
				.append("        <div class=\"card-title\">\n").append("          <span>事件信息</span>\n")
				.append("        </div>\n").append("        <div class=\"card-body\">\n")
				.append("          <div class=\"card-row-1\">\n").append("            <div style=\"width: 50%;\">\n")
				.append("              <span>开始时间: </span>\n")
				.append("              <span style=\"color: #40a9ff\">2020-3-15 00:00:00</span>\n")
				.append("            </div>\n").append("            <div  style=\"width: 50%;\">\n")
				.append("              <span>结束时间: </span>\n")
				.append("              <span style=\"color: #40a9ff\">2020-3-16 00:00:00</span>\n")
				.append("            </div>\n").append("          </div>\n")
				.append("          <div class=\"card-row-2\">\n").append("            <span>持续时长: </span>\n")
				.append("            <span style=\"color: #40a9ff\">2.7h</span>\n").append("          </div>\n")
				.append("        </div>\n").append("      </section>\n").append("    </div>\n").append("  </div>\n")
				.append("  \n").append("    <div class=\"wrapper\">\n").append("    <div class=\"content\">\n")
				.append("      <section class=\"title row\">事件详情</section>\n")
				.append("      <section class=\"info row\">\n").append("        <div>一号仓</div>\n")
				.append("        <div>\n").append("          <span>事件</span>\n")
				.append("          <span style=\"color: #40a9ff\">人员告警</span>\n").append("          <span>|</span>\n")
				.append("          <span>作业车辆</span>\n").append("          <span style=\"color: #40a9ff\">无</span>\n")
				.append("        </div>\n").append("      </section>\n").append("      <section class='card'>\n")
				.append("        <div class=\"card-title\">\n").append("          <span>事件信息</span>\n")
				.append("        </div>\n").append("        <div class=\"card-body\">\n")
				.append("          <div class=\"card-row-1\">\n").append("            <div style=\"width: 50%;\">\n")
				.append("              <span>开始时间: </span>\n")
				.append("              <span style=\"color: #40a9ff\">2020-3-15 00:00:00</span>\n")
				.append("            </div>\n").append("            <div  style=\"width: 50%;\">\n")
				.append("              <span>结束时间: </span>\n")
				.append("              <span style=\"color: #40a9ff\">2020-3-16 00:00:00</span>\n")
				.append("            </div>\n").append("          </div>\n")
				.append("          <div class=\"card-row-2\">\n").append("            <span>持续时长: </span>\n")
				.append("            <span style=\"color: #40a9ff\">2.7h</span>\n").append("          </div>\n")
				.append("        </div>\n").append("      </section>\n").append("    </div>\n").append("  </div>\n")
				.append("  \n").append("    <div class=\"wrapper\">\n").append("    <div class=\"content\">\n")
				.append("      <section class=\"title row\">事件详情</section>\n")
				.append("      <section class=\"info row\">\n").append("        <div>一号仓</div>\n")
				.append("        <div>\n").append("          <span>事件</span>\n")
				.append("          <span style=\"color: #40a9ff\">人员告警</span>\n").append("          <span>|</span>\n")
				.append("          <span>作业车辆</span>\n").append("          <span style=\"color: #40a9ff\">无</span>\n")
				.append("        </div>\n").append("      </section>\n").append("      <section class='card'>\n")
				.append("        <div class=\"card-title\">\n").append("          <span>事件信息</span>\n")
				.append("        </div>\n").append("        <div class=\"card-body\">\n")
				.append("          <div class=\"card-row-1\">\n").append("            <div style=\"width: 50%;\">\n")
				.append("              <span>开始时间: </span>\n")
				.append("              <span style=\"color: #40a9ff\">2020-3-15 00:00:00</span>\n")
				.append("            </div>\n").append("            <div  style=\"width: 50%;\">\n")
				.append("              <span>结束时间: </span>\n")
				.append("              <span style=\"color: #40a9ff\">2020-3-16 00:00:00</span>\n")
				.append("            </div>\n").append("          </div>\n")
				.append("          <div class=\"card-row-2\">\n").append("            <span>持续时长: </span>\n")
				.append("            <span style=\"color: #40a9ff\">2.7h</span>\n").append("          </div>\n")
				.append("        </div>\n").append("      </section>\n").append("    </div>\n").append("  </div>\n")
				.append("</body>\n").append("</html>").toString();

		return str;

	}

}
