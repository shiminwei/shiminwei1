package com.ahcd.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ahcd.common.DBconn;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.QyBean;
import com.alibaba.fastjson.JSONArray;
@Service("qyInfoService")
public class QyInfoService {
	public static List<Object> getQyJbxx(QyBean qyBean) {
		List<Object> list = new ArrayList<Object>();
		String sql = "";
		if (StringUtil.isBlank(qyBean.getQymc())) {
			return list;
		} else {
			sql = " select * from sys_company_info   where name = '" + qyBean.getQymc() + "'";
		}
		Connection conn = DBconn.getConnectionById("1");
		Statement stmt = null;
		ResultSet rs = null;
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
			    int columnCount = md.getColumnCount();
				while (rs.next()) {
					Map<String, Object> rowData = new HashMap<String, Object>();
		            for (int i = 1; i <= columnCount; i++) {
		                rowData.put(md.getColumnName(i), rs.getObject(i));
		            }
		            list.add(rowData);
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
	public static void main(String[] args) {
		QyBean qyBean=new QyBean();
		qyBean.setQymc("池州凯邦置业有限公司");
		List list = getQyJbxx(qyBean);
		System.out.println(list);
	}
}
