package com.sand.monitoringplatform.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sand.mail.send.SandMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sand.monitoringplatform.dao.DBConfigDao;
import com.sand.monitoringplatform.dao.MonitoringConfigDao;
import com.sand.monitoringplatform.pojo.DBConfig;
import com.sand.monitoringplatform.pojo.MonitoringConfig;
import com.sand.monitoringplatform.pojo.ResultBean;
import com.sand.monitoringplatform.properties.SendMailProperties;
import com.sand.monitoringplatform.service.MonitoringService;
import com.sand.monitoringplatform.util.JDBCUtil;
import com.sand.monitoringplatform.util.SendMailUtil;
import com.sand.monitoringplatform.util.StringUtil;

@Service
public class MonitoringServiceImpl implements MonitoringService {

	@Autowired
	private DBConfigDao dbConfigDao;
	
	@Autowired
	private MonitoringConfigDao monitoringConfigDao;
	
	@Autowired
	private SendMailUtil sendMailUtil;
	
	@Autowired
	private SendMailProperties sendMailProperties;

	@Override
	public ResultBean monitoring(MonitoringConfig monitoringConfig) {
		
		DBConfig db1 = null;
		DBConfig db2 = null;
		ResultBean resultBean = null;
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		try {
			db1 = dbConfigDao.queryById(monitoringConfig.getDbId1().toString());
			db2 = dbConfigDao.queryById(monitoringConfig.getDbId2().toString());
		} catch (Exception e) {
			return new ResultBean(false, "ID:"+monitoringConfig.getId()+",连接数据库错误,请检查db_config中的数据库配置 !");
		}
		List<String> types = Arrays.asList(monitoringConfig.getType().split(","));
		//count(*)
		if(types.contains("1")) {
			if(StringUtil.isEmpty(monitoringConfig.getTable1())||StringUtil.isEmpty(monitoringConfig.getTable2())||StringUtil.isEmpty(monitoringConfig.getRate())) {
				return new ResultBean(false, "ID:"+monitoringConfig.getId()+",type = 1 table列,rate列不能为空 !");
			}
			resultBean = compareByCount(monitoringConfig,db1,db2,date);
			if(!resultBean.isSuccess()) {
				return resultBean;
			}
		}
		//sum() group by
		if(types.contains("2")) {
			if(StringUtil.isEmpty(monitoringConfig.getTable1())||StringUtil.isEmpty(monitoringConfig.getTable2())||StringUtil.isEmpty(monitoringConfig.getRate())||
					StringUtil.isEmpty(monitoringConfig.getColumn1())||StringUtil.isEmpty(monitoringConfig.getColumn2())||
					StringUtil.isEmpty(monitoringConfig.getGroupbyColumn1())||StringUtil.isEmpty(monitoringConfig.getGroupbyColumn2())) {
				return new ResultBean(false, "ID:"+monitoringConfig.getId()+",type = 2 table列,rate列,column列,groupby column列不能为空 !");
			}
			resultBean = compareBySumGroupBy(monitoringConfig,db1,db2,date);
			if(!resultBean.isSuccess()) {
				return resultBean;
			}
		}
		//is not null
		if(types.contains("3")) {
			if(StringUtil.isEmpty(monitoringConfig.getTable2())||StringUtil.isEmpty(monitoringConfig.getRate())||StringUtil.isEmpty(monitoringConfig.getNotnullColumn())) {
				return new ResultBean(false, "ID:"+monitoringConfig.getId()+",type = 3 table列,rate列,notnull column列不能为空 !");
			}
			resultBean = notNull(monitoringConfig,db2,date);
			if(!resultBean.isSuccess()) {
				return resultBean;
			}
		}
		
		try {
			//比对成功,更改数据库信息
			calendar.setTime(date);
			updateMonitoring(monitoringConfig,calendar,"success");
		} catch (Exception e) {
			return new ResultBean(false, "ID:"+monitoringConfig.getId()+",更新数据库错误,请联系系统管理员 !");
		}
		
		return new ResultBean(true, "ID:"+monitoringConfig.getId()+",比对成功 !");
	}

