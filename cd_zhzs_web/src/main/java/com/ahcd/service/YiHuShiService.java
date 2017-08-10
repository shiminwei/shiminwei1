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
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.QyBean;

@Service("yiHuShiService")
public class YiHuShiService {

	public static Page<String[]> toQuery(Page<String[]> page, QyBean qyBean) {
		Connection conn = DBconn.getConnectionById("1");
		Statement stmt = null;
		ResultSet rs = null;
		List<String[]> list = new ArrayList<String[]>();
		Page<String[]> pageList = new Page<String[]>();
		int totalCount = 0;
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		String sql = getNewSql(qyBean);
		StringBuffer newSql = new StringBuffer("");
		if (conn != null) {
			try {
				newSql.append("select  * from ( select rownum rownum_, oldtable_.* from (");
				newSql.append(sql);
				newSql.append("  ) oldtable_ ) where rownum_>" + beginRow + " and rownum_<=" + endRow);
				System.out.println("查询SQL" + newSql.toString());
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
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					list.add(newstr);
				}
				totalCount = getContent(sql, stmt);
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		int totalPage = (int) Math.ceil(totalCount / page.getNumPerPage());

		pageList.setTotalPage(totalPage);
		pageList.setPageNum(page.getPageNum());
		pageList.setTotalCount(totalCount);
		pageList.setNumPerPage(page.getNumPerPage());
		pageList.setResult(list);
		return pageList;
	}

	public static String getNewSql(QyBean qyBean) {
		String sql = " select * from bd_qy_jbxx where 1=1 ";
		if (!StringUtil.isBlank(qyBean.getQymc())) {
			sql += "and qymc like '%" + qyBean.getQymc() + "%'";
		}
		return sql;
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
}
