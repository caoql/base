package com.cal.base.common.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * 邮件创建，发送等一系列操作
 * 
 * @author andyc 2018-4-10
 *
 */
public class MailSender {
	private String defaultFrom;
	private String defaultTo;
	private String defaultSubject;
	// 群发
	private String[] sendTo;

	// 注入Spring提供的邮件服务
	@Autowired
	private JavaMailSenderImpl javaMailSender;

	// 邮件发送重载了一下几个方法
	public void send(MimeMessage mimeMessage) {
		javaMailSender.send(mimeMessage);
	}

	public void send(MimeMessagePreparator mimeMessagePreparator) {
		javaMailSender.send(mimeMessagePreparator);
	}

	public void send(final SimpleMailMessage smm) {
		send(smm, true);
	}

	public void send(final SimpleMailMessage smm, final boolean useHtmlFormat) {
		if (smm == null) {
			return;
		}
		// 发送人默认设置-有群发设置群发，没有就发给具体的某人
		if (smm.getTo() == null || smm.getTo().length == 0) {
			if (sendTo != null && sendTo.length != 0) {
				smm.setTo(sendTo);
			} else {
				smm.setTo(defaultTo);
			}
		}
		// 默认主题设置
		if (StringUtils.isBlank(smm.getSubject())) {
			smm.setSubject(defaultSubject);
		}
		// 循环发送邮件
		for (final String to : smm.getTo()) {
			javaMailSender.send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws MessagingException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					// 发件人编码
					if (StringUtils.isNotBlank(smm.getFrom())) {
						message.setFrom(encode(smm.getFrom()));
					} else {
						message.setFrom(encode(defaultFrom));
					}
					if (StringUtils.isNotBlank(smm.getReplyTo())) {
						message.setReplyTo(encode(smm.getReplyTo()));
					}
					message.setTo(encode(to));
					message.setSubject(smm.getSubject());
					message.setText(smm.getText(), useHtmlFormat);
				}
			});
		}
	}

	// 编码
	public static String encode(String to) {
		if (StringUtils.isBlank(to)) {
			return to;
		}
		String[] array = to.split(",");
		int len = array.length;
		for (int i = 0; i < len; i++) {
			// 不存在'<'就跳过
			if (array[i].indexOf('<') < 0) {
				continue;
			}
			// 截取名字
			String name = array[i].substring(0, array[i].indexOf('<'));
			name = name.trim();
			// 删除"和'
			name = StringUtils.remove(name, "\"");
			name = StringUtils.remove(name, "'");
			// 获取Email地址
			String email = array[i].substring(array[i].indexOf('<'));
			try {
				array[i] = MimeUtility.encodeWord(name, "UTF-8", "Q") + email;
			} catch (UnsupportedEncodingException e) {
				email = email.substring(1, email.length());
				array[i] = email;
			}
		}
		return StringUtils.join(array, ",");
	}

	public String getDefaultFrom() {
		return defaultFrom;
	}

	public void setDefaultFrom(String defaultFrom) {
		this.defaultFrom = defaultFrom;
	}

	public String getDefaultTo() {
		return defaultTo;
	}

	// 是否配置的是群发
	public void setDefaultTo(String defaultTo) {
		if (StringUtils.contains(defaultTo, ";")) {
			sendTo = StringUtils.split(defaultTo, ";");
		} else {
			this.defaultTo = defaultTo;
		}
	}

	public String getDefaultSubject() {
		return defaultSubject;
	}

	public void setDefaultSubject(String defaultSubject) {
		this.defaultSubject = defaultSubject;
	}
}
