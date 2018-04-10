package com.cal.base.common.mail;

import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

/**
 * 邮件发送服务
 * @author andyc 2018-4-10
 *
 */
public class MailService {

	private Logger log = LoggerFactory.getLogger(getClass());

	ExecutorService executorService = Executors.newCachedThreadPool();

	@Autowired
	private MailSender mailSender;

	public void send() {
		SimpleMailMessage smm = new SimpleMailMessage();
		send(smm,true);
	}
	
	public void send(final SimpleMailMessage smm, final boolean useHtmlFormat) {
		log.debug("邮件发送了额...");
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				mailSender.send(smm, useHtmlFormat);
			}
		});
	}

	public void send(String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setText(message);
		send(mailMessage, true);
	}
	
	public void send(String message, String subject) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		send(mailMessage, true);
	}

	public void send(String message, String subject, String to) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailMessage.setTo(to);
		send(mailMessage, true);
	}

	public void send(SimpleMailMessage smm, String templateName,
			Map<String, Object> model) {
		send(smm, templateName, model, true);
	}

	public void send(SimpleMailMessage smm, String templateName,
			Map<String, Object> model, boolean useHtmlFormat) {
		model.put("sendmail", true);
		StringWriter writer = new StringWriter();
		smm.setText(writer.toString());
		send(smm, useHtmlFormat);
	}
}
