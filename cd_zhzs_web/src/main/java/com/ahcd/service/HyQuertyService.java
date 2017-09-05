package com.ahcd.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.ahcd.common.Constant;
import com.ahcd.common.DBconn;
import com.ahcd.common.StringUtil;
//import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.HyQuertyBean;

/**
 * @author : fei yang
 * @version ：2016年11月21日 下午6:11:47
 */
@Service("hyQuertyService")
public class HyQuertyService {
	public static List<HyQuertyBean> getList(String startDate, int type, String datasourcerId) {
		Connection conn = DBconn.getConnectionById(datasourcerId);
		Statement stmt = null;
		ResultSet rs = null;
		List<HyQuertyBean> list = new ArrayList<HyQuertyBean>();
		String sql = getSql(startDate, type);
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					HyQuertyBean bean = toGetBeanList(rs);
					list.add(bean);
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public static String getSql(String startDate, int type) {
		StringBuffer sql = new StringBuffer();
		if (type == 2) {// 二级
			sql.append(" select max(id_gbhy),max(code_cy) as code_cy ,max(name_cy),code_ml,max(name_ml),'','',");
			sql.append(" min(id_mth) as id_mth,sum(nvl(gs_shuihu,0)+nvl(ds_shuihu,0)) as shuihu,"
					+ "sum(nvl(sj_se_gs,0)+nvl(sj_se_ds,0)) as sj_se ,sum(yj_se) from zd_hy ");
			sql.append(" where 1=1");
			if (!StringUtil.isBlank(startDate)) {
				sql.append(" and id_mth = " + startDate);
			}
			sql.append("   group by code_ml order by code_cy,code_ml");
		} else if (type == 3) {// 三级
			sql.append(
					" select max(id_gbhy),max(code_cy) as code_cy ,max(name_cy),max(code_ml) as code_ml ,max(name_ml),code_dl,max(name_dl), ");
			sql.append(
					" min(id_mth) as id_mth,sum(nvl(gs_shuihu,0)+nvl(ds_shuihu,0)) as shuihu,sum(nvl(sj_se_gs,0)+nvl(sj_se_ds,0)) as sj_se,sum(yj_se) from zd_hy   ");
			sql.append(" where 1=1");
			if (!StringUtil.isBlank(startDate)) {
				sql.append(" and id_mth = " + startDate);
			}
			sql.append("    group by code_dl order by code_cy,code_ml,code_dl ");
		} else {// 一级
			sql.append(
					"select max(id_gbhy),code_cy,max(name_cy),'','','','',min(id_mth) as id_mth,sum(nvl(gs_shuihu,0)+nvl(ds_shuihu,0)) as shuihu,sum(nvl(sj_se_gs,0)+nvl(sj_se_ds,0)) as sj_se,sum(yj_se) from zd_hy ");
			sql.append(" where 1=1");
			if (!StringUtil.isBlank(startDate)) {
				sql.append(" and id_mth = " + startDate);
			}
			sql.append("  group by code_cy  order by code_cy");
		}
		return sql.toString();
	}

	/**
	 * 
	 * 功能说明 :获取数据合计
	 * 
	 * @author : fei yang
	 * @version ：2016年11月23日 下午5:31:56
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static HyQuertyBean getTotalNum(String startDate,String datasourcerId) {
		Connection conn =DBconn.getConnectionById(datasourcerId);
		Statement stmt = null;
		ResultSet rs = null;
		HyQuertyBean bean = new HyQuertyBean();
		String sql = " select '','','','','','','',0,sum(nvl(gs_shuihu,0)+nvl(ds_shuihu,0)) as shuihu,sum(nvl(sj_se_gs,0)+nvl(sj_se_ds,0)) as sj_se,sum(yj_se) from zd_hy  ";
		sql += "  where   1=1  ";
		if (!StringUtil.isBlank(startDate)) {
			sql += " and id_mth = " + startDate;
		}
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					bean = toGetBeanList(rs);
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

	public static HyQuertyBean toGetBeanList(ResultSet rs) {
		HyQuertyBean bean = new HyQuertyBean();
		try {
			bean.setIdGbhy(rs.getString(1));
			bean.setCodeCy(rs.getString(2));
			bean.setNameCy(rs.getString(3));
			bean.setCodeMl(rs.getString(4));
			bean.setNameMl(rs.getString(5));
			bean.setCodeDl(rs.getString(6));
			bean.setNameDl(rs.getString(7));
			if (StringUtil.isBlank(rs.getString(8))) {
				bean.setIdMth(0);
			} else {
				bean.setIdMth(Integer.valueOf(rs.getString(8)));
			}

			if (StringUtil.isBlank(rs.getString(9))) {
				bean.setShuiHu(0);
			} else {
				bean.setShuiHu(Integer.valueOf(rs.getString(9)));
			}

			if (StringUtil.isBlank(rs.getString(10))) {
				bean.setSjSe(new BigDecimal(0));
			} else {
				bean.setSjSe(new BigDecimal(rs.getString(10)).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP));
			}

			if (StringUtil.isBlank(rs.getString(11))) {
				bean.setYjSe(new BigDecimal(0));
			} else {
				bean.setYjSe(new BigDecimal(rs.getString(11)).divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP));
			}

			bean.setLjSe(bean.getYjSe().subtract(bean.getSjSe()));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
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
	public static String getNewSql(String sql, String[] querValues) {
		String newSql = sql;
		String needReplaceStr = Constant.needReplaceStr; // 需要替换的字符串
		if (StringUtil.isBlank(querValues)) {
			newSql = newSql.replace(needReplaceStr, "");
		} else {
			for (int i = 0; i < querValues.length; i++) {
				int index = newSql.indexOf(needReplaceStr);
				String beforSql = newSql.substring(0, index + 2);
				String endSql = newSql.substring(index + 2, newSql.length());
				if (querValues[i].equals(" ")) {
					beforSql = beforSql.substring(0, index);
				} else {
					if (querValues[i].indexOf("^") > 0) { // 后面带月份查询
						String str = querValues[i].replace("^", "");
						beforSql = beforSql.replace(needReplaceStr, changeStr(str) + changeStr(querValues[i + 1]));
						i++;
					} else {
						beforSql = beforSql.replace(needReplaceStr, changeStr(querValues[i]));
					}

				}
				newSql = beforSql + endSql;
			}
		}
		return newSql;
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

}
