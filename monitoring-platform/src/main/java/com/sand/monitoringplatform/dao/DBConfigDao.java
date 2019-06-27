package com.sand.monitoringplatform.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sand.monitoringplatform.pojo.DBConfig;

@Service
public class DBConfigDao {

	@Resource
    private JdbcTemplate jdbcTemplate;
	
	public List<DBConfig> queryAll() {
		String sql = "SELECT *  FROM DB_Config";
		List<DBConfig> queryForList = jdbcTemplate.query(sql, new RowMapper<DBConfig>() {
			@Override
			public DBConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
				DBConfig dbConfig = new DBConfig();
				dbConfig.setId(rs.getInt("ID"));
				dbConfig.setDriverClassName(rs.getString("DRIVERCLASSNAME"));
				dbConfig.setUrl(rs.getString("URL"));
				dbConfig.setUserName(rs.getString("USERNAME"));
				dbConfig.setPassword(rs.getString("PASSWORD"));
				return dbConfig;
			}
		});

		return queryForList;
	}
	public DBConfig queryById(String id) {
		String sql = "SELECT *  FROM DB_Config where id = ?";
		DBConfig queryForObject = jdbcTemplate.queryForObject(sql, new Object[] {id}, new RowMapper<DBConfig>() {
			@Override
			public DBConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
				DBConfig dbConfig = new DBConfig();
				dbConfig.setId(rs.getInt("ID"));
				dbConfig.setDriverClassName(rs.getString("DRIVERCLASSNAME"));
				dbConfig.setUrl(rs.getString("URL"));
				dbConfig.setUserName(rs.getString("USERNAME"));
				dbConfig.setPassword(rs.getString("PASSWORD"));
				return dbConfig;
			}
		});
		return queryForObject;
	}
}
