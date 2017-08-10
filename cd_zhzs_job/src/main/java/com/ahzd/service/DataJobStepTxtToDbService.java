package com.ahzd.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.ahcd.common.DBconn;
import com.ahcd.common.ReadTxt;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.OperateResult;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepDbToTxt;
import com.ahzd.pojo.DataJobStepTxtToDb;

@Service("TxtToDbService")
public class DataJobStepTxtToDbService {
	@Autowired
	private DataJobMongoDao dataJobMongoDao;
	/**
	 * 
	 * 功能说明 :批量增加数据
	 * 
	 * @param list
	 * @param newMap
	 * @param tableName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean executeIns(List<String[]> list, Map<String, String> newMap, String tableName,
			String dataSourceId) throws ClassNotFoundException, SQLException {
		Connection con = DBconn.getConnectionById(dataSourceId);
		StringBuffer sql = ReadTxt.getNewInsertSql(newMap, tableName);
		System.out.println(sql);
		con.setAutoCommit(false);
		Long startTime = System.currentTimeMillis();
		PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql.toString());
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(0).length; j++) {
				String a = list.get(i)[ReadTxt.getCorner(newMap, j)];
				pst.setString(j + 1, a);
			}
			pst.addBatch();
		}
		pst.executeBatch();
		con.commit();
		Long endTime = System.currentTimeMillis();
		System.out.println("用时：" + (endTime - startTime));
		pst.close();
		con.close();
		return true;
	}

	/**
	 * 
	 * 功能说明 : txt导入数据库
	 * 
	 * @param tBean
	 * @return 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static OperateResult txtToDb(DataJobStepTxtToDb txtBean) {
		OperateResult opreateResult = new OperateResult();
		List<String[]> list = null;
		boolean flag = true;
		Map<String, String> txtMap = new HashMap<String, String>();
		String[] str = null;
		try {
			str = ReadTxt.getHead(new FileInputStream(new File(txtBean.getFilePath())), txtBean.getSeparator());
		} catch (FileNotFoundException e2) {
			flag = false;
			opreateResult.setResult(flag);
			opreateResult.setMsg(e2.getMessage());
			return opreateResult;
		} catch (IOException e2) {
			flag = false;
			opreateResult.setResult(flag);
			opreateResult.setMsg(e2.getMessage());
			return opreateResult;
		}
		txtMap = ReadTxt.getNewMap(txtBean.getParamMap(), str);
		try {
			list = ReadTxt.readText(txtBean);
		} catch (IOException e1) {
			flag = false;
			opreateResult.setResult(flag);
			opreateResult.setMsg(e1.getMessage());
			return opreateResult;
		}
		for (int i = 0; i < list.size(); i++) {
			if (StringUtil.isBlank(list.get(i)[0])) {
				list.remove(list.get(i));
			}
		}
		try {
			flag = executeIns(list, txtMap, txtBean.getTableName(), txtBean.getDatasourceId());
		} catch (Exception e) {
			flag = false;
			opreateResult.setResult(flag);
			opreateResult.setMsg(e.getMessage());
			return opreateResult;
		}
		opreateResult.setResult(flag);
		opreateResult.setMsg("导入成功!");
		return opreateResult;
	}
	
	
	
	/**
	 * 
	 * 功能说明 : 查询 并返回list
	 * 
	 * @param dUtil
	 * @param txtMap
	 * @param tableName
	 * @param sep
	 * @return
	 * @throws SQLException 
	 */
	public static List<String> executeQueryBytxt(DataJobStepDbToTxt txtBean,String dataSourceId) throws SQLException {
		String sqlStr = ReadTxt.getSelectSql(txtBean.getParamMap(), txtBean.getSql());
		String headStr = ReadTxt.getHeadStr(txtBean.getParamMap(), txtBean.getSeparator());
		Connection con = DBconn.getConnectionById(dataSourceId);
		ResultSet rs = con.createStatement().executeQuery(sqlStr.toString());
		List<String> list = new ArrayList<String>();
		list.add(headStr);
		int rsNum = 1;
			while (rs.next()) {
				if (rsNum >= txtBean.getBeginRowIndex()) {
					String str = "";
					for (int i = 1; i < txtBean.getParamMap().size() + 1; i++) {
						str += rs.getString(i) + txtBean.getSeparator();
					}
					System.out.println(str.substring(0, str.length() - txtBean.getSeparator().length()) + "\r\n");
					list.add(str.substring(0, str.length() - txtBean.getSeparator().length()) + "\r\n");
				}
				rsNum++;
			}
		return list;
	}
	
