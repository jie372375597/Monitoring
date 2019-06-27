package com.sand.monitoringplatform.pojo;

public class ResultBean {

	private boolean success;
	
	private String msg;
	
	private Object data;

	public ResultBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public ResultBean(boolean success) {
		super();
		this.success = success;
	}


	public ResultBean(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public ResultBean(boolean success, String msg, Object data) {
		super();
		this.success = success;
		this.msg = msg;
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [success=" + success + ", msg=" + msg + ", data=" + data + "]";
	}
	
	
}
