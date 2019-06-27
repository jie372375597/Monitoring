package com.sand.monitoringplatform.pojo;


public class DBConfig {
	private Integer id;
	private String driverClassName;
	private String url;
	private String userName;
	private String password;
	

	public DBConfig() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DBConfig(String driverClassName, String url, String userName, String password) {
		super();
		this.driverClassName = driverClassName;
		this.userName = userName;
		this.password = password;
		this.url = url;
	}
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "DBConfig [id=" + id + ", driverClassName=" + driverClassName + ", url=" + url + ", userName=" + userName
				+ ", password=" + password + "]";
	}

}