package com.cal.base.common.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 1.RFC882文档规定了如何编写一封简单的邮件(纯文本邮件)，一封简单的邮件包含邮件头和邮件体两个部分，邮件头和邮件体之间使用空行分隔。
 * 2.在我们的实际开发当中，一封邮件既可能包含图片，又可能包含有附件，在这样的情况下，RFC882文档规定的邮件格式就无法满足要求了。
 * 3.MIME协议是对RFC822文档的升级和补充，它描述了如何生产一封复杂的邮件。通常我们把MIME协议描述的邮件称之为MIME邮件。MIME协议描述的数据称之为MIME消息。
 * 4.对于一封复杂邮件，如果包含了多个不同的数据，MIME协议规定了要使用分隔线对多段数据进行分隔，并使用Content-Type头字段对数据的类型、以及多个数据之间的关系进行描述。
 * 5.JavaMail创建的邮件是基于MIME协议的。因此可以使用JavaMail创建出包含图片，包含附件的复杂邮件。
 * @author andyc 2018-4-10
 *
 */
public class MyMailSenderTest extends Thread {
	/**
	 * Java mail使用QQ邮箱发送邮件配置方案:
	 * 1.QQ邮箱的POP3/SMTP，IMAP/SMTP服务已开启，并获取授权码（当密码使用）,至于怎么设置，搜索引擎查去。
	 * 2.正确的邮件服务配置，【有些默认配置可以不配，如端口号】
	 * 3.发件人和收件人是相同的地址，发件人的邮件类型要和服务器地址类型匹配，isSSL要是true才可以（mail.smtp.ssl.enable）
	 */
	// 邮件头包含的内容有：
	// 用于指明发件人
	private String from = "2397776038@qq.com";
	// 用于指明收件人
	private String to = "2397776038@qq.com";
	// 用于说明邮件主题
	private String subject = "邮件主题Test";
	// 抄送，将邮件发送给收件人的同时抄送给另一个收件人，收件人可以看到邮件抄送给了谁
	// private String cc;
	// 密送，将邮件发送给收件人的同时将邮件秘密发送给另一个收件人，收件人无法看到邮件密送给了谁
	// private String bcc;
	
	// 发送邮件的服务器地址,注意：如果你的邮箱服务为smtp.163.com，而你的发送邮箱就必须是xxxx@163.com;接收邮箱随便。
	private String host = "smtp.qq.com";
	// qq邮箱端口号-发件服务器（SMTP): smtp.qq.com 端口号是：465,收件服务器（IMAP): imap.qq.com 端口号是：993
	private String port = "465";
	// 邮箱的用户名
	private String username = "2397776038";
	// 邮箱的密码lnjegnhwswtwdjfc
	private String password = "lnjegnhwswtwdjfc";

	@Override
	public void run() {
		try {
			// 使用JavaMail发送邮件的5个步骤
			Properties prop = new Properties();
			prop.setProperty("mail.host", host);
			prop.setProperty("mail.smtp.port", port);
			prop.setProperty("mail.transport.protocol", "smtp");
			prop.setProperty("mail.smtp.auth", "true");
			prop.setProperty("mail.smtp.ssl.enable", "true");// 这个属性设置很关键
			// 1、创建session
			Session session = Session.getInstance(prop);
			// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
			session.setDebug(true);
			// 2、通过session得到transport对象
			Transport ts = session.getTransport();
			// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			ts.connect(host, username, password);
			// 4、创建邮件
			Message message = createEmail(session);
			// 5、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 创建一封只包含文本的邮件
	public Message createEmail(Session session) throws Exception {
		// MimeMessage类代表整封邮件
		MimeMessage message = new MimeMessage(session);
		// 指明邮件的发件人
		message.setFrom(new InternetAddress(from));
		// 指明邮件的收件人(如果发件人和收件人是一样的，那就是自己给自己发)
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		// 邮件的标题
		message.setSubject(subject);
		// 邮件体指的就是邮件的具体内容。为了避免邮件正文中文乱码问题，需要使用charset=UTF-8指明字符编码
		String info = "恭喜您注册成功，您的用户名:..." + "，请妥善保管，如有问题请联系网站客服!!";
		message.setContent(info, "text/html;charset=UTF-8");
		
		message.saveChanges();
		// 返回创建好的邮件对象
		return message;
	}
}