	//count(*)比较
	private ResultBean compareByCount(MonitoringConfig monitoringConfig,DBConfig db1,DBConfig db2,Date date) {
		
		Calendar calendar = Calendar.getInstance();
		
		try {
			//DB1 sql
			JDBCUtil jdbcUtil1 = new JDBCUtil(db1);
			StringBuffer sb1 = new StringBuffer("select count(*) count from " + monitoringConfig.getTable1());
			if(monitoringConfig.getDateColumn1()!=null || "".equals(monitoringConfig.getDateColumn1())) {
				calendar.setTime(date);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-monitoringConfig.getDateStart1(),0, 0, 0);
				SimpleDateFormat sdf1 = new SimpleDateFormat(monitoringConfig.getDateFormat1());
				String startTime = sdf1.format(calendar.getTime());
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+monitoringConfig.getDateContinue1(),0, 0, 0);
				String endTime = sdf1.format(calendar.getTime());
				sb1.append(" where "+monitoringConfig.getDateColumn1() + " >= '" + startTime +"' and "+monitoringConfig.getDateColumn1() + " < '" + endTime +"'");
			}
			if(StringUtil.isNotEmpty(monitoringConfig.getWhere1())) {
				if(StringUtil.isEmpty(monitoringConfig.getDateColumn1())) {
					sb1.append(" where "+monitoringConfig.getWhere1());
				}else {
					sb1.append(" and "+monitoringConfig.getWhere1());
				}
			}
			List<Map<String, Object>> db1query = jdbcUtil1.query(sb1.toString());
			//DB2 sql
			JDBCUtil jdbcUtil2 = new JDBCUtil(db2);
			StringBuffer sb2 = new StringBuffer("select count(*) count from " + monitoringConfig.getTable2());
			if(monitoringConfig.getDateColumn2()!=null || "".equals(monitoringConfig.getDateColumn2())) {
				calendar.setTime(date);
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-monitoringConfig.getDateStart2(),0, 0, 0);
				SimpleDateFormat sdf2 = new SimpleDateFormat(monitoringConfig.getDateFormat2());
				String startTime = sdf2.format(calendar.getTime());
				calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+monitoringConfig.getDateContinue2(),0, 0, 0);
				String endTime = sdf2.format(calendar.getTime());
				sb2.append(" where "+monitoringConfig.getDateColumn2() + " >= '" + startTime +"' and "+monitoringConfig.getDateColumn2() + " < '" + endTime+"'");
			}
			if(StringUtil.isNotEmpty(monitoringConfig.getWhere2())) {
				if(StringUtil.isEmpty(monitoringConfig.getDateColumn2())) {
					sb2.append(" where "+monitoringConfig.getWhere2());
				}else {
					sb2.append(" and "+monitoringConfig.getWhere2());
				}
			}
			List<Map<String, Object>> db2query = jdbcUtil2.query(sb2.toString());
			//为空校验
			if(db1query == null || db1query.isEmpty()) {
				return new ResultBean(false, "ID:"+monitoringConfig.getId()+",count(*) sql错误:"+sb1.toString());
			}
			if(db2query == null || db2query.isEmpty()) {
				return new ResultBean(false, "ID:"+monitoringConfig.getId()+",count(*) sql错误:"+sb2.toString());
			}
			//由于数据库的多样性,查询结果map转小写
			Map<String, Object> map1 = transformUpperCase(db1query.get(0));
			Map<String, Object> map2 = transformUpperCase(db2query.get(0));
			//对比
			if (map1.equals(map2)) {
				return new ResultBean(true);
			}else {
				return new ResultBean(false,"ID:"+monitoringConfig.getId()+",count(*)不匹配! \r\n"+sb1.toString()+"; \r\n"+sb2.toString()+"; \r\n "+map1.toString()+" \r\n "+map2.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean(false, "ID:"+monitoringConfig.getId()+",count(*)错误:"+e.toString());
		}
	}
	
	//sum group by比较
		private ResultBean compareBySumGroupBy(MonitoringConfig monitoringConfig,DBConfig db1,DBConfig db2,Date date) {
			
			Calendar calendar = Calendar.getInstance();
			
			try {
				//DB1 sql
				JDBCUtil jdbcUtil1 = new JDBCUtil(db1);
				StringBuffer sb1 = new StringBuffer("select ");
				String[] columns1 = monitoringConfig.getColumn1().split(",");
				for (int i = 0; i < columns1.length; i++) {
					if(i==columns1.length-1) {
						sb1.append(columns1[i]);
					}else {
						sb1.append(columns1[i]+",");
					}
				}
				sb1.append(" from "+monitoringConfig.getTable1());
				if(monitoringConfig.getDateColumn1()!=null || "".equals(monitoringConfig.getDateColumn1())) {
					calendar.setTime(date);
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-monitoringConfig.getDateStart1(),0, 0, 0);
					SimpleDateFormat sdf1 = new SimpleDateFormat(monitoringConfig.getDateFormat1());
					String startTime = sdf1.format(calendar.getTime());
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+monitoringConfig.getDateContinue1(),0, 0, 0);
					String endTime = sdf1.format(calendar.getTime());
					sb1.append(" where "+monitoringConfig.getDateColumn1() + " >= '" + startTime +"' and "+monitoringConfig.getDateColumn1() + " < '" + endTime +"'");
				}
				if(StringUtil.isNotEmpty(monitoringConfig.getWhere1())) {
					if(StringUtil.isEmpty(monitoringConfig.getDateColumn1())) {
						sb1.append(" where "+monitoringConfig.getWhere1());
					}else {
						sb1.append(" and "+monitoringConfig.getWhere1());
					}
				}
				sb1.append(" group by ");
				String[] groupbycolumns1 = monitoringConfig.getGroupbyColumn1().split(",");
				for (int i = 0; i < groupbycolumns1.length; i++) {
					if(i==groupbycolumns1.length-1) {
						sb1.append(groupbycolumns1[i]);
					}else {
						sb1.append(groupbycolumns1[i]+",");
					}
				}
				List<Map<String, Object>> db1query = jdbcUtil1.query(sb1.toString());
				//DB2 sql
				JDBCUtil jdbcUtil2 = new JDBCUtil(db2);
				StringBuffer sb2 = new StringBuffer("select ");
				String[] columns2 = monitoringConfig.getColumn2().split(",");
				for (int i = 0; i < columns2.length; i++) {
					if(i==columns2.length-1) {
						sb2.append(columns2[i]);
					}else {
						sb2.append(columns2[i]+",");
					}
				}
				sb2.append(" from "+monitoringConfig.getTable2());
				if(monitoringConfig.getDateColumn2()!=null || "".equals(monitoringConfig.getDateColumn2())) {
					calendar.setTime(date);
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-monitoringConfig.getDateStart2(),0, 0, 0);
					SimpleDateFormat sdf2 = new SimpleDateFormat(monitoringConfig.getDateFormat2());
					String startTime = sdf2.format(calendar.getTime());
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+monitoringConfig.getDateContinue2(),0, 0, 0);
					String endTime = sdf2.format(calendar.getTime());
					sb2.append(" where "+monitoringConfig.getDateColumn2() + " >= '" + startTime +"' and "+monitoringConfig.getDateColumn2() + " < '" + endTime+"'");
				}
				if(StringUtil.isNotEmpty(monitoringConfig.getWhere2())) {
					if(StringUtil.isEmpty(monitoringConfig.getDateColumn2())) {
						sb2.append(" where "+monitoringConfig.getWhere2());
					}else {
						sb2.append(" and "+monitoringConfig.getWhere2());
					}
				}
				sb2.append(" group by ");
				String[] groupbycolumns2 = monitoringConfig.getGroupbyColumn2().split(",");
				for (int i = 0; i < groupbycolumns2.length; i++) {
					if(i==groupbycolumns2.length-1) {
						sb2.append(groupbycolumns2[i]);
					}else {
						sb2.append(groupbycolumns2[i]+",");
					}
				}
				List<Map<String, Object>> db2query = jdbcUtil2.query(sb2.toString());
				//为空校验
				if(db1query == null ) {
					return new ResultBean(false, "ID:"+monitoringConfig.getId()+",sum group by sql错误:"+sb1.toString());
				}
				if(db2query == null ) {
					return new ResultBean(false, "ID:"+monitoringConfig.getId()+",sum group by sql错误:"+sb2.toString());
				}
				
				if(db1query.isEmpty() && db2query.isEmpty()) {
					return new ResultBean(true);
				}
				
				if(db1query.size() != db2query.size()) {
					return new ResultBean(false,"ID:"+monitoringConfig.getId()+",sum group by不匹配! \r\n"+sb1.toString()+"; \r\n"+sb2.toString()+"; \r\n "+db1query.toString()+" \r\n "+db2query.toString());
				}
				//对比
				int size = 0;
				for (Map<String, Object> map1 : db1query) {
					for (Map<String, Object> map2 : db2query) {
						//由于数据库的多样性,查询结果map转小写
						if(transformUpperCase(map1).equals(transformUpperCase(map2))) {
							size++;
							continue;
						}
					}
				}
				if(size==db1query.size()) {
					return new ResultBean(true);
				}else {
					return new ResultBean(false,"ID:"+monitoringConfig.getId()+",sum group by不匹配! \r\n"+sb1.toString()+"; \r\n"+sb2.toString()+"; \r\n "+db1query.toString()+" \r\n "+db2query.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultBean(false, "ID:"+monitoringConfig.getId()+",sum group by错误:"+e.toString());
			}
		}
		
		//not null
		private ResultBean notNull(MonitoringConfig monitoringConfig,DBConfig db,Date date) {
			
			Calendar calendar = Calendar.getInstance();
			
			try {
				//DB sql
				JDBCUtil jdbcUtil = new JDBCUtil(db);
				StringBuffer sb = new StringBuffer("select count(*) count from " + monitoringConfig.getTable2()+" where ("+monitoringConfig.getNotnullColumn() + " is null or trim("+monitoringConfig.getNotnullColumn()+")='') ");
				if(monitoringConfig.getDateColumn2()!=null || "".equals(monitoringConfig.getDateColumn2())) {
					calendar.setTime(date);
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-monitoringConfig.getDateStart2(),0, 0, 0);
					SimpleDateFormat sdf2 = new SimpleDateFormat(monitoringConfig.getDateFormat2());
					String startTime = sdf2.format(calendar.getTime());
					calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)+monitoringConfig.getDateContinue2(),0, 0, 0);
					String endTime = sdf2.format(calendar.getTime());
					sb.append(" and "+monitoringConfig.getDateColumn2() + " >= '" + startTime +"' and "+monitoringConfig.getDateColumn2() + " < '" + endTime + "'");
				}
				if(StringUtil.isNotEmpty(monitoringConfig.getWhere2())) {
					if(StringUtil.isEmpty(monitoringConfig.getDateColumn2())) {
						sb.append(" where "+monitoringConfig.getWhere2());
					}else {
						sb.append(" and "+monitoringConfig.getWhere2());
					}
				}
				List<Map<String, Object>> dbquery = jdbcUtil.query(sb.toString());
				//为空校验
				if(dbquery == null || dbquery.isEmpty()) {
					return new ResultBean(false, "ID:"+monitoringConfig.getId()+",not null sql错误:"+sb.toString());
				}
				//由于数据库的多样性,查询结果map转小写
				Map<String, Object> map = transformUpperCase(dbquery.get(0));
				//对比
				String count = (String) (transformUpperCase(map).get("count"));
				
				if ("0".equals(count)) {
					return new ResultBean(true);
				}else {
					return new ResultBean(false,"ID:"+monitoringConfig.getId()+",非空条件存在查询结果,请检查 ! \r\n"+sb.toString()+"; \r\n "+map.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new ResultBean(false, "ID:"+monitoringConfig.getId()+",not null错误:"+e.toString());
			}
		}
		
		public void writeErrorMsg(MonitoringConfig monitoringConfig,ResultBean result) {
			Calendar calendar = Calendar.getInstance();
			updateMonitoring(monitoringConfig,calendar,result.getMsg());
		}
		
		public void sendMsg(String subject,String msg) {
			sendMailUtil.sendMail(subject, msg, sendMailProperties.getDefaultTo(), sendMailProperties.getDefaultCc());
		}
		
		public void sendErrorMsg(String subject,String msg) {
			sendMailUtil.sendMail(subject, msg, sendMailProperties.getErrorSuccessTo(), sendMailProperties.getErrorSuccessCc());
		}
		
		private int updateMonitoring(MonitoringConfig monitoringConfig,Calendar calendar,String msg) {
			String sql = "update monitoring_config m set m.error_msg = ?, m.workdate = ?,m.nextdate = ? where m.id = ?";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String workDate = sdf.format(calendar.getTime());
			calendar.add(Calendar.MINUTE,  new Integer(monitoringConfig.getRate()));
			String nextDate = sdf.format(calendar.getTime());
			String id = monitoringConfig.getId().toString();
			List<String> params = new ArrayList<String>();
			params.add(msg);
			params.add(workDate);
			params.add(nextDate);
			params.add(id);
		    int update = monitoringConfigDao.update(sql, params);
		    return update;
		}

	
	//map中key转小写工具
	private Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
		Map<String, Object> resultMap = new HashMap<>();

		if (orgMap == null || orgMap.isEmpty()) {
			return resultMap;
		}

		Set<String> keySet = orgMap.keySet();
		for (String key : keySet) {
			String newKey = key.toLowerCase();
			newKey = newKey.replace("_", "");

			resultMap.put(newKey, orgMap.get(key));
		}
		return resultMap;

	}

}
