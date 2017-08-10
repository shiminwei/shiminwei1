package com.ahcd.common;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.FieldBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ProcedureParameterBean;
import com.ahcd.pojo.TableBean;
import com.ahcd.pojo.TableColumnBean;
import com.ahcd.pojo.TableRowBean;
import com.ahcd.service.ConfigService;
import com.ahcd.service.impl.JsonConfigServiceImpl;
import com.ahzd.service.DataSourceBeanService;


/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月30日 下午2:13:37 类说明
 */

public class DbUntil {
	

	private static DataSourceBeanService dataSourceBeanService;
	@Autowired 
	public void setDataSourceBeanService(DataSourceBeanService dataSourceBeanService){ 
		DbUntil.dataSourceBeanService = dataSourceBeanService; 
	} 
	@Resource
	private ConfigService configService;

	/**
	 * 
	* @Title: 根据XML配置文件查询数据库返回分页对象
	* @author: feiyang
	* @date: 2016年10月8日 下午1:14:02 
	* @param bean	XML配置文件
	* @param page   分页
	* @param map	查询条件
	* @return:
	 */
	public static Page<String[]> toQuery(ConfigBean bean, Page<String[]> page, Map<String, String[]> map) {
		Connection conn = DBconn.getConnectionById(bean.getDatasource());
		Statement stmt = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();// 结果集
		List<String> totalaList = new ArrayList<String>();// 合计集合
		List<String> subtotalaList = new ArrayList<String>();// 小计集合
		int totalCount = 0;
		String sql = getNewSql(bean.getResults().getSql(), map);
		String rownumSql = getSqlByRownum(sql, (page.getPageNum() - 1) * page.getNumPerPage(),
				page.getPageNum() * page.getNumPerPage());
		if (conn != null) {
			try {
				System.out.println("查询SQL:===>" + rownumSql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(rownumSql);
				ResultSetMetaData data = rs.getMetaData();
				while (rs.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data.getColumnCount(); i++) {
						if (rs.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs.getObject(i) + "@");
						}
					}
					list.add(strb.toString().substring(0, strb.length() - 1).split("@"));
				}
				totalCount = getContent(sql, stmt);
				for (int i = 0; i < bean.getResults().getFieldBeans().size(); i++) {// 获取合计小计
					String totalaStr = "";
					String subtotalaStr = "";
					if (bean.getResults().getFieldBeans().get(i).getTotalaggr().equals("1")) {
						totalaStr = getTotala(sql, stmt, bean.getResults().getFieldBeans().get(i).getSqlFielName());
					}

					// if
					// (bean.getResults().getFieldBeans().get(i).getSubtotalaggr().equals("1"))
					// {
					// subtotalaStr = getTotala(rownumSql, stmt,
					// bean.getResults().getFieldBeans().get(i).getSqlFielName());
					// }
					if (!bean.getResults().getFieldBeans().get(i).getHidden().equals("1")) {
						totalaList.add(totalaStr);
						subtotalaList.add(subtotalaStr);
					}
				}

				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		page.setTotalCount(totalCount);
		page.setTotalaList(totalaList);
		page.setSubtotalaList(subtotalaList);
		page.setResult(toCheckResultList(list, bean.getResults().getFieldBeans()));
		return page;
	}

	/**
	 * 
	 * 功能说明 :获取数据条数
	 * 
	 * @author : fei yang
	 * @version ：2016年10月8日 下午1:27:36
	 * @param sql
	 * @param stmt
	 * @return
	 */
	public static int getContent(String sql, Statement stmt) {
		int count = 0;
		try {
			if (sql != null) {
				String countSql = " select  count(*) record_ from (" + sql + " )";
				ResultSet rs = stmt.executeQuery(countSql);
				if (rs.next()) {
					count = rs.getInt("record_");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 
	 * 功能说明 :获取合计小计数量
	 * 
	 * @author : fei yang
	 * @version ：2016年10月8日 下午3:33:39
	 * @param sql
	 * @param stmt
	 * @param sqlFielName
	 * @return
	 */
	public static String getTotala(String sql, Statement stmt, String sqlFielName) {
		String totalaNum = "0.00";
		try {
			if (sql != null) {
				String countSql = " select trunc(sum( replace(" + sqlFielName + ",',','')),2) sumSqlFielName_ from ("
						+ sql + " )";
				ResultSet rs = stmt.executeQuery(countSql);
				if (rs.next()) {
					totalaNum = String.valueOf(rs.getString(1));
					if (totalaNum.equals("null")) {
						totalaNum = "0.00";
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalaNum;
	}

	/**
	 * 
	 * 功能说明 : 替换条件区域
	 * 
	 * @author : fei yang
	 * @version ：2016年10月8日 下午7:55:12
	 * @param sql
	 * @param querValues
	 * @return
	 */
	public static String getNewSql(String sql, Map<String, String[]> map) {
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			if (!StringUtil.isBlank(entry.getKey())) {
				String needReStr = "@" + entry.getKey() + "@";
				String str = "";
				if (!StringUtil.isBlank(entry.getValue())) {
					for (int i = 0; i < entry.getValue().length; i++) {
						str += entry.getValue()[i];
					}
				}
				sql = sql.replaceAll(needReStr, str);
			}
		}
		return sql;
	}

	/**
	 * 
	 * 功能说明 : 筛选查询条件字符串
	 * 
	 * @author : fei yang
	 * @version ：2016年10月11日 下午4:37:57
	 * @param str
	 * @return
	 */
	public static String changeStr(String str) {
		if (str.equals(Constant.notChoseStr) || str.equals(Constant.notChoseStr + "^")) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 
	 * 功能说明 : 获取查询SQL的所有列名
	 * 
	 * @author : fei yang
	 * @version ：2016年10月10日 下午2:31:41
	 * @param sql
	 * @return
	 */
	public static List<FieldBean> getFieldBySql(String configId, String datasourceId, String sql) {
		List<FieldBean> fieldBeans = new ArrayList<FieldBean>();
		if (StringUtil.isBlank(configId)) {

		}

		try {
			// if (bean.getResults().getFieldBeans() != null) {
			// if (bean.getResults().getFieldBeans().size() > 0
			// && bean.getResults().getFieldBeans().get(0).getSqlFielName() !=
			// "") {
			// return bean.getResults().getFieldBeans();
			// }
			// }
			// String sql = bean.getResults().getSql();
			Connection conn = DBconn.getConnectionById(datasourceId);
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				FieldBean fieldBean = new FieldBean();
				fieldBean.setSqlFielName(data.getColumnName(i));
				fieldBean.setJspFielName(data.getColumnName(i));
				fieldBeans.add(fieldBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<FieldBean>();
		}
		return fieldBeans;
	}

	public static List<String[]> toQueryExcel(ConfigBean bean, Map<String, String[]> map) {
		JsonConfigServiceImpl jsonConfigServiceImpl = new JsonConfigServiceImpl();
		DatasourceBean datasourceBean = jsonConfigServiceImpl.getDatasourceById(bean.getDatasource());
		Connection conn = DBconn.getConnectionByBean(datasourceBean);
		Statement stmt = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		String sql = getNewSql(bean.getResults().getSql(), map);
		StringBuffer newSql = new StringBuffer("");
		if (conn != null) {
			try {
				newSql.append(sql);
				System.out.println("查询SQL" + newSql.toString());
				stmt = conn.createStatement();
				rs = stmt.executeQuery(newSql.toString());
				while (rs.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= bean.getResults().getFieldBeans().size(); i++) {
						if (rs.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs.getString(i) + "@");
						}
					}
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					list.add(newstr);
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 
	 * 功能说明 :判断当前存储过程是否合法
	 * 
	 * @author : fei yang
	 * @version ：2017年3月16日 下午2:55:44
	 * @param datasourceId
	 * @param procedureSql
	 * @param beans
	 * @return
	 */
	public static boolean checkeProcedure(String datasourceId, String procedureSql,
			List<ProcedureParameterBean> beans) {
		Connection conn = null;
		CallableStatement proc = null;
		try {
			conn = DBconn.getConnectionById(datasourceId);
			proc = conn.prepareCall(procedureSql);
			for (int i = 0; i < beans.size(); i++) {
				if ("VARCHAR2".equals(beans.get(i).getType())) {
					proc.setString(beans.get(i).getId(), beans.get(i).getValue());
				} else if ("NUMBER".equals(beans.get(i).getType())) {
					proc.setInt(beans.get(i).getId(), Integer.parseInt(beans.get(i).getValue()));
				}
			}
			proc.registerOutParameter(2, Types.VARCHAR);
			proc.execute();
			String testPrint = proc.getString(2);
			System.out.println(testPrint);
			if (testPrint.equals("1")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex1) {
			}
		}
		return false;
	}

	/**
	 * 
	 * 功能说明 :更具SQL 和数据源ID获取查询的内容
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午12:23:16
	 * @param sql
	 * @param datasourceId
	 * @return
	 */
	public static TableBean toQuerysSql(String sql, String datasourceId) {
		TableBean tableBean = new TableBean();
		if (StringUtil.isBlank(datasourceId) || StringUtil.isBlank(sql)) {
			return tableBean;
		}
		Connection conn = DBconn.getConnectionById(datasourceId);
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData data = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			data = rs.getMetaData();
			List<TableColumnBean> columnBeans = new ArrayList<TableColumnBean>();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				TableColumnBean bean = new TableColumnBean();
				bean.setName(data.getColumnName(i));
				bean.setType(data.getColumnTypeName(i));
				columnBeans.add(bean);
			}
			List<TableRowBean> rows = new ArrayList<TableRowBean>();
			while (rs.next()) {
				TableRowBean bean = new TableRowBean();
				Map<String, String> map = new LinkedHashMap<String, String>();
				for (int i = 0; i < columnBeans.size(); i++) {
					map.put(columnBeans.get(i).getName(), rs.getString(columnBeans.get(i).getName()));
				}
				bean.setValue(map);
				rows.add(bean);
			}
			tableBean.setRows(rows);
			tableBean.setColumns(columnBeans);
		} catch (Exception e) {
			e.printStackTrace();
			return tableBean;
		} finally {
			try {
				if (conn != null) {
					stmt.close();
					conn.close();
				}
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}

		return tableBean;
	}

	/**
	 * 
	 * 功能说明 :获取存储过程名称
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午12:22:21
	 * @param procedureName
	 * @param ownerUser
	 * @return
	 */
	public static String getSelectProcedureSql(String procedureName, String ownerUser) {
		String sql = "";
		if (StringUtil.isNotEmpty(procedureName) && StringUtil.isNotEmpty(ownerUser)) {
			sql += " select sequence,data_type from all_arguments t ";
			sql += " where t.owner = '" + ownerUser.toUpperCase() + "'";
			sql += " AND  t.object_name = '" + procedureName.toUpperCase() + "'";
			sql += "  order by sequence";
		}
		return sql;
	}

	/**
	 * 
	 * 功能说明 : 获取执行存储过程SQL
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午3:58:37
	 * @param sql
	 * @return
	 */
	public static String getCallSql(String sql) {
		String callSql = "";
		if (StringUtil.isNotEmpty(sql)) {
			sql = sql.toUpperCase();
			String procedureName = sql.substring(sql.indexOf("CALL") + "CALL".length(), sql.indexOf("("));
			String[] newStr = sql.split("@");
			int length = Integer.valueOf(newStr.length / 2);
			callSql = "CALL " + procedureName + " ( ";
			for (int i = 0; i < length; i++) {
				if (i == length - 1) {
					callSql += "?)";
				} else {
					callSql += "?,";
				}
			}
		}
		return callSql;
	}

	/**
	 * 
	 * 功能说明 :比对查询条件获取新的查询参数和值
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午4:00:15
	 * @param map
	 * @param parameters
	 * @return
	 */
	public static List<ProcedureParameterBean> getNewParameters(Map<String, String[]> map,
			List<ProcedureParameterBean> parameters, int beginRow, int endRow) {
		for (int i = 0; i < parameters.size(); i++) {
			for (Map.Entry<String, String[]> entry : map.entrySet()) {
				if (entry.getKey().equals(parameters.get(i).getConditionId())) {
					String value = "";
					for (int j = 0; j < entry.getValue().length; j++) {
						value += entry.getValue()[j];
					}
					parameters.get(i).setValue(value);
				}
			}

		}
		return parameters;
	}

	/**
	 * 
	 * 功能说明 :更具参数列表赋值
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午4:00:49
	 * @param proc
	 * @param parameters
	 */
	public static void setCallValue(CallableStatement proc, List<ProcedureParameterBean> parameters) {
		try {
			for (int i = 0; i < parameters.size(); i++) {
				if ("VARCHAR2".equals(parameters.get(i).getType())) {
					proc.setString(parameters.get(i).getId(), parameters.get(i).getValue());
				} else if ("NUMBER".equals(parameters.get(i).getType())) {
					int number = 0;
					if (StringUtil.isInteger(parameters.get(i).getValue())) {
						number = Integer.parseInt(parameters.get(i).getValue());
					}
					proc.setInt(parameters.get(i).getId(), number);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	* @Title: 根据XML配置文件查询数据库返回分页对象(存储过程)
	* @author: feiyang
	* @date: 2016年10月8日 下午1:14:02 
	* @param bean	XML配置文件
	* @param page   分页
	* @param map	查询条件
	* @return:
	 */
	public static Page<String[]> toQueryByProcedure(ConfigBean bean, Page<String[]> page, Map<String, String[]> map) {
//		JsonConfigServiceImpl jsonConfigServiceImpl = new JsonConfigServiceImpl();
//		DatasourceBean datasourceBean = jsonConfigServiceImpl.getDatasourceById(bean.getDatasource());
		DatasourceBean datasourceBean = dataSourceBeanService.getDataSourceById(bean.getDatasource());
		Connection conn = DBconn.getConnectionByBean(datasourceBean);
		Statement stmt = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		List<String> totalaList = new ArrayList<String>();
		List<String> subtotalaList = new ArrayList<String>();
		int totalCount = 0;
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		List<ProcedureParameterBean> parameters = bean.getParameters();
		String[] newProcedureSql = Constant.getProcedureSql(bean.getResults().getSql());	
		String selectSql = getNewSql(newProcedureSql[1], map);
		CallableStatement proc = null;
		String newSql = getSqlByRownum(selectSql, beginRow, endRow);
		if (conn != null) {
			try {
				String callSql = getCallSql(newProcedureSql[0]);
				proc = conn.prepareCall(callSql);
				parameters = getNewParameters(map, parameters, beginRow, endRow);
				setCallValue(proc, parameters);
				proc.registerOutParameter(2, Types.VARCHAR);
				proc.execute();	
				System.out.println("查询SQL" + newSql);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(newSql.toString());
				ResultSetMetaData data = rs.getMetaData();
				
				while (rs.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data.getColumnCount(); i++) {
						if (rs.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs.getObject(i) + "@");
						}
					}
					list.add(strb.toString().substring(0, strb.length() - 1).split("@"));
				}
				totalCount = getContent(selectSql, stmt);
				for (int i = 0; i < bean.getResults().getFieldBeans().size(); i++) {// 获取合计小计
					String totalaStr = "";
					String subtotalaStr = "";
					if (bean.getResults().getFieldBeans().get(i).getTotalaggr().equals("1")) {
						totalaStr = getTotala(selectSql, stmt,
								bean.getResults().getFieldBeans().get(i).getSqlFielName());
					}

			/*		if (bean.getResults().getFieldBeans().get(i).getSubtotalaggr().equals("1")) {
						subtotalaStr = getTotala(newSql.toString(), stmt,
								bean.getResults().getFieldBeans().get(i).getSqlFielName());
					}*/
					if (!bean.getResults().getFieldBeans().get(i).getHidden().equals("1")) {
						totalaList.add(totalaStr);
						subtotalaList.add(subtotalaStr);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		page.setTotalCount(totalCount);
		page.setTotalaList(totalaList);
		page.setSubtotalaList(subtotalaList);
		page.setResult(toCheckResultList(list, bean.getResults().getFieldBeans()));
		return page;
	}

	/**
	 * 
	 * @Title: 根据rownum获取起止行的新SQL
	 * @author: feiyang
	 * @date: 2017年3月31日 下午2:15:21
	 * @param sql
	 *            原始SQL
	 * @param beginRow
	 *            开始行
	 * @param endRow
	 *            结束行
	 * @return:
	 */
	public static String getSqlByRownum(String sql, Integer beginRow, Integer endRow) {
		if (StringUtil.isBlank(sql) || StringUtil.isBlank(beginRow) || StringUtil.isBlank(endRow)) {
			return "";
		}
		StringBuffer rownumSql = new StringBuffer("");
		rownumSql.append("select  * from ( select rownum rownum_, oldtable_.* from (");
		rownumSql.append(sql);
		rownumSql.append("  ) oldtable_ ) where rownum_>" + beginRow + " and rownum_<=" + endRow);
		return rownumSql.toString();
	}

	/**
	 * 
	 * @Title: 根据配置文件检查结果返回新的结果集
	 * @author: feiyang
	 * @date: 2017年3月31日 下午4:01:58
	 * @param resultList
	 *            原始结果集
	 * @param fieldBeans
	 *            结果集操作BEAN
	 * @return:
	 */
	public static List<String[]> toCheckResultList(List<String[]> resultList, List<FieldBean> fieldBeans) {
		if (resultList == null || fieldBeans == null || resultList.size() == 0 || fieldBeans.size() == 0) {
			return resultList;
		}
		for (int i = 0; i < resultList.size(); i++) {
			String[] content = resultList.get(i);
			if (content.length - 1 != fieldBeans.size()) {
				return resultList;
			}
			/**
			 * 定义长度为3是数组，为了操作页面 1：返回结果的序号 2：预警字符串 3：传值到子页面字符串
			 */

			String[] indexContent = new String[] { resultList.get(i)[0], " ", " " };
			for (int j = 0; j < fieldBeans.size(); j++) {
				FieldBean fieldBean = fieldBeans.get(j);
				/**
				 * 以下匹配预警字符串
				 */
				if (toCheakWarningStr(fieldBean.getWarningStr(), content[j + 1])) {
					indexContent[1] = indexContent[1] + "color: red;";
				}
				/**
				 * 以下判断是否需要传值要子页面
				 */
				if (StringUtil.isNotEmpty(fieldBean.getIsByValue())) {
					indexContent[2] = indexContent[2] + "&" + fieldBean.getIsByValue() + "=" + content[j + 1];
				}
			}
			resultList.get(i)[0] = StringUtil.arrayToString(indexContent);// 替换原有第一列字符串
		}
		return resultList;
	}

	/**
	 * 
	 * @Title: 判断预警字符串
	 * @author: feiyang
	 * @date: 2017年3月31日 下午4:03:06
	 * @param warningStr
	 * @param needCheckStr
	 * @return:
	 */
	public static boolean toCheakWarningStr(String warningStr, String needCheckStr) {
		if (StringUtil.isNotEmpty(warningStr) && StringUtil.isNotEmpty(needCheckStr)) {
			return warningStr.equals(needCheckStr);
		}
		return false;
	}


}
