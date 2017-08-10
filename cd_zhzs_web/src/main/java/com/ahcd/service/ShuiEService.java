package com.ahcd.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ahcd.common.DBconn;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.HyQuertyBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ShuiEBean;
import com.ahcd.pojo.ShuiHuBean;

@Service("shuiEService")
public class ShuiEService {

	public static List<ShuiEBean> getList(String datasourcerId, ShuiEBean bean, HyQuertyBean hyQuertyBean, int type) {
		Connection conn = DBconn.getConnectionById(datasourcerId);
		Statement stmt = null;
		ResultSet rs = null;
		List<ShuiEBean> list = new ArrayList<ShuiEBean>();
		String sql = getSql(bean, hyQuertyBean, type);
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					ShuiEBean newBean = toGetBeanList(rs);
					list.add(newBean);
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

	public static String getSql(ShuiEBean bean, HyQuertyBean hyQuertyBean, int type) {

		String sql = " select t1.*,t2.name_dl from ( ";

		sql += " select * from gs_ds_sjse ";
		sql += " where  1=1";
		if (!StringUtil.isBlank(bean.getIdYear())) {
			sql += " and id_year=" + bean.getIdYear();
		}
		sql += " and  nsrmc in( select nsrmc from dmn_gbhy  where ";
		if (!StringUtil.isBlank(hyQuertyBean.getNameDl())) {
			sql += "   name_dl ='" + hyQuertyBean.getNameDl() + "' ";
		} else if (!StringUtil.isBlank(hyQuertyBean.getNameMl())) {
			sql += "   name_ml ='" + hyQuertyBean.getNameMl() + "' ";
		} else if (!StringUtil.isBlank(hyQuertyBean.getNameCy())) {
			sql += "   name_cy ='" + hyQuertyBean.getNameCy() + "' ";
		}
		sql += ") )t1 left join ";
		if (type == 1) {
			sql += " 	(select * from dmn_gbhy_gs  where 1=1 ";
		} else {
			sql += " 	(select * from dmn_gbhy_ds  where 1=1 ";
		}
		if (!StringUtil.isBlank(bean.getIdYear())) {
			sql += " and id_year=" + bean.getIdYear();
		}
		sql += " ) t2 on t1.nsrmc=t2.nsrmc ";
		return sql;
	}

	public static Page<ShuiEBean> getPage(Page<ShuiEBean> page, ShuiEBean bean, HyQuertyBean hyQuertyBean) {
		List<ShuiEBean> gsList = getList("1", bean, hyQuertyBean, 1);
		List<ShuiEBean> dsList = getList("1", bean, hyQuertyBean, 2);
		List<ShuiEBean> reList = new ArrayList<ShuiEBean>();

		if (gsList.size() == 0) {
			reList = dsList;
		} else if (dsList.size() == 0) {
			reList = gsList;
		} else {
			if (gsList.size() > dsList.size()) {
				for (int i = 0; i < gsList.size(); i++) {
					for (int j = 0; j < dsList.size(); j++) {
						if (gsList.get(i).getNsrmc().equals(dsList.get(j).getNsrmc())) {
							gsList.get(i).setDsSe(dsList.get(j).getDsSe());
							reList.add(gsList.get(i));
						} else {
							reList.add(gsList.get(i));
						}
					}
				}

			} else {
				for (int i = 0; i < dsList.size(); i++) {
					for (int j = 0; j < gsList.size(); j++) {
						if (dsList.get(i).getNsrmc().equals(gsList.get(j).getNsrmc())) {
							dsList.get(i).setGsSe(gsList.get(j).getGsSe());
							reList.add(dsList.get(i));
						} else {
							reList.add(dsList.get(i));
						}
					}
				}
			}

		}

		page.setTotalCount(reList.size());

		List<ShuiEBean> newList = new ArrayList<ShuiEBean>();
		int begin = (page.getPageNum() - 1) * page.getNumPerPage();
		int end = page.getPageNum() * page.getNumPerPage();

		for (int i = 1; i <= reList.size(); i++) {
			if (i>=begin &&i<=end) {
				newList.add(reList.get(i));
			}
		}
		page.setResult(newList);
		return page;

	}

	public static ShuiEBean toGetBeanList(ResultSet rs) {
		ShuiEBean bean = new ShuiEBean();
		try {
			bean.setNsrmc(rs.getString(1));
			bean.setNsrsbh(rs.getString(2));
			bean.setGsSe(rs.getString(3));
			bean.setDsSe(rs.getString(4));
			bean.setIdYear(rs.getString(6));
			bean.setNameDl(rs.getString(7));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	public static void main(String[] args) {
		Page<ShuiEBean> page = new Page<ShuiEBean>();
		ShuiEBean bean = new ShuiEBean();
		bean.setIdYear("2014");
		HyQuertyBean hyQuertyBean = new HyQuertyBean();
		hyQuertyBean.setNameDl("房屋和土木工程建筑业");
		page = getPage(page, bean, hyQuertyBean);

		for (int i = 0; i < page.getResult().size(); i++) {
			System.out.println(page.getResult().get(i).getNsrmc() + ":" + page.getResult().get(i).getGsSe() + ":"
					+ page.getResult().get(i).getDsSe());
		}
	}

}
