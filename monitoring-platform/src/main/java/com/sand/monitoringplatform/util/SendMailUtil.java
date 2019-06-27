package com.sand.monitoringplatform.util;

import org.sand.mail.send.SandMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendMailUtil {
	
	@Autowired
	private SandMailSender sandMailSender;
	
	public void sendMail(String subject, String errorMsg, String to, String cc) {
		try {
			sandMailSender.sendTextMail(to, cc, subject, errorMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMailHtml(String subject, String errorMsg, String to, String cc) {
		try {
			sandMailSender.sendHtmlMail(to, cc, subject, errorMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
