package kr.co.kmac.pms.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MailService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	JavaMailSenderImpl sender = new JavaMailSenderImpl();
	
	@Async
	public void sendMail(String title, String content, ArrayList email) throws Exception {
		sender.setHost("webmail.kmac.co.kr");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(new InternetAddress("cs@kmac.co.kr", "KMAC", "UTF-8"));
		helper.setSentDate(new Date());
		helper.setSubject(title);
		helper.setText(content, true);
		logger.info("content ==>"+content);
		System.out.println();
		logger.info("================SEND START =====================");
		for(int i=0;i<email.size();i++) {
			String ReceiveEmail = (String)email.get(i);
			logger.info("SEND ==>"+ReceiveEmail);
			helper.setTo(ReceiveEmail);
			sender.send(message);
		}
		logger.info("================SEND END =====================");
		
	}
	
	
	@Async
	public void sendMail(String title, String content, ArrayList email, Map<String, Object> UserInfo) throws Exception {
		sender.setHost("webmail.kmac.co.kr");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(new InternetAddress((String)UserInfo.get("email"), (String)UserInfo.get("name"), "UTF-8"));
		helper.setSentDate(new Date());
		helper.setSubject(title);
		helper.setText(content, true);
		logger.info("content ==>"+content);
		System.out.println();
		logger.info("================SEND START =====================");
		for(int i=0;i<email.size();i++) {
			String ReceiveEmail = (String)email.get(i);
			logger.info("SEND ==>"+ReceiveEmail);
			helper.setTo(ReceiveEmail);
			sender.send(message);
		}
		logger.info("================SEND END =====================");
		
	}
}
