package com.sand.monitoringplatform.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sand.monitoringplatform.pojo.DBConfig;


public class JDBCUtil{
	
	private DBConfig dbConfig;
	
    public JDBCUtil(DBConfig dbConfig) {
		super();
		this.dbConfig = dbConfig;
	}
	/**
     * 得到数据库连接
     * @return
     * @throws Exception
     */
    public Connection getConnection() throws Exception{
    	 String driver =dbConfig.getDriverClassName();
         String url = dbConfig.getUrl();
         String user = dbConfig.getUserName();
         String password = dbConfig.getPassword();
        //1.加载运行时类对象
        Class.forName(driver);
        //2.通过DriverManager得到连接
        Connection connection = DriverManager.getConnection(url,user,password);
        return connection;
        
    }
    /**
     * 释放资源的方法
     * @param connection
     * @param statement      
     * @param resultSet
     */
    public void release(Connection connection,Statement statement,ResultSet resultSet){
        try {
            if(resultSet!=null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
             if(statement!=null){
                 statement.close();
             }
        } catch (Exception e) {
        	e.printStackTrace();
        }
       try{
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    /**
     * 查询数据库的方法
     * @param sql        字符串，要执行的sql语句  如果其中有变量的话，就用  ‘"+变量+"’
     */
    public List<Map<String,Object>> query(String sql, Object ...args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while(resultSet.next()!=false){
                //这里可以执行一些其他的操作
            	Map<String,Object> map = new HashMap<String,Object>();
                for (int i = 1; i <= columnCount; i++) {
                	map.put(resultSet.getMetaData().getColumnName(i), resultSet.getString(i));
                }
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            release(connection, preparedStatement, resultSet);
        }
        return list;
    }
    /**
     * 更新数据库的方法
     * @param sql        字符串，要执行的sql语句  如果其中有变量的话，就用  ‘"+变量+"’
     */
    public int update(String sql, Object ...args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1, args[i]);
            }
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            release(connection, preparedStatement, null);
        }
        return result;
    }
}
