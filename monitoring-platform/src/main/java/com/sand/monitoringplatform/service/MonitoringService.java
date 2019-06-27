package com.sand.monitoringplatform.service;

import com.sand.monitoringplatform.pojo.MonitoringConfig;
import com.sand.monitoringplatform.pojo.ResultBean;

public interface MonitoringService {

	public ResultBean monitoring(MonitoringConfig monitoringConfig);
	
	public void writeErrorMsg(MonitoringConfig monitoringConfig,ResultBean result);
	
	public void sendMsg(String subject,String msg);
	
	public void sendErrorMsg(String subject,String msg);
	
}
