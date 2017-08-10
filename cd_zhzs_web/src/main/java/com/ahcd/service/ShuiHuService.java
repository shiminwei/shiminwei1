package com.ahcd.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.ahcd.pojo.ShuiHuBean;

@Service("shuiHuService")
public class ShuiHuService {

	public static List<ShuiHuBean> getList(String datasourcerId, ShuiHuBean bean, Page<ShuiHuBean> page,HyQuertyBean hyQuertyBean) {
		Connection conn = DBconn.getConnectionById(datasourcerId);
		Statement stmt = null;
		ResultSet rs = null;
		List<ShuiHuBean> list = new ArrayList<ShuiHuBean>();
		String sql = "  select * from ( select t1.* ,rownum as rown from( ";
		sql += getSql(bean,hyQuertyBean);
		sql += ") t1 )where rown>=" + (page.getPageNum() - 1) * page.getNumPerPage() + " and rown<="
				+ page.getPageNum() * page.getNumPerPage();
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					ShuiHuBean newBean = toGetBeanList(rs);
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

	public static int getCounNum(String datasourcerId, ShuiHuBean bean,HyQuertyBean hyQuertyBean) {
		String sql = "select count(1) as count from (";
		sql += getSql(bean,hyQuertyBean);
		sql += " )";
		Connection conn = DBconn.getConnectionById(datasourcerId);
		ResultSet rs = null;
		PreparedStatement pstm = null;
		int count = 0;
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			rs.next();
			count = rs.getInt("count");
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static String getSql(ShuiHuBean bean,HyQuertyBean hyQuertyBean) {
		String sql = " select nsrmc, nsrsbh, code_dl,name_dl, fddbr, jydz, id_year, ssgds ";
		sql += "  from (select t1.*, '国税' as ssgds  from dmn_gbhy_gs t1 ";
		sql += "  union all ";
		sql += "   select t2.*, '地税' as ssgds from dmn_gbhy_ds t2) ";
		sql += "  where 1=1 ";
		if (!StringUtil.isBlank(bean.getStartDate())) {
			sql += "  and id_year = " + bean.getStartDate();
		}
		if (!StringUtil.isBlank(hyQuertyBean.getNameDl())) {
			sql +=" and name_dl in (";
			sql +=" select name_dl from zd_hy where name_dl ='"+hyQuertyBean.getNameDl()+"') ";
		}else if (!StringUtil.isBlank(hyQuertyBean.getNameMl())) {
			sql +=" and name_dl in (";
			sql +=" select name_dl from zd_hy where name_ml ='"+hyQuertyBean.getNameMl()+"') ";
		}else if (!StringUtil.isBlank(hyQuertyBean.getNameCy())) {
			sql +=" and name_dl in (";
			sql +=" select name_dl from zd_hy where name_cy ='"+hyQuertyBean.getNameCy()+"') ";
		}
		if (!StringUtil.isBlank(bean.getNsrmc())) {
			 sql+=" and nsrmc like '%"+bean.getNsrmc()+"%'";
		}
		if (!StringUtil.isBlank(bean.getNsrsbh())) {
			 sql+=" and nsrsbh like '%"+bean.getNsrsbh()+"%'";
		}
		
		return sql;
	}

	public static Page<ShuiHuBean> getPage(Page<ShuiHuBean> page, ShuiHuBean bean,HyQuertyBean hyQuertyBean) {
		List<ShuiHuBean> result = getList("1", bean, page,hyQuertyBean);
		page.setResult(result);
		int count = getCounNum("1", bean,hyQuertyBean);
		page.setTotalCount(count);
		return page;

	}

	public static ShuiHuBean toGetBeanList(ResultSet rs) {
		ShuiHuBean bean = new ShuiHuBean();
		try {
			bean.setNsrmc(rs.getString(1));
			bean.setNsrsbh(rs.getString(2));
			bean.setCodeDl(rs.getString(3));
			bean.setNameDl(rs.getString(4));
			bean.setFddbr(rs.getString(5));
			bean.setJydz(rs.getString(6));
			bean.setIdYear(rs.getString(7));
			bean.setSsgds(rs.getString(8));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

}
