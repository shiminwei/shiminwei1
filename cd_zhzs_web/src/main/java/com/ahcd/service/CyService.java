package com.ahcd.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ahcd.common.DBconn;
//import com.ahcd.common.DbUntil;
import com.ahcd.common.StringUtil;
//import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.HyQuertyBean;
import com.ahcd.pojo.Page;

/**
 * @author : fei yang
 * @version ：2016年12月7日 下午2:37:27
 */
@Service("cyService")
public class CyService {

	public static String getSql(int sqlType, String name, String startDate, String endDate) {
		String sql = "";
		if (sqlType == 1) {
			sql = "select '' name_cy, name_ml, ''name_dl, sum(shuihu)shuihu, sum(yj_hj)yj_hj, sum(sj_hj)sj_hj, sum(lj_hj)lj_hj";
			sql += "  from (select *  from bd_qy_hy_hj  where 1=1 ";
			if (!StringUtil.isBlank(startDate)) {
				sql += "  and id_year >= '" + startDate + "'";
			}
			if (!StringUtil.isBlank(endDate)) {
				sql += "  and id_year <= '" + endDate + "'";
			}
			sql += " and name_ml in (select gr_name from (select name_ml as gr_name from sys_gbhy ";
			sql += "    where name_cy = '" + name + "')  group by gr_name))  group by name_ml ";
		} else if (sqlType == 2) {
			sql = " select '' name_cy, '' name_ml, name_dl, sum(shuihu)shuihu, sum(yj_hj)yj_hj, sum(sj_hj)sj_hj, sum(lj_hj)lj_hj ";
			sql += "  from (select *  from bd_qy_hy_hj  where 1=1 ";
			if (!StringUtil.isBlank(startDate)) {
				sql += "  and id_year >= '" + startDate + "'";
			}
			if (!StringUtil.isBlank(endDate)) {
				sql += "  and id_year <= '" + endDate + "'";
			}
			sql += " and name_dl in (select gr_name from (select name_dl as gr_name from sys_gbhy ";
			sql += "    where name_ml = '" + name + "')  group by gr_name))  group by name_dl ";
		}
		return sql;
	}

	public static String getShuihuSql(HyQuertyBean bean) {
		String sql = " select t2.* ,rownum rown from (select id_gbhy, qymc  from bd_qy_hy  where 1 = 1 ";

		if (!StringUtil.isBlank(bean.getStartDate())) {
			sql += "  and id_year >= '" + bean.getStartDate() + "'";
		}
		if (!StringUtil.isBlank(bean.getEndDate())) {
			sql += "  and id_year <= '" + bean.getEndDate() + "'";
		}
		if (!StringUtil.isBlank(bean.getIdGbhy())) {
			sql += "  and qymc like '%" + bean.getIdGbhy() + "%'";
		}
		sql += "  and id_gbhy in (select id_gbhy from sys_gbhy where ";

		if (!StringUtil.isBlank(bean.getNameDl())) {
			sql += " name_dl ='" + bean.getNameDl();
		} else if (!StringUtil.isBlank(bean.getNameMl())) {
			sql += " name_ml ='" + bean.getNameMl();
		} else {
			sql += " name_cy  ='" + bean.getNameCy();
		}

		sql += "' )) t1 ";
		sql += "   left join bd_qy_jbxx t2 on t1.qymc = t2.qymc ";
		return sql;
	}

