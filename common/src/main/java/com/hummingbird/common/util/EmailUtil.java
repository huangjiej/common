/**
 * 
 * EmailUtil.java
 * 版本所有 深圳市蜂鸟娱乐有限公司 2013-2014
 */
package com.hummingbird.common.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.hummingbird.common.util.DESUtil;
import com.hummingbird.common.util.PropertiesUtil;

/**
 * @author john huang 2015年7月18日 上午9:42:07 本类主要做为 邮件工具类
 */
public class EmailUtil {

	static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory
			.getLog(EmailUtil.class);

	/**
	 * 发送html格式的邮件
	 * 
	 * @param toemail
	 * @param subject
	 * @param msg
	 * @throws EmailException
	 */
	public static void sendEmail(String toemail, String subject, String msg)
			throws EmailException {
		if (log.isDebugEnabled()) {
			log.debug(String.format("发送邮件至%s,现在进行准备", toemail));
		}
		// 附件
		// EmailAttachment attachment = new EmailAttachment();
		// attachment.setPath("mypictures/john.jpg");
		// attachment.setDisposition(EmailAttachment.ATTACHMENT);
		// attachment.setDescription("Picture of John");
		// attachment.setName("John");

		// Create the email message
		// MultiPartEmail email = new MultiPartEmail();
		HtmlEmail email = new HtmlEmail();
		PropertiesUtil pu = new PropertiesUtil();
		try {
			email.setAuthenticator(new DefaultAuthenticator(pu
					.getProperty("monitor.notify.email.from"), DESUtil
					.decodeDES(pu.getProperty("monitor.notify.email.psw"),
							"email.psw")));
		} catch (UnsupportedEncodingException e) {
			// log.error(String.format(""),e);
			throw new EmailException("邮件密码获取失败", e);
		}
		email.setHostName(pu.getProperty("monitor.notify.email.hostname"));
		email.setFrom(pu.getProperty("monitor.notify.email.from"));
		String[] toemailarr = StringUtils.split(toemail);
		for (int i = 0; i < toemailarr.length; i++) {
			String atoemail = toemailarr[i];
			email.addTo(atoemail);

		}
		email.setSubject(subject);
		//email.setMsg(msg);
		StringBuilder bodyBf = new StringBuilder();
		bodyBf.append(msg);
		email.addPart(bodyBf.toString(), "text/html;charset=utf-8");

		// add the attachment
		// email.attach(attachment);

		// send the email
		if (log.isDebugEnabled()) {
			log.debug(String.format("开始发送邮件"));
		}
		email.send();
		if (log.isDebugEnabled()) {
			log.debug(String.format("邮件发送完成"));
		}
	}

	public static void main(String[] args) throws EmailException {
		sendEmail("huangjiej@21cn.com", "监控风险警报", "测试监控风险");
	}
}
