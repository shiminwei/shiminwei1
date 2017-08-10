package com.ahzd.service;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.ahcd.common.DBconn;
import com.ahcd.pojo.OperateResult;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.pojo.DataJobStepSqlScripts;

@Service("dataJobStepSqlScriptsService")
public class DataJobStepSqlScriptsService {
	@Autowired
	private DataJobMongoDao dataJobMongoDao;
	
	/**
	 * 功能说明 :根据JobId和StepId查询特定步骤
	 * */
	@SuppressWarnings("unchecked")
	public <T> T selectJobStep(String jobId,String stepId,Class<T> clazz) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		DataJob job = dataJobMongoDao.findOne(query);
		if(job == null){
			return null ;
		}
		List<DataJobStepBase> list = (List<DataJobStepBase>) job.getSteps();
		for(DataJobStepBase step : list){
			if(stepId != null && stepId.equals(step.getStepId())){
				return (T)step;
			}
		}
		return null;
	}

	/**
	 * 功能说明 :SQL脚本--UPDATE 
	 * */
	public  int updateJobStep(DataJobStepSqlScripts bean,String _id){
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(_id));
		query.addCriteria(Criteria.where("steps.stepId").is(bean.getStepId()));
		Update update = new Update();
		update.set("steps.$.name", bean.getName());
		update.set("steps.$.datasourceId", bean.getDatasourceId());
		update.set("steps.$.sql", bean.getSql());
		update.set("steps.$.paramMap", bean.getParamMap());
		DataJob job = dataJobMongoDao.findAndModify(query, update);
		if(job == null){
			return -1 ;
		}
		return 1 ;
	}
	
	/**
	 * 功能说明 :SQL脚本--checkSQL
	 * */
	public String checkSql(DataJobStepSqlScripts bean) {
		OperateResult result = new OperateResult();
		Connection con = null;
		Map<String,String> map = null;
		try {
			String[] slist = bean.getSql().split(";");
			for(String sql : slist){
				if("".equals(sql.trim())){
					continue ;
				}
				if(map != null){
					map.clear();
				}
				String[] temp = sql.split("@");
				for(int i=0;i< temp.length;i++){
					if(i%2 == 1){
						if(map == null){
							map = new HashMap<String, String>();
						}
						String[] stemp = null ;
						if(temp[i] != null){
							stemp = temp[i].split("#");
						}
						if(stemp != null && stemp.length >1){
							map.put("@"+temp[i]+"@", stemp[1]);
						}else{
							//验证语句是否正确时,参数默认值赋值为0
							map.put("@"+temp[i]+"@", "0");
						}
					}
				}
				if(con == null){
					con = DBconn.getConnectionById(bean.getDatasourceId());
				}
				//存储过程以英文状态下即##开头
				if("##".equals(sql.trim().substring(0, 2))){
					sql = sql.replaceAll("##", "");
					result = doProcedure(con, sql,map);
				}else{
					result = doSql(con, sql,map);
				}
				if(result  == null || !result.isResult()){
					if(result  == null){
						return "执行SQL语句发生了未知异常";
					}
					return result.getMsg();
				}
			}
			return "true";
		}finally{
			try {
				if (con != null) {
					con.close();
				}			
			} catch (SQLException e) {
				e.printStackTrace();
				return "关闭数据库连接发生异常";
			}
		}
	}
		
	/**
	 * 功能说明 :SQL脚本--执行--测试
	 * */
	public  OperateResult excute(String jobId,String stepId){
		DataJobStepSqlScripts sqlScripts = this.selectJobStep(jobId, stepId, DataJobStepSqlScripts.class);
		return excute(sqlScripts);
	}
	
	/**
	 * 功能说明 :SQL脚本--执行--调度
	 * */
	public  OperateResult excute(DataJobStepSqlScripts bean){
		OperateResult result = new OperateResult();
		result.setResult(false);
		if(bean == null){
			result.setMsg("此步骤已经被删除了");
			return result;
		}
		String sql = bean.getSql();
		if("".equals(sql) || sql ==null){
			result.setMsg("此步骤SQL为空");
			return result ;
		}
		String[] slist = sql.split(";");
		Connection con = null;
		try {
			for (String tsql : slist) {
				if ("".equals(tsql.trim())) {
					continue;
				}
				if(con == null){
					con = DBconn.getConnectionById(bean.getDatasourceId());
				}
				if ("##".equals(tsql.trim().substring(0, 2))) {
					tsql = tsql.replaceAll("##", "");
					result = doProcedure(con, tsql,bean.getParamMap());
				} else {
					result = doSql(con, tsql,bean.getParamMap());
				}
				if (result == null || !result.isResult()) {
					if(result == null){
						result = new OperateResult();
						result.setResult(false);
					}
					return result ;
				}
			}
			result.setResult(true);
		} catch (SecurityException e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMsg(e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMsg(e.getMessage());
		} finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result.setResult(false);
					result.setMsg(e.getMessage());
				}
			}
		}
		return result;
	}
	
	/** 执行SQL语句
	 * */
	private OperateResult doSql(Connection con,String sql,Map<String,String> map){
		OperateResult result = new OperateResult();
		//给占位符赋值
		if(map != null && map.size() >0){
			for(Entry<String, String> entry : map.entrySet()){
				if(entry.getKey()== null || entry.getValue()== null){
					result.setResult(false);
					result.setMsg("传入的参数值为null");
					return result ;
				}
				sql = sql.replaceFirst(entry.getKey(), entry.getValue());
			}
		}
		Statement statement = null;
		try {
			statement = con.createStatement();
			if("select".equals(sql.trim().substring(0, 6).toLowerCase())){
				statement.executeQuery(sql);
			}else{
				statement.executeUpdate(sql.toString());
			}
			result.setResult(true);
		} catch (SQLException e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMsg(e.getMessage());
		}finally{
			if(statement != null ){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result.setResult(false);
					result.setMsg(e.getMessage());
				}
			}
		}
		return result ;
	}
	
	/** 执行存储过程语句
	 * */
	private OperateResult doProcedure(Connection con,String sql,Map<String,String> map){
		OperateResult result = new OperateResult();
		List<String> param = new ArrayList<String>();
		sql = getProcedureSql(param,sql);
		CallableStatement  call = null ;
		try {
			call = con.prepareCall(sql);
			if(param.size() > 0){
				result = setParams(param,map,call);
				if(!result.isResult()){
					return result ;
				}
			}
			call.execute();
			result.setResult(true);
		} catch (SQLException e) {
			e.printStackTrace();
			result.setResult(false);
			result.setMsg(e.getMessage());
		}finally{
			if(call != null){
				try {
					call.close();
				} catch (SQLException e) {
					e.printStackTrace();
					result.setResult(false);
					result.setMsg(e.getMessage());
				}
			}
		}
		return result ;
	}
	/** 拼接存储过程和获得参数KEY值
	 * */
	private String getProcedureSql(List<String> list,String sql){		
		String[] slist = sql.split("@");
		if(slist.length > 1){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i< slist.length;i++){
				if(i%2 == 1){
					sb.append("?");
					list.add("@"+slist[i]+"@");
					continue ;
				}
				sb.append(slist[i]);
			}
			sql = sb.toString();
		}
		return sql;
	}
	/**给?赋值
	 * */
	private OperateResult setParams(List<String> param ,Map<String,String> map ,CallableStatement call) throws SQLException{
		OperateResult result = new OperateResult();	
		for(int i=0; i<param.size(); i++){
			String temp = param.get(i).replaceAll("@", "");
			String[] temps = temp.split("_");
			if(temps.length <3){
				result.setResult(false);
				result.setMsg("存储过程占位符拼接有误");
				return result;
			}
			if(temps[0]==null || "".equals(temps[0].trim()) || temps[1]==null || "".equals(temps[1].trim())){
				result.setResult(false);
				result.setMsg("存储过程占位符格式不正确,正确格式'输入输出_值类型_唯一标记'");
				return result;
			}
			setParam(i+1,temps[0].trim().toUpperCase(),temps[1].trim().toUpperCase(),map.get(param.get(i)),call);
		}
		result.setResult(true);
		return result ;
	}
		
	//存储过程参数赋值
	private void setParam(int index ,String inOut,String datatype,String value,CallableStatement call) throws SQLException{
		if("IN".equals(inOut)){
			setInParam(index,datatype,value,call);
		}else if(inOut != null && inOut.startsWith("OUT")){
			setOutParam(index,datatype,call);
		}else if("INOUT".equals(inOut)){
			setInParam(index,datatype,value,call);
			setOutParam(index,datatype,call);
		}else{
			throw new SQLException("存储过程参数输入输出类型不正确");
		}
	}
	//存储过程输入参数
	private void setInParam(int index ,String datatype,String value,CallableStatement call) throws SQLException{
		if(datatype.contains("CHAR")){
			call.setString(index, value);
		}else if("NUMBER".equals(datatype) || "NUMERIC".equals(datatype)){
			call.setBigDecimal(index, new BigDecimal(value));
		}else if(datatype.startsWith("INT")){
			call.setInt(index,Integer.parseInt(value));
		}else if("FLOAT".equals(datatype)){
			call.setFloat(index,Float.parseFloat(value));
		}else if("DATE".equals(datatype)){
			call.setDate(index, Date.valueOf(value));
		}else if("LONG".equals(datatype)){
			call.setLong(index,Integer.parseInt(value));
		}else{
			throw new SQLException("存储过程参数值类型不正确或代码中无此类参数值类型");
		}
	}
	//存储过程输出参数
	private void setOutParam(int index ,String datatype,CallableStatement call) throws SQLException{
		if(datatype.contains("CHAR")){
			call.registerOutParameter(index, java.sql.Types.VARCHAR);
		}else if("NUMBER".equals(datatype) || "NUMERIC".equals(datatype)){
			call.registerOutParameter(index, java.sql.Types.NUMERIC);
		}else if("FLOAT".equals(datatype)){
			call.registerOutParameter(index, java.sql.Types.FLOAT);
		}else if(datatype.startsWith("INT")){
			call.registerOutParameter(index, java.sql.Types.INTEGER);
		}else{
			throw new SQLException("存储过程参数值类型不正确或代码中无此类参数值类型");
		}
	}
}