	/**
	 * 
	 * 功能说明 :
	 * 
	 * @author : fei yang
	 * @version ：2016年12月8日 下午3:27:23
	 * @param sqlType
	 * @param name
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String[]> getList(int sqlType, String name, String startDate, String endDate) {
		List<String[]> list = new ArrayList<String[]>();
		String sql = getSql(sqlType, name, startDate, endDate);
		Connection conn = DBconn.getConnectionById("1");
		Statement stmt = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				ResultSetMetaData data = rs.getMetaData();
				while (rs.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data.getColumnCount(); i++) {
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

	public static Page getShuiHuList(Page page, HyQuertyBean bean) {
		List<String[]> list = new ArrayList<String[]>();
		String sql = " select t3.rown, t3.qymc,t3.nsrsbh,t3.nsrzt,t3.fddbr,t3.zclx,t3.jydz,t3.zcjb from (  ";
		sql += getShuihuSql(bean);
		sql += " ) t3 where rown >=" + (page.getPageNum() - 1) * page.getNumPerPage() + " and rown<= "
				+ page.getPageNum() * page.getNumPerPage();
		Connection conn = DBconn.getConnectionById("1");
		Statement stmt = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				ResultSetMetaData data = rs.getMetaData();
				while (rs.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data.getColumnCount(); i++) {
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
				page.setTotalCount(getContent(getShuihuSql(bean), stmt));
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		page.setResult(list);

		return page;

	}

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

	public static Page getShuiEList(Page page, HyQuertyBean bean) {
		List<String[]> list = new ArrayList<String[]>();
		String sql = " select t3.rown,t3.nsrmc,t3.nsrsbh,t3.zsxm,t3.zspm,t3.sjje,t3.rkrq,t3.ssgds from ( ";
		sql += getShuiESql(bean);
		sql += " ) t3 where rown >=" + (page.getPageNum() - 1) * page.getNumPerPage() + " and rown<= "
				+ page.getPageNum() * page.getNumPerPage();
		Connection conn = DBconn.getConnectionById("1");
		Statement stmt = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
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
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					list.add(newstr);
				}
				page.setTotalCount(getContent(getShuiESql(bean), stmt));
				page=getTotle(page, bean, stmt);
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		page.setResult(list);
		return page;

	}

	public static Page getTotle(Page page, HyQuertyBean bean, Statement stmt) {
		String subtotalaNum = "0.00";
		String sql = " select sum(sjje) from (";
		sql += " select t3.rown,t3.nsrmc,t3.nsrsbh,t3.zsxm,t3.zspm,t3.sjje,t3.rkrq,t3.ssgds from ( ";
		sql += getShuiESql(bean);
		sql += " ) t3 where rown >=" + (page.getPageNum() - 1) * page.getNumPerPage() + " and rown<= "
				+ page.getPageNum() * page.getNumPerPage();
		sql += ")";

		try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				subtotalaNum = String.valueOf(rs.getString(1));
				if (subtotalaNum.equals("null")) {
					subtotalaNum = "0.00";
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> subtotalaList = new ArrayList<String>();
		for (int i = 0; i < 7; i++) {
			if (i == 4) {
				subtotalaList.add(subtotalaNum);
			} else {
				subtotalaList.add(null);
			}
		}
		List<String> totalaList = new ArrayList<String>();
		String totalaNum = "0.00";	
		String totalaSql = " select sum(sjje) from (";
		totalaSql += getShuiESql(bean);
		totalaSql += ")";
		try {
			ResultSet rs = stmt.executeQuery(totalaSql);
			if (rs.next()) {
				totalaNum = String.valueOf(rs.getString(1));
				if (totalaNum.equals("null")) {
					totalaNum = "0.00";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	for (int i = 0; i < 7; i++) {
			if (i == 4) {
				totalaList.add(totalaNum);
			} else {
				totalaList.add(null);
			}
		}
		page.setSubtotalaList(subtotalaList);
		page.setTotalaList(totalaList);
		return page;
	}

	public static String getShuiESql(HyQuertyBean bean) {
		String sql = " select t2.*, rownum rown  from (select id_gbhy, qymc from bd_qy_hy where 1 = 1 ";
		if (!StringUtil.isBlank(bean.getStartDate())) {
			sql += "  and id_year >= '" + bean.getStartDate() + "'";
		}
		if (!StringUtil.isBlank(bean.getEndDate())) {
			sql += "  and id_year <= '" + bean.getEndDate() + "'";
		}
		if (!StringUtil.isBlank(bean.getIdGbhy())) {
			sql += "  and qymc like '%" + bean.getIdGbhy() + "%'";
		}
		sql += "  and id_gbhy in (select id_gbhy from sys_gbhy where ";

		if (!StringUtil.isBlank(bean.getNameDl())) {
			sql += " name_dl ='" + bean.getNameDl();
		} else if (!StringUtil.isBlank(bean.getNameMl())) {
			sql += " name_ml ='" + bean.getNameMl();
		} else {
			sql += " name_cy  ='" + bean.getNameCy();
		}
		sql += "' )) t1 ";
		sql += "   left join (select * from bd_qy_sjxx  where 1 = 1 ";
		if (!StringUtil.isBlank(bean.getStartDate())) {
			sql += "   and substr(k_date, 0, 4) >= '" + bean.getStartDate() + "'";
		}
		if (!StringUtil.isBlank(bean.getEndDate())) {
			sql += "  and substr(k_date, 0, 4) <= '" + bean.getEndDate() + "'";
		}

		if (!StringUtil.isBlank(bean.getIdGbhy())) {
			sql += "  and nsrmc like '%" + bean.getIdGbhy() + "%'";
		}
		sql += ") t2  on t1.qymc = t2.nsrmc";
		return sql;
	}

	public static void main(String[] args) {
		List<String[]> list = getList(1, "第二产业", "2014", "2015");
		for (int i = 0; i < list.size(); i++) {
		}
	}
}
