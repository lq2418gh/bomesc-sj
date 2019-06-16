package dkd.paltform.util;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


/**
 * 标准邮件
 * @author sy
 * 
 */

public class MailSend {
	
	static SimpleMailMessage simpleMailMessage =  (SimpleMailMessage) SpringUtil.getBean("mailMessage"); 
	static JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) SpringUtil.getBean("mailSender");
	
	/**
	 * 发送普通邮件方法
	 * @param subject 主题
	 * @param text 内容
	 * @param to 收件邮箱
	 */
	
	public void sendMessage(String subject,String text,String to){
		MimeMessage mailMessage = javaMailSenderImpl.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,false,"gb2312");
			messageHelper.setFrom(simpleMailMessage.getFrom());
			messageHelper.setSubject(subject);
			messageHelper.setText(text);
			messageHelper.setTo(to);
			javaMailSenderImpl.send(mailMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送附件邮件方法
	 * @param subject主题
	 * @param text内容
	 * @param to收件邮箱
	 * @param files 附件   key是附件名称带后缀   value是文件地址
	 */
	
	public void sendEmail(String subject,String text,String to,Map<String, String> files) {
		MimeMessage mailMessage = javaMailSenderImpl.createMimeMessage();
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");
			messageHelper.setSubject(subject);
			messageHelper.setText(text);
			messageHelper.setTo(to);
			for(Map.Entry<String, String> entry: files.entrySet()){
				FileSystemResource file = new FileSystemResource(entry.getValue());
				messageHelper.addAttachment(entry.getKey(), file);//添加到附件
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		javaMailSenderImpl.send(mailMessage);
	}
	
}