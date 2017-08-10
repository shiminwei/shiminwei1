package com.ahcd.dbutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ahcd.common.Constant;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentInfo;

/**
 * @author : fei yang
 * @version ：2016年11月14日 下午5:51:26
 */
public class WebDataQueryJdbc {

	/**
	 * 
	 * 功能说明 : 获取SQL
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 上午10:27:02
	 * @param bean
	 * @return
	 */
	private static String getSelectSql(ExcelTemplate bean, List<SysDepartmentInfo> departmentInfos) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  ");
		for (int i = 0; i < bean.getCols().size(); i++) {
			sql.append(bean.getCols().get(i).getColumnName() + ", ");
		}
		sql.append(" rownum  as selectRow from " + bean.getTableName());
		sql.append(" where 1=1");
		sql.append(" and k_department_id in (");
		for (int i = 0; i < departmentInfos.size(); i++) {
			if (i != 0) {
				sql.append(",");
			}
			sql.append(" '" + departmentInfos.get(i).getDepartmentId() + "'");
			
		}
		sql.append(")");
		if (Constant.isNumeric(bean.getYearColName())) {
			sql.append(" and k_year = " + bean.getYearColName());
		}
		if (Constant.isNumeric(bean.getMonthColName())) {
			sql.append(" and k_month = " + bean.getMonthColName());
		}
		sql.append(" and k_del='0' ");
		return sql.toString();
	}

	/**
	 * 
	 * 功能说明 :获取分页SQL
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 上午10:27:11
	 * @param bean
	 * @param page
	 * @return
	 */
	private static String getRowNumSql(ExcelTemplate bean, Page<ExcelTemplate> page,
			List<SysDepartmentInfo> departmentInfos) {
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		StringBuffer sql = new StringBuffer();
		sql.append("select  * from (");
		sql.append(getSelectSql(bean, departmentInfos));
		sql.append("  ) where selectRow >=" + beginRow);
		sql.append("   and  selectRow <=" + endRow);
		System.out.println("查询SQL===》" + sql.length());
		return sql.toString();
	}

	/**
	 * 
	 * 功能说明 : 获取查询结果
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 上午10:27:52
	 * @param bean
	 * @param page
	 * @return
	 */
	public static List<List<String>> getListByExcelBean(ExcelTemplate bean, Page<ExcelTemplate> page,
			List<SysDepartmentInfo> departmentInfos) {
		List<List<String>> rsList = new ArrayList<List<String>>();
		Connection conn = JDBCUtils.getConnection(bean.getDatasource());
		if (conn == null) {
			return rsList;
		} else {
			String sql = getRowNumSql(bean, page, departmentInfos);
			PreparedStatement pstmt;
			try {
				pstmt = (PreparedStatement) conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					List<String> list = new ArrayList<String>();
					for (int i = 1; i <= bean.getCols().size(); i++) {
						list.add(rs.getString(i));
					}
					rsList.add(list);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rsList;
	}

	/**
	 * 
	 * 功能说明 :获取查询条数
	 * 
	 * @author : fei yang
	 * @version ：2016年11月15日 上午10:28:51
	 * @param bean
	 * @return
	 */
	public static int getCount(ExcelTemplate bean,List<SysDepartmentInfo> departmentInfos) {
		Connection conn = JDBCUtils.getConnection(bean.getDatasource());
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int count = 0;
		StringBuffer sql = new StringBuffer(" select count(*) as count from (  " + getSelectSql(bean,departmentInfos) + " )");
		try {
			conn = JDBCUtils.getConnection(bean.getDatasource());
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			rs.next();
			count = rs.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
}
