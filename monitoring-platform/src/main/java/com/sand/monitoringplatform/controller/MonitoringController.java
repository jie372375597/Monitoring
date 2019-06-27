package com.sand.monitoringplatform.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sand.monitoringplatform.dao.MonitoringConfigDao;
import com.sand.monitoringplatform.instance.CheckMonitoringManage;
import com.sand.monitoringplatform.pojo.MonitoringConfig;
import com.sand.monitoringplatform.pojo.ResultBean;
import com.sand.monitoringplatform.service.MonitoringService;
import com.sand.monitoringplatform.util.StringUtil;

@RestController
@RequestMapping(value = "/monitoring")
@EnableScheduling
public class MonitoringController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MonitoringConfigDao monitoringConfigDao;
	@Autowired
	private MonitoringService monitoringService;
	
	private CheckMonitoringManage instance = CheckMonitoringManage.getInstance();

	/**
	 * 秒（0~59） 例如0/5表示每5秒 
	 * 分（0~59）
	 * 时（0~23）
	 * 日（0~31）的某天，需计算
	 * 月（0~11）
	 * 周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void task() {
		monitoring();
	}
	
	@RequestMapping(value = "/database")
	public void monitoring() {
		if (!instance.checkMonitoringFlag()) {
			try {
				List<MonitoringConfig> monitoringConfigList = monitoringConfigDao.queryAll();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Date now = new Date();
				//System.out.println(now);
				for (MonitoringConfig monitoringConfig : monitoringConfigList) {
					if(StringUtil.isEmpty(monitoringConfig.getNextdate())||StringUtil.isEmpty(monitoringConfig.getType())) {
						monitoringService.writeErrorMsg(monitoringConfig, new ResultBean(false, "ID:"+monitoringConfig.getId()+",nextDate列,type列不能为空 !"));
						monitoringService.sendErrorMsg("ID:"+monitoringConfig.getId()+",比对错误", "nextDate列,type列不能为空 !");
						continue;
					}
				    //是否开始比对时间校验
					Date nextDate = sdf.parse(monitoringConfig.getNextdate());
					if(now.getTime() < nextDate.getTime()) {
						continue;
					}
					logger.info("ID:"+monitoringConfig.getId()+",比对开始 !");
					ResultBean result = monitoringService.monitoring(monitoringConfig);
					if (!result.isSuccess()) {
						monitoringService.writeErrorMsg(monitoringConfig, result);
						monitoringService.sendErrorMsg("ID:"+monitoringConfig.getId()+",比对错误", result.getMsg());
					}
					logger.info(result.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				monitoringService.sendErrorMsg("minitoring比对异常", e.toString());
			} finally {
				instance.setMonitoring(false);
			}
		}else {
			logger.info("比对中---------------------------------------------------------");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
