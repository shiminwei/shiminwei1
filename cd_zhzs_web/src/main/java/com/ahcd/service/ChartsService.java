package com.ahcd.service;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.ahcd.common.Constant;
import com.ahcd.common.DBconn;
import com.ahcd.common.PropertiesUntil;
import com.ahcd.common.StringUtil;
import com.ahcd.common.XmlUtils;
import com.ahcd.pojo.ChartsBean;
import com.ahcd.pojo.ChartsChileBean;
import com.ahcd.pojo.ConditionBean;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.FieldBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ResultBean;
import com.alibaba.fastjson.JSONArray;

/**
 * @author : fei yang
 * @version ：2016年11月25日 下午1:56:11
 */
@Service("chartsService")
public class ChartsService {

	/**
	 * 
	 * 功能说明 :获取税额map
	 * 
	 * @author : fei yang
	 * @version ：2016年11月25日 下午2:03:49
	 * @param sql
	 * @param datasourcerId
	 * @return
	 */
	public static List<String[]> getSeMap(String datasourcerId) {
		List<String[]> list = new ArrayList<String[]>();
		Connection conn = DBconn.getConnectionById(datasourcerId);
		Statement stmt = null;
		ResultSet rs = null;
		String sql = getNewSql();

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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	public static int getSum(String datasourcerId) {
		Connection conn =DBconn.getConnectionById(datasourcerId);
		Statement stmt = null;
		int count = 0;
		try {
			if (conn != null) {
				stmt = conn.createStatement();
				String countSql = " select sum(sjje_hj)   as record_  from ( ";
				countSql += getNewSql();
				countSql += " ) where rkrq like '%2016%'";
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

	public static String getNewSql() {

		return "   select * from sj_gs_ds_hj  ";
	}

	/**
	 * 
	 * 功能说明 :获取查询SQL
	 * 
	 * @author : fei yang
	 * @version ：2016年11月25日 下午2:12:02
	 * @param type
	 * @return
	 */
	public static String getSelectSql(int type) {
		if (type == 1) {// 国税
			String sql = " select rkrq,round(sum(sjje)/10000) as sjje from ( ";
			sql += " select   substr(replace(rkrq,'-',''),0,6) as rkrq, replace(sjje,',','') as sjje ,k_del from zhzs_gsj_rkxx where k_del=0 ";
			sql += " ) group by rkrq order by rkrq";
			return sql;
		} else if (type == 2) {// 地税
			String sql = " select k_date,round(sum(sjje)/10000) as sjje from (  ";
			sql += " select   k_date , replace(sjje,',','') as sjje ,k_del from zhzs_dsj_jrkxx where k_del=0 ";
			sql += " ) group by k_date order by k_date ";
			return sql;
		} else {// 综合
			String sql = "select t1.rkrq,t1.sjje+t2.sjje  as sjje from (";
			sql += getSelectSql(1) + "	) t1 left join  (";
			sql += getSelectSql(2) + ") t2 on t1.rkrq=t2.k_date order by   t1.rkrq";
			return sql;
		}
	}

	public static String getYearKey(String year, int i) {
		if (i < 10) {
			return year + "0" + i;
		} else {
			return year + i;
		}
	}

	public static String getYearValue(String yearKey, String key, String value) {
		System.out.println("yearKey:" + yearKey + "key:" + key + "value:" + value);
		boolean flag = false;
		for (int j = 1; j <= 12; j++) {
			if (yearKey.equals(key)) {
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println("一样了" + value);
			return value;
		} else {
			return "0";
		}
	}

	public Page<ChartsBean> getList(Page<ChartsBean> page, ChartsBean bean) {
		PropertiesUntil propertiesUntil = new PropertiesUntil();
		String xmlPath = propertiesUntil.getPropertyValue(Constant.CHARTS_PATH);
		File file = new File(xmlPath);
		String[] filelist = file.list();
		List<ChartsBean> list = new ArrayList<ChartsBean>();
		Page<ChartsBean> pageList = new Page<ChartsBean>();
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		List<ChartsBean> resultList = new ArrayList<ChartsBean>();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				String fileName = xmlPath + "\\" + filelist[i];
				list = queryChartsList(list, bean, xmlPath, fileName);
			}
		} else {
			System.out.println("文件夹不不存在");
		}

		for (int i = 0; i < list.size(); i++) {
			if (i >= beginRow && i < endRow) {
				resultList.add(list.get(i));
			}
		}
		pageList.setResult(resultList);
		pageList.setTotalCount(list.size());
		pageList.setPageNum(page.getPageNum());
		pageList.setNumPerPage(page.getNumPerPage());
		return pageList;
	}

	public static ChartsBean getBean(String filePath) {
		ChartsBean bean=new ChartsBean();
		bean=XmlUtils.getBean(filePath, ChartsBean.class);
//		XMLDocument doc = new XMLDocument(filePath);
//		ChartsBean bean = new ChartsBean();
//		bean.setId(doc.getRoot().getChildNode("id").getNodeValue());
//		bean.setName(doc.getRoot().getChildNode("name").getNodeValue());
//		bean.setDatasource(doc.getRoot().getChildNode("datasource").getNodeValue());
//		bean.setLayoutType(doc.getRoot().getChildNode("layoutType").getNodeValue());
//		XMLNode[] ChileNodes = doc.getRoot().getAllChildNode("chartsChileBean");
//		List<ChartsChileBean> chartsChileBeans = new ArrayList<ChartsChileBean>();
//		for (int i = 0; i < ChileNodes.length; i++) {
//			ChartsChileBean chartsChileBean = new ChartsChileBean();
//			chartsChileBean.setWidth(ChileNodes[i].getAttributeValue("width"));
//			chartsChileBean.setHeigh(ChileNodes[i].getAttributeValue("heigh"));
//			chartsChileBean.setFangwei(ChileNodes[i].getAttributeValue("fangwei"));
//			chartsChileBean.setType(ChileNodes[i].getAttributeValue("type"));
//			chartsChileBean.setSql(ChileNodes[i].getChildNode("sql").getNodeValue());
//			chartsChileBeans.add(chartsChileBean);
//		}
//		bean.setChartsChileBeans(chartsChileBeans);
		return bean;
	}

	public static List<ChartsBean> queryChartsList(List<ChartsBean> list, ChartsBean bean, String xmlPath,
			String fileName) {
		File readfile = new File(fileName);
		if (!readfile.isDirectory()) {
			if (readfile.getName().endsWith(".xml")) {
				if (StringUtil.isNotEmpty(bean.getId()) || StringUtil.isNotEmpty(bean.getName())) {
					if (StringUtil.isNotEmpty(bean.getId()) && StringUtil.isNotEmpty(bean.getName())) {// ID和名称模糊查询
						ChartsBean oldbean = getBean(fileName);
						if (StringUtil.isInclude(readfile.getName(), bean.getId())
								&& StringUtil.isInclude(oldbean.getName(), bean.getName())) {
							list.add(oldbean);
						}
					} else if (StringUtil.isNotEmpty(bean.getId())) {// ID模糊查询
						if (StringUtil.isInclude(readfile.getName(), bean.getId())) {
							ChartsBean oldbean = getBean(fileName);
							list.add(oldbean);
						}
					} else if (StringUtil.isNotEmpty(bean.getName())) {// 名称模糊查询
						ChartsBean oldbean = getBean(fileName);
						if (StringUtil.isInclude(oldbean.getName(), bean.getName())) {
							list.add(oldbean);
						}
					}
				} else {
					ChartsBean oldbean = getBean(fileName);
					list.add(oldbean);
				}
			}
		}
		return list;
	}
	/**
	 * 
	 * 功能说明 : 获取xml路径
	 * 
	 * @author : fei yang
	 * @version ：2016年10月21日 下午3:09:42
	 * @return
	 */
	public static String getXmlPath() {
		PropertiesUntil propertiesUntil = new PropertiesUntil();
		String xmlPath = propertiesUntil.getPropertyValue(Constant.CHARTS_PATH);
		File file = new File(xmlPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return xmlPath;
	}
}
