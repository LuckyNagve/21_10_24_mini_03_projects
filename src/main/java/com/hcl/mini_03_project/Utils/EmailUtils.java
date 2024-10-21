package com.hcl.mini_03_project.Utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Component
public class EmailUtils {
	
@Autowired
public JavaMailSender mailsender;
	

	public boolean sendEmail(String to,String subject,String body) {
		boolean isSend=false;
		try {
			MimeMessage mimiMessage=mailsender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(mimiMessage);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			
			mailsender.send(mimiMessage);
			isSend=true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return isSend;
	}
}