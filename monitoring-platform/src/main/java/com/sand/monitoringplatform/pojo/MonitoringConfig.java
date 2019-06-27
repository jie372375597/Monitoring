package com.sand.monitoringplatform.pojo;

public class MonitoringConfig {
	
	private Integer id;

	private Integer dbId1;

	private String table1;

	private String column1;

	private String groupbyColumn1;
	
	private String where1;
	
	private String dateColumn1;
	
	private String dateFormat1;
	
	private Integer dateStart1;
	
	private Integer dateContinue1;

	private Integer dbId2;

	private String table2;

	private String column2;

	private String groupbyColumn2;
	
	private String where2;
	
	private String dateColumn2;
	
	private String dateFormat2;
	
	private Integer dateStart2;
	
	private Integer dateContinue2;
	
	private String notnullColumn;

	private String type;

	private Integer status;

	private String workdate;

	private String nextdate;

	private String rate;

	private String insertO;

	private String insertT;

	private String modifyO;

	private String modifyT;

	private String buf;

	private String errorMsg;

	public MonitoringConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MonitoringConfig(Integer id, Integer dbId1, String table1, String column1, String groupbyColumn1,
			String where1, String dateColumn1, String dateFormat1, Integer dateStart1, Integer dateContinue1,
			Integer dbId2, String table2, String column2, String groupbyColumn2, String where2, String dateColumn2,
			String dateFormat2, Integer dateStart2, Integer dateContinue2, String notnullColumn, String type,
			Integer status, String workdate, String nextdate, String rate, String insertO, String insertT,
			String modifyO, String modifyT, String buf, String errorMsg) {
		super();
		this.id = id;
		this.dbId1 = dbId1;
		this.table1 = table1;
		this.column1 = column1;
		this.groupbyColumn1 = groupbyColumn1;
		this.where1 = where1;
		this.dateColumn1 = dateColumn1;
		this.dateFormat1 = dateFormat1;
		this.dateStart1 = dateStart1;
		this.dateContinue1 = dateContinue1;
		this.dbId2 = dbId2;
		this.table2 = table2;
		this.column2 = column2;
		this.groupbyColumn2 = groupbyColumn2;
		this.where2 = where2;
		this.dateColumn2 = dateColumn2;
		this.dateFormat2 = dateFormat2;
		this.dateStart2 = dateStart2;
		this.dateContinue2 = dateContinue2;
		this.notnullColumn = notnullColumn;
		this.type = type;
		this.status = status;
		this.workdate = workdate;
		this.nextdate = nextdate;
		this.rate = rate;
		this.insertO = insertO;
		this.insertT = insertT;
		this.modifyO = modifyO;
		this.modifyT = modifyT;
		this.buf = buf;
		this.errorMsg = errorMsg;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDbId1() {
		return dbId1;
	}

	public void setDbId1(Integer dbId1) {
		this.dbId1 = dbId1;
	}

	public String getTable1() {
		return table1;
	}

	public void setTable1(String table1) {
		this.table1 = table1;
	}

	public String getColumn1() {
		return column1;
	}

	public void setColumn1(String column1) {
		this.column1 = column1;
	}

	public String getGroupbyColumn1() {
		return groupbyColumn1;
	}

	public void setGroupbyColumn1(String groupbyColumn1) {
		this.groupbyColumn1 = groupbyColumn1;
	}

	public String getWhere1() {
		return where1;
	}

	public void setWhere1(String where1) {
		this.where1 = where1;
	}

	public String getDateColumn1() {
		return dateColumn1;
	}

	public void setDateColumn1(String dateColumn1) {
		this.dateColumn1 = dateColumn1;
	}

	public String getDateFormat1() {
		return dateFormat1;
	}

	public void setDateFormat1(String dateFormat1) {
		this.dateFormat1 = dateFormat1;
	}

	public Integer getDateStart1() {
		return dateStart1;
	}

	public void setDateStart1(Integer dateStart1) {
		this.dateStart1 = dateStart1;
	}

	public Integer getDateContinue1() {
		return dateContinue1;
	}

	public void setDateContinue1(Integer dateContinue1) {
		this.dateContinue1 = dateContinue1;
	}

	public Integer getDbId2() {
		return dbId2;
	}

	public void setDbId2(Integer dbId2) {
		this.dbId2 = dbId2;
	}

	public String getTable2() {
		return table2;
	}

	public void setTable2(String table2) {
		this.table2 = table2;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	public String getGroupbyColumn2() {
		return groupbyColumn2;
	}

	public void setGroupbyColumn2(String groupbyColumn2) {
		this.groupbyColumn2 = groupbyColumn2;
	}

	public String getWhere2() {
		return where2;
	}

	public void setWhere2(String where2) {
		this.where2 = where2;
	}

	public String getDateColumn2() {
		return dateColumn2;
	}

	public void setDateColumn2(String dateColumn2) {
		this.dateColumn2 = dateColumn2;
	}

	public String getDateFormat2() {
		return dateFormat2;
	}

	public void setDateFormat2(String dateFormat2) {
		this.dateFormat2 = dateFormat2;
	}

	public Integer getDateStart2() {
		return dateStart2;
	}

	public void setDateStart2(Integer dateStart2) {
		this.dateStart2 = dateStart2;
	}

	public Integer getDateContinue2() {
		return dateContinue2;
	}

	public void setDateContinue2(Integer dateContinue2) {
		this.dateContinue2 = dateContinue2;
	}

	public String getNotnullColumn() {
		return notnullColumn;
	}

	public void setNotnullColumn(String notnullColumn) {
		this.notnullColumn = notnullColumn;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWorkdate() {
		return workdate;
	}

	public void setWorkdate(String workdate) {
		this.workdate = workdate;
	}

	public String getNextdate() {
		return nextdate;
	}

	public void setNextdate(String nextdate) {
		this.nextdate = nextdate;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getInsertO() {
		return insertO;
	}

	public void setInsertO(String insertO) {
		this.insertO = insertO;
	}

	public String getInsertT() {
		return insertT;
	}

	public void setInsertT(String insertT) {
		this.insertT = insertT;
	}

	public String getModifyO() {
		return modifyO;
	}

	public void setModifyO(String modifyO) {
		this.modifyO = modifyO;
	}

	public String getModifyT() {
		return modifyT;
	}

	public void setModifyT(String modifyT) {
		this.modifyT = modifyT;
	}

	public String getBuf() {
		return buf;
	}

	public void setBuf(String buf) {
		this.buf = buf;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "MonitoringConfig [id=" + id + ", dbId1=" + dbId1 + ", table1=" + table1 + ", column1=" + column1
				+ ", groupbyColumn1=" + groupbyColumn1 + ", where1=" + where1 + ", dateColumn1=" + dateColumn1
				+ ", dateFormat1=" + dateFormat1 + ", dateStart1=" + dateStart1 + ", dateContinue1=" + dateContinue1
				+ ", dbId2=" + dbId2 + ", table2=" + table2 + ", column2=" + column2 + ", groupbyColumn2="
				+ groupbyColumn2 + ", where2=" + where2 + ", dateColumn2=" + dateColumn2 + ", dateFormat2="
				+ dateFormat2 + ", dateStart2=" + dateStart2 + ", dateContinue2=" + dateContinue2 + ", notnullColumn="
				+ notnullColumn + ", type=" + type + ", status=" + status + ", workdate=" + workdate + ", nextdate="
				+ nextdate + ", rate=" + rate + ", insertO=" + insertO + ", insertT=" + insertT + ", modifyO=" + modifyO
				+ ", modifyT=" + modifyT + ", buf=" + buf + ", errorMsg=" + errorMsg + "]";
	}

	

	

	

	

	

	

}
