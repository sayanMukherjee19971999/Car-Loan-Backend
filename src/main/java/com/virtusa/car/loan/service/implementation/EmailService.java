package com.virtusa.car.loan.service.implementation;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Configuration config;

	public boolean email(String message,String subject,String recipient) {
		boolean f=false;
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		
		simpleMailMessage.setFrom("springboot130@gmail.com");
		simpleMailMessage.setTo(recipient);
		simpleMailMessage.setText(message);
		simpleMailMessage.setSubject(subject);
		
		mailSender.send(simpleMailMessage);
		f=true;
		return f;
	}
	
	public boolean templateEmail(Map<String,String> message,String subject,String recipient) {
		boolean f=false;
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
			
			Template t=config.getTemplate("email-template.ftl");
			String html=FreeMarkerTemplateUtils.processTemplateIntoString(t, message);
			
			mimeMessageHelper.setFrom("springboot130@gmail.com");
			mimeMessageHelper.setTo(recipient);
			mimeMessageHelper.setText(html,true);
			mimeMessageHelper.setSubject(subject);
			
			mailSender.send(mimeMessage);
			
			f=true;
		}
		catch(MessagingException | IOException | TemplateException e) {
			System.out.println(e.getMessage());
			f=false;
		}
		return f;
	}
	
	public boolean loanUpdateEmail(Map<String,String> message,String subject,String recipient) {
		boolean f=false;
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
			
			Template t=config.getTemplate("loan-update.ftl");
			String html=FreeMarkerTemplateUtils.processTemplateIntoString(t, message);
			
			mimeMessageHelper.setFrom("springboot130@gmail.com");
			mimeMessageHelper.setTo(recipient);
			mimeMessageHelper.setText(html,true);
			mimeMessageHelper.setSubject(subject);
			
			mailSender.send(mimeMessage);
			
			f=true;
		}
		catch(MessagingException | IOException | TemplateException e) {
			System.out.println(e.getMessage());
			f=false;
		}
		return f;
	}
	
	public boolean loanApproveEmail(Map<String,String> message,String subject,String recipient) {
		boolean f=false;
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
			
			Template t=config.getTemplate("loan-approve.ftl");
			String html=FreeMarkerTemplateUtils.processTemplateIntoString(t, message);
			
			mimeMessageHelper.setFrom("springboot130@gmail.com");
			mimeMessageHelper.setTo(recipient);
			mimeMessageHelper.setText(html,true);
			mimeMessageHelper.setSubject(subject);
			
			mailSender.send(mimeMessage);
			
			f=true;
		}
		catch(MessagingException | IOException | TemplateException e) {
			System.out.println(e.getMessage());
			f=false;
		}
		return f;
	}
	
	public boolean loanRejectEmail(Map<String,String> message,String subject,String recipient) {
		boolean f=false;
		MimeMessage mimeMessage=mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
			
			Template t=config.getTemplate("loan-reject.ftl");
			String html=FreeMarkerTemplateUtils.processTemplateIntoString(t, message);
			
			mimeMessageHelper.setFrom("springboot130@gmail.com");
			mimeMessageHelper.setTo(recipient);
			mimeMessageHelper.setText(html,true);
			mimeMessageHelper.setSubject(subject);
			
			mailSender.send(mimeMessage);
			
			f=true;
		}
		catch(MessagingException | IOException | TemplateException e) {
			System.out.println(e.getMessage());
			f=false;
		}
		return f;
	}
}
