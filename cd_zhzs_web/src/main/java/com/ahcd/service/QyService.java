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
//import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.QyBean;

@Service("qyService")
public class QyService {

	public static List<Object> getQyJbxx(QyBean qyBean, String startDate, String endDate) {
		List<Object>list=new ArrayList<Object>();
		
		QyBean newBean = new QyBean();
		String sql = "";
		if (StringUtil.isBlank(qyBean.getQymc())) {
			return list;
		} else {
			sql = " select * from bd_qy_jbxx   where qymc = '" + qyBean.getQymc() + "'  and rownum =1";
		}
		Connection conn =DBconn.getConnectionById("1");
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String getSeSql=getQyYjSjSql(qyBean, startDate, endDate);
		List<String[]> seList=new ArrayList<String[]>();
		List<String[]> xmList=new ArrayList<String[]>();
		List<String[]> sjList=new ArrayList<String[]>();
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					newBean = toGetBean(rs);
				}
				rs1 = stmt.executeQuery(getSeSql);
				ResultSetMetaData data = rs1.getMetaData();
				while (rs1.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data.getColumnCount(); i++) {
						if (rs1.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs1.getString(i) + "@");
						}
					}
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					seList.add(newstr);
				}
				
				
				rs2 = stmt.executeQuery(getQyXmxxSql(qyBean, startDate, endDate));
				ResultSetMetaData data2 = rs2.getMetaData();
				while (rs2.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data2.getColumnCount(); i++) {
						if (rs2.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs2.getString(i) + "@");
						}
					}
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					xmList.add(newstr);
				}
				
				
				rs3 = stmt.executeQuery(getQySjxxSql(qyBean, startDate, endDate));
				ResultSetMetaData data3 = rs3.getMetaData();
				while (rs3.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data3.getColumnCount(); i++) {
						if (rs3.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs3.getString(i) + "@");
						}
					}
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					sjList.add(newstr);
				}
				
				
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(newBean);
			list.add(seList);
			list.add(xmList);
			list.add(sjList);
		}
		return list;
	}

	
	public static List<Object> getQyJbxxByYear(QyBean qyBean, String year) {
		List<Object>list=new ArrayList<Object>();
		
		QyBean newBean = new QyBean();
		String sql = "";
		if (StringUtil.isBlank(qyBean.getQymc())) {
			return list;
		} else {
			sql = " select * from bd_qy_jbxx   where qymc = '" + qyBean.getQymc() + "'  and rownum =1";
		}
		Connection conn =DBconn.getConnectionById("1");
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String getSeSql=getQyYjSjSqlByYear(qyBean, year);
		List<String[]> seList=new ArrayList<String[]>();
		List<String[]> xmList=new ArrayList<String[]>();
		List<String[]> sjList=new ArrayList<String[]>();
		if (conn != null) {
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					newBean = toGetBean(rs);
				}
				rs1 = stmt.executeQuery(getSeSql);
				ResultSetMetaData data = rs1.getMetaData();
				while (rs1.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data.getColumnCount(); i++) {
						if (rs1.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs1.getString(i) + "@");
						}
					}
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					seList.add(newstr);
				}
				
				rs2 = stmt.executeQuery(getQyXmxxSqlByYear(qyBean, year));
				ResultSetMetaData data2 = rs2.getMetaData();
				while (rs2.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data2.getColumnCount(); i++) {
						if (rs2.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs2.getString(i) + "@");
						}
					}
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					xmList.add(newstr);
				}
				
				
				rs3 = stmt.executeQuery(getQySjxxSqlByYear(qyBean, year));
				ResultSetMetaData data3 = rs3.getMetaData();
				while (rs3.next()) {
					StringBuffer strb = new StringBuffer();
					for (int i = 1; i <= data3.getColumnCount(); i++) {
						if (rs3.getString(i) == null) {
							strb.append(" " + "@");
						} else {
							strb.append(rs3.getString(i) + "@");
						}
					}
					String str = strb.toString().substring(0, strb.length() - 1);
					String[] newstr = str.split("@");
					sjList.add(newstr);
				}
				
				
				
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(newBean);
			list.add(seList);
			list.add(xmList);
			list.add(sjList);
		}
		return list;
	}

	
	public static String getQyYjSjSql(QyBean qyBean, String startDate, String endDate) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select id_year,sj_zzs  ");	
		sql.append(" ,sj_yys,sj_xfs,  ");	
		sql.append(" sj_qysds_gs,sj_qysds_ds,  ");
		sql.append(" sj_tdsys,sj_tdzzs, ");	
		sql.append(" sj_yhs,sj_qs,sj_cjs, ");
		sql.append(" sj_jyf,sj_dfjyf, ");
		sql.append(" sj_zzs+sj_yys+sj_xfs+sj_qysds_gs+sj_qysds_ds+sj_tdsys+sj_tdzzs+sj_yhs+sj_qs+sj_cjs+sj_jyf+sj_dfjyf  ");	
		sql.append(" 	  from bd_qy_hy  where  1=1  ");
		sql.append(" 	and qymc='" + qyBean.getQymc() + "'");
		sql.append(" 		and id_year >='" + startDate + "' and id_year<='" + endDate + "'");
		sql.append(" order by id_year ");
		return sql.toString();

	}

	
	public static String getQyYjSjSqlByYear(QyBean qyBean,String year) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id_year,sj_zzs  ");	
		sql.append(" ,sj_yys,sj_xfs,  ");	
		sql.append(" sj_qysds_gs,sj_qysds_ds,  ");
		sql.append(" sj_tdsys,sj_tdzzs, ");	
		sql.append(" sj_yhs,sj_qs,sj_cjs, ");
		sql.append(" sj_jyf,sj_dfjyf, ");
		sql.append(" sj_zzs+sj_yys+sj_xfs+sj_qysds_gs+sj_qysds_ds+sj_tdsys+sj_tdzzs+sj_yhs+sj_qs+sj_cjs+sj_jyf+sj_dfjyf  ");	
		sql.append(" 	  from bd_qy_hy  where  1=1  ");
		sql.append(" 	and qymc='" + qyBean.getQymc() + "'");
		sql.append(" 		and id_year ='" + year +  "'");
		sql.append(" order by id_year ");
		return sql.toString();

	}
	public static String getQyXmxxSqlByYear(QyBean qyBean, String year) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select xmmc,yye,rq,k_date,beizhu from bd_qy_xmxx where 1=1    ");	
		sql.append(" 	and qymc='" + qyBean.getQymc() + "'");
		sql.append(" 		and substr(k_date,0,4) ='" + year + "'");
		sql.append(" order by k_date ");
		return sql.toString();

	}
	public static String getQySjxxSqlByYear(QyBean qyBean, String year) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select nsrmc,nsrsbh,zsxm,zspm,sjje,rkrq,ssgds from bd_qy_sjxx  where  1=1   ");	
		sql.append(" 	and nsrmc='" + qyBean.getQymc() + "'");
		sql.append(" 	and substr(k_date,0,4) ='" + year + "'");
		sql.append(" order by k_date ");
		return sql.toString();

	}
	
	
	public static String getQyXmxxSql(QyBean qyBean, String startDate, String endDate) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select xmmc,yye,rq,k_date,beizhu from bd_qy_xmxx where 1=1    ");	
		sql.append(" 	and qymc='" + qyBean.getQymc() + "'");
		sql.append(" 		and substr(k_date,0,4) >='" + startDate + "' and substr(k_date,0,4)<='" + endDate + "'");
		sql.append(" order by k_date ");
		return sql.toString();

	}
	
	public static String getQySjxxSql(QyBean qyBean, String startDate, String endDate) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select nsrmc,nsrsbh,zsxm,zspm,sjje,rkrq,ssgds from bd_qy_sjxx  where  1=1   ");	
		sql.append(" 	and nsrmc='" + qyBean.getQymc() + "'");
		sql.append(" 	and substr(k_date,0,4) >='" + startDate + "' and substr(k_date,0,4)<='" + endDate + "'");
		sql.append(" order by k_date ");
		return sql.toString();

	}
	
	
	public static QyBean toGetBean(ResultSet rs) {
		QyBean bean = new QyBean();
		try {
			bean.setQymc(rs.getString(1));
			bean.setNsrsbh(rs.getString(2));
			bean.setNsrzt(rs.getString(3));
			bean.setFddbr(rs.getString(4));
			bean.setZclx(rs.getString(5));
			bean.setJydz(rs.getString(6));
			bean.setZcdz(rs.getString(7));
			bean.setZczb(rs.getString(8));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}

	public static void main(String[] args) {

		QyBean qyBean=new QyBean();
		qyBean.setQymc("安徽省海铭星航运有限公司");
		List<Object> list=getQyJbxx(qyBean, "2014", "2016");
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
		
		QyBean bean=(QyBean) list.get(0);
		System.out.println(bean.getQymc());
		List<String[]> seList= (List<String[]>) list.get(1);
		
		for (int i = 0; i < seList.size(); i++) {
			for (int j = 0; j < seList.get(i).length; j++) {
				System.out.println(seList.get(i)[j]);
				
			}
		}
	}
}
