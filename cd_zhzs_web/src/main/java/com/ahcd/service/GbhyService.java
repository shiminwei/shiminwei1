package com.ahcd.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ahcd.common.DBconn;
//import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.GbhyBean;

/**
 * @author : fei yang
 * @version ：2016年11月18日 下午3:39:08
 */
@Service("gbhyService")
public class GbhyService {

	public static List<GbhyBean> getList() {
		Connection conn = DBconn.getConnectionById("2");
		Statement stmt = null;
		ResultSet rs = null;
		List<GbhyBean> list = new ArrayList<GbhyBean>();
		String sql = " SELECT *  FROM (  ";
		sql+=" SELECT z.*, ROW_NUMBER() OVER(PARTITION BY z.name_dl ORDER BY z.code_dl) AS code_id ";
		sql+="  FROM DMN_GBHY z )  ";
		sql+=" WHERE code_id = 1 ";
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					GbhyBean bean = toGetBeanList(rs);
					list.add(bean);
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

	public static GbhyBean toGetBeanList(ResultSet rs) {
		GbhyBean bean = new GbhyBean();
		try {
			bean.setIdGbhy(rs.getString(1));
			bean.setCodeCy(rs.getString(2));
			bean.setNameCy(rs.getString(3));
			bean.setCodeMl(rs.getString(4));
			bean.setNameMl(rs.getString(5));
			bean.setCodeDl(rs.getString(6));
			bean.setNameDl(rs.getString(7));
			bean.setCodeZl(rs.getString(8));
			bean.setNameZl(rs.getString(9));
			bean.setCodeGbhy(rs.getString(10));
			bean.setNameGbhy(rs.getString(11));
			bean.setNewDs(rs.getString(12));
			bean.setNewSds(rs.getString(13));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
}
