package com.sand.monitoringplatform.instance;


public class CheckMonitoringManage {

	private volatile static CheckMonitoringManage instance = null;
	
	private volatile boolean isMonitoring = false;//true:表示存在比对，false允许比对
	
	private CheckMonitoringManage() {}
	
	public static CheckMonitoringManage getInstance(){
		if (instance == null) {
			synchronized (CheckMonitoringManage.class) {
				if (instance == null) {
					instance = new CheckMonitoringManage();
				}
			} 
		}
		return instance;
	}

	public synchronized boolean checkMonitoringFlag() {
		if (isMonitoring) {
			return isMonitoring;
		} else {
			isMonitoring = true;
			return false;
		}
	}

	public synchronized void setMonitoring(boolean isMonitoring) {
		this.isMonitoring = isMonitoring;
	}
}
