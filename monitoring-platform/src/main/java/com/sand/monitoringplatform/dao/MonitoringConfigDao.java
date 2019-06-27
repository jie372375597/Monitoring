package com.sand.monitoringplatform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sand.monitoringplatform.pojo.MonitoringConfig;

@Service
public class MonitoringConfigDao {

	@Resource
    private JdbcTemplate jdbcTemplate;
	
	public List<MonitoringConfig> queryAll() {
		String sql = "SELECT *  FROM MONITORING_CONFIG WHERE STATUS = '0' ";
		List<MonitoringConfig> queryForList = jdbcTemplate.query(sql, new RowMapper<MonitoringConfig>() {
			@Override
			public MonitoringConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
				MonitoringConfig monitoringConfig = new MonitoringConfig();
				monitoringConfig.setId(rs.getInt("ID"));
				monitoringConfig.setDbId1(rs.getInt("DB_ID1"));
				monitoringConfig.setTable1(rs.getString("TABLE1"));
				monitoringConfig.setColumn1(rs.getString("COLUMN1"));
				monitoringConfig.setGroupbyColumn1(rs.getString("GROUPBY_COLUMN1"));
				monitoringConfig.setWhere1(rs.getString("WHERE1"));
				monitoringConfig.setDateColumn1(rs.getString("DATE_COLUMN1"));
				monitoringConfig.setDateFormat1(rs.getString("DATE_FORMAT1"));
				monitoringConfig.setDateStart1(rs.getInt("DATE_START1"));
				monitoringConfig.setDateContinue1(rs.getInt("DATE_CONTINUE1"));
				monitoringConfig.setDbId2(rs.getInt("DB_ID2"));
				monitoringConfig.setTable2(rs.getString("TABLE2"));
				monitoringConfig.setColumn2(rs.getString("COLUMN2"));
				monitoringConfig.setGroupbyColumn2(rs.getString("GROUPBY_COLUMN2"));
				monitoringConfig.setWhere2(rs.getString("WHERE2"));
				monitoringConfig.setDateColumn2(rs.getString("DATE_COLUMN2"));
				monitoringConfig.setDateFormat2(rs.getString("DATE_FORMAT2"));
				monitoringConfig.setDateStart2(rs.getInt("DATE_START2"));
				monitoringConfig.setDateContinue2(rs.getInt("DATE_CONTINUE2"));
				monitoringConfig.setNotnullColumn(rs.getString("NOTNULL_COLUMN"));
				monitoringConfig.setType(rs.getString("TYPE"));
				monitoringConfig.setStatus(rs.getInt("STATUS"));
				monitoringConfig.setWorkdate(rs.getString("WORKDATE"));
				monitoringConfig.setNextdate(rs.getString("NEXTDATE"));
				monitoringConfig.setRate(rs.getString("RATE"));
				monitoringConfig.setInsertO(rs.getString("INSERT_O"));
				monitoringConfig.setInsertT(rs.getString("INSERT_T"));
				monitoringConfig.setModifyO(rs.getString("MODIFY_O"));
				monitoringConfig.setModifyT(rs.getString("MODIFY_T"));
				monitoringConfig.setBuf(rs.getString("BUF"));
				monitoringConfig.setErrorMsg(rs.getString("ERROR_MSG"));
				
				return monitoringConfig;
			}
		});

		return queryForList;
	}
	
	//更新数据库
	public int  update(String sql,List params) {
		Object[] args = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			args[i]=params.get(i);
		}
		int temp = jdbcTemplate.update(sql, args); 
		return temp;
	}


}
