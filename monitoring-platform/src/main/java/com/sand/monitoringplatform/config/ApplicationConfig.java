package com.sand.monitoringplatform.config;

import java.util.Properties;

import org.sand.mail.send.impl.SpringMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
public class ApplicationConfig {

	@Bean
	@Profile("pro")
	public JavaMailSenderImpl javaMailSenderImpl() {
		JavaMailSenderImpl mail = new JavaMailSenderImpl();
		mail.setHost("172.18.92.254");
		mail.setPort(25);
		mail.setUsername("tui.kuan");
		mail.setPassword("qawsed12");
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.timeout", "25000");
		mail.setJavaMailProperties(javaMailProperties);
		return mail;
	}
	
	@Bean
	public SpringMailSender springMailSender(JavaMailSenderImpl javaMailSenderImpl) {
		SpringMailSender sender = new SpringMailSender();
		sender.setFrom("tui.kuan@sand.com.cn");
		sender.setJavaMailSender(javaMailSenderImpl);
		return sender;
	}
	
	@Bean
	@Profile("dev")
	public JavaMailSenderImpl javaMailSenderImplDev() {
		JavaMailSenderImpl mail = new JavaMailSenderImpl();
		mail.setHost("mail.sand.com.cn");
		mail.setPort(25);
		mail.setUsername("tui.kuan");
		mail.setPassword("qawsed12");
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.timeout", "25000");
		mail.setJavaMailProperties(javaMailProperties);
		return mail;
	}
	
	@Bean
	@Profile("test")
	public JavaMailSenderImpl javaMailSenderImplTest() {
		JavaMailSenderImpl mail = new JavaMailSenderImpl();
		mail.setHost("mail.sand.com.cn");
		mail.setPort(25);
		mail.setUsername("tui.kuan");
		mail.setPassword("qawsed12");
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty("mail.smtp.auth", "true");
		javaMailProperties.setProperty("mail.smtp.timeout", "25000");
		mail.setJavaMailProperties(javaMailProperties);
		return mail;
	}
}