	/**
	 * 
	 * 功能说明 : txt导出数据库
	 * @param tBean
	 * @return
	 */
	public static OperateResult dbtoTxt(DataJobStepDbToTxt txtBean) {
		boolean flag = true;
		OperateResult opreateResult = new OperateResult();
		if (StringUtil.isBlank(txtBean.getSeparator()) ||txtBean.getSeparator() != null) {
			txtBean.setSeparator(ReadTxt.checkSep(txtBean.getSeparator()));
		}
		List<String> list = null;
		try {
			list = executeQueryBytxt(txtBean,txtBean.getDatasourceId());
		} catch (SQLException e1) {
			flag = false;
			opreateResult.setResult(flag);
			opreateResult.setSql(txtBean.getSql());
			opreateResult.setMsg(e1.getMessage());
			return opreateResult;
		}
		try {
			ReadTxt.isexitsPath(txtBean.getFilePath());
			File file = new File(txtBean.getFilePath());
			flag = ReadTxt.writeTxtFile(file, list);
		} catch (Exception e) {
			flag = false;
			opreateResult.setResult(flag);
			opreateResult.setSql(txtBean.getSql());
			opreateResult.setMsg(e.getMessage());
			return opreateResult;
		}
		opreateResult.setResult(flag);
		opreateResult.setMsg("导出成功!");
		return opreateResult;
	}
	
	/**
	 * 修改TXT导入
	 * @param dbFtp
	 * @param jobId
	 * @return
	 */
	public int updateJobStepTxtUpload(DataJobStepTxtToDb dbTxt,String jobId){
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(dbTxt.getStepId()));
		Update update = new Update();
		update.set("steps.$.beginRowIndex", dbTxt.getBeginRowIndex());
		update.set("steps.$.endRowIndex", dbTxt.getEndRowIndex());
		update.set("steps.$.filePath", dbTxt.getFilePath());
		update.set("steps.$.tableName", dbTxt.getTableName());
		update.set("steps.$.separator", dbTxt.getSeparator());
		update.set("steps.$.fileNamePatterns", dbTxt.getFileNamePatterns());
		update.set("steps.$.stepId", dbTxt.getStepId());
		update.set("steps.$.name", dbTxt.getName());
		update.set("steps.$.sortNum", dbTxt.getSortNum());
		update.set("steps.$.type", dbTxt.getType());
		update.set("steps.$.datasourceId", dbTxt.getDatasourceId());
		update.set("steps.$.paramMap", dbTxt.getParamMap());
		DataJob job = dataJobMongoDao.findAndModify(query, update);
		if(job == null){
			return -1 ;
		}
		return 1 ;
	}

	/**
	 * 修改TXT导出
	 * @param dbFtp
	 * @param jobId
	 * @return
	 */
	public int updateJobStepTxtDownLoad(DataJobStepDbToTxt dbTxt, String jobId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(dbTxt.getStepId()));
		Update update = new Update();
		update.set("steps.$.filePath", dbTxt.getFilePath());
		update.set("steps.$.separator", dbTxt.getSeparator());
		update.set("steps.$.sql", dbTxt.getSql());
		update.set("steps.$.beginRowIndex", dbTxt.getBeginRowIndex());
		update.set("steps.$.fileNamePatterns", dbTxt.getFileNamePatterns());
		update.set("steps.$.stepId", dbTxt.getStepId());
		update.set("steps.$.name", dbTxt.getName());
		update.set("steps.$.sortNum", dbTxt.getSortNum());
		update.set("steps.$.type", dbTxt.getType());
		update.set("steps.$.datasourceId", dbTxt.getDatasourceId());
		update.set("steps.$.paramMap", dbTxt.getParamMap());
		DataJob job = dataJobMongoDao.findAndModify(query, update);
		if(job == null){
			return -1 ;
		}
		return 1 ;
	}
}
