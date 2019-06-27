package com.sand.monitoringplatform.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "mail")
@PropertySource("classpath:config/${spring.profiles.active}/sendMail.properties")
public class SendMailProperties {

	private String defaultTo;
	private String defaultCc;
	private String errorSuccessTo;
	private String errorSuccessCc;
	
	
	public String getDefaultTo() {
		return defaultTo;
	}
	public void setDefaultTo(String defaultTo) {
		this.defaultTo = defaultTo;
	}
	public String getDefaultCc() {
		return defaultCc;
	}
	public void setDefaultCc(String defaultCc) {
		this.defaultCc = defaultCc;
	}
	public String getErrorSuccessTo() {
		return errorSuccessTo;
	}
	public void setErrorSuccessTo(String errorSuccessTo) {
		this.errorSuccessTo = errorSuccessTo;
	}
	public String getErrorSuccessCc() {
		return errorSuccessCc;
	}
	public void setErrorSuccessCc(String errorSuccessCc) {
		this.errorSuccessCc = errorSuccessCc;
	}

	
}
