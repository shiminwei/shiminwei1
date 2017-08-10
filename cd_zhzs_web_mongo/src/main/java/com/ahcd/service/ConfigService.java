package com.ahcd.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.common.Constant;
import com.ahcd.common.DBconn;
import com.ahcd.common.DbUntil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.PropertiesUntil;
import com.ahcd.common.StringUtil;
import com.ahcd.common.XmlUtils;
import com.ahcd.pojo.ConditionBean;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.FieldBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ProcedureParameterBean;
import com.ahcd.pojo.ResultBean;
import com.ahcd.pojo.TableBean;
import com.ahcd.pojo.TableColumnBean;
import com.ahcd.pojo.TableRowBean;
import com.ahcd.service.impl.JsonConfigServiceImpl;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月29日 上午11:04:21 类说明
 */
@Service
public class ConfigService {

	@Resource
	private JsonConfigServiceImpl jsonConfigService;

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
		String xmlPath = propertiesUntil.getPropertyValue(Constant.CONFIG_PATH) + "/"
				+ propertiesUntil.getPropertyValue(Constant.CONFIG_XML_PATH);
		File file = new File(xmlPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return xmlPath;
	}

	/**
	 * 
	 * 功能说明 :更具配置ID获取对象
	 * 
	 * @author : fei yang
	 * @version ：2017年3月9日 下午5:14:21
	 * @param id
	 * @return
	 */
	public ConfigBean getConfigBeanById(String id) {
		ConfigBean bean = XmlUtils.getBean(getXmlPath() + "/" + "con" + id + ".xml", ConfigBean.class);
		/**
		 * 以下是为了以前是否值的更改 现在改成对应子页面的条件ID
		 */
		if (bean != null && bean.getResults() != null && bean.getResults().getFieldBeans() != null) {
			List<FieldBean> beans = bean.getResults().getFieldBeans();
			for (int i = 0; i < beans.size(); i++) {
				FieldBean fieldBean = beans.get(i);
				if (fieldBean != null && fieldBean.getIsByValue() != null) {
					if (fieldBean.getIsByValue().equals("0") || fieldBean.getIsByValue().equals("1")) {
						fieldBean.setIsByValue("");
					}
				}
			}
		}

		return bean;
	}

	/**
	 * 
	 * 类说明:获取文件夹下所有配置文件集合
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年9月29日 下午1:47:17
	 * @param xmlPath
	 * @return:
	 */
	public Page<ConfigBean> getList(Page<ConfigBean> page, ConfigBean bean) {
		String xmlPath = getXmlPath();
		File file = new File(xmlPath);
		String[] filelist = file.list();
		List<ConfigBean> list = new ArrayList<ConfigBean>();
		Page<ConfigBean> pageList = new Page<ConfigBean>();
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		if (filelist != null) {
			if (StringUtil.isBlank(bean.getId()) && StringUtil.isBlank(bean.getName())) {
				for (int i = 0; i < filelist.length; i++) {
					if (i >= beginRow && i < endRow) {
						String fileName = xmlPath + "/" + filelist[i];
						list = conditionQuery(list, bean, xmlPath, fileName);
					}
				}
				pageList.setResult(list);
				pageList.setTotalCount(filelist.length);
				pageList.setPageNum(page.getPageNum());
				pageList.setNumPerPage(page.getNumPerPage());
				return pageList;
			} else {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = xmlPath + "/" + filelist[i];
					list = conditionQuery(list, bean, xmlPath, fileName);
				}
			}
		}
		List<ConfigBean> resultList = new ArrayList<ConfigBean>();
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

	/**
	 * 
	 * 功能说明 : 获取全部集合
	 * 
	 * @author : fei yang
	 * @version ：2016年10月31日 下午5:01:36
	 * @param bean
	 * @return
	 */
	public List<ConfigBean> getAllList() {
		String xmlPath = getXmlPath();
		File file = new File(xmlPath);
		String[] filelist = file.list();
		List<ConfigBean> list = new ArrayList<ConfigBean>();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				String fileName = xmlPath + "\\" + filelist[i];
				ConfigBean bean = XmlUtils.getBean(fileName, ConfigBean.class);
				if (bean != null) {
					list.add(bean);
				}
			}
		} else {
			System.out.println("文件夹不不存在");
		}
		return list;
	}

	/**
	 * 
	 * 功能说明 :模糊查询
	 * 
	 * @author : fei yang
	 * @version ：2016年10月21日 上午10:22:48
	 * @param list
	 * @param bean
	 * @param xmlPath
	 * @param fileName
	 * @return
	 */
	public static List<ConfigBean> conditionQuery(List<ConfigBean> list, ConfigBean bean, String xmlPath,
			String fileName) {
		File readfile = new File(fileName);
		if (!readfile.isDirectory()) {
			if (readfile.getName().endsWith(".xml")) {
				ConfigBean oldbean = XmlUtils.getBean(fileName, ConfigBean.class);
				if (oldbean == null) {
					System.out.println(fileName);
				}
				if (StringUtil.isNotEmpty(bean.getId()) || StringUtil.isNotEmpty(bean.getName())) {
					if (StringUtil.isNotEmpty(bean.getId()) && StringUtil.isNotEmpty(bean.getName())) {// ID和名称模糊查询
						if (StringUtil.isInclude(readfile.getName(), bean.getId())
								&& StringUtil.isInclude(oldbean.getName(), bean.getName())) {
							list.add(oldbean);
						}
					} else if (StringUtil.isNotEmpty(bean.getId())) {// ID模糊查询
						if (StringUtil.isInclude(readfile.getName(), bean.getId())) {
							list.add(oldbean);
						}
					} else if (StringUtil.isNotEmpty(bean.getName()) && oldbean != null
							&& StringUtil.isNotEmpty(oldbean.getName())) {// 名称模糊查询
						if (StringUtil.isInclude(oldbean.getName(), bean.getName())) {
							list.add(oldbean);
						}
					}
				} else {
					list.add(oldbean);
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * 类说明: 新增或者修改
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年9月19日 下午10:23:41
	 * @param basePath
	 * @param baseBean
	 * @param userbean
	 *            type 1新增 2修改SQL 3 结果集 4条件区域
	 * @return:
	 */
	public OpreateResult saveOrUpdate(ConfigBean bean, ResultBean resultBean, Integer type) {
		OpreateResult opreateResult = new OpreateResult("300", "操作失败", "configList", "closeCurrent", "");
		String xmlPath = getXmlPath();
		String id = "";
		if (StringUtil.isBlank(bean.getId())) {
			id = Constant.getNowStr();
			bean.setId(id);
		} else {
			id = bean.getId();
		}
		xmlPath = xmlPath + "/" + "con" + id + ".xml";
		if (type == 1) { // SQL管理
			List<FieldBean> newFieldBeans = new ArrayList<FieldBean>();
			List<ConditionBean> conditionBeans = getConditionListBySql(resultBean.getSql());
			if ("SQL".equals(bean.getResultType())) {
				newFieldBeans = getFieldBySql(id, bean.getDatasource(), resultBean.getSql());
			} else if ("存储过程".equals(bean.getResultType())) {
				newFieldBeans = getFieldByProcedureSql(id, bean.getDatasource(), resultBean.getSql());
				String[] newStr = Constant.getProcedureSql(resultBean.getSql());
				DatasourceBean datasourceBean = jsonConfigService.getDatasourceById(bean.getDatasource());
				TableBean tableBean = getTable(newStr[0], datasourceBean.getId(), datasourceBean.getUser());
				List<ConditionBean> procedureConditionBeans = getConditionListBySql(newStr[0]);
				List<ProcedureParameterBean> parameterBeans = getParameters(tableBean.getRows(),
						procedureConditionBeans);
				bean.setParameters(parameterBeans);
			}

			// 增改结果区域
			resultBean.setFieldBeans(newFieldBeans);
			bean.setResults(resultBean);
			// 增改条件区域
			bean.setConditionBeans(conditionBeans);

		} else if (type == 2) {// 结果集
			ConfigBean oldBean = getConfigBeanById(id);
			for (int i = 0; i < resultBean.getFieldBeans().size(); i++) {
				if (resultBean.getFieldBeans().get(i).getMergeNum() > 1) {
					resultBean.setShowType(2); // 合并类类型
					break;
				}
			}
			oldBean.setResults(resultBean);
			bean = oldBean;
		} else if (type == 3) {// 条件区域
			ConfigBean oldBean = getConfigBeanById(id);
			oldBean.setConditionBeans(bean.getConditionBeans());
			bean = oldBean;
		}

		if (XmlUtils.saveOrUpdateXml(xmlPath, bean)) {
			opreateResult = new OpreateResult("200", "操作成功", "configList", "closeCurrent", "");
		}
		return opreateResult;

	}

	/**
	 * 
	 * 类说明: 删除
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年9月19日 下午10:51:46
	 * @param basePath
	 * @param id:
	 */
	public boolean Delete(String ids) {
		boolean flag = true;
		String xmlPath = getXmlPath();
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			String path = xmlPath + "/con" + id[i] + ".xml";
			File file = new File(path);
			flag = file.delete();
			if (!flag) {
				return false;
			}
		}
		return flag;
	}

	/**
	 * 
	 * 功能说明 : 更具SQL 获取返回列
	 * 
	 * @author : fei yang
	 * @version ：2017年3月16日 下午2:56:43
	 * @param configId
	 * @param datasourceId
	 * @param sql
	 * @return
	 */
	public List<FieldBean> getFieldBySql(String configId, String datasourceId, String sql) {
		List<FieldBean> fieldBeans = new ArrayList<FieldBean>();
		if (StringUtil.isBlank(sql) || StringUtil.isBlank(datasourceId)) {
			return fieldBeans;
		}
		try {
			Connection conn = DBconn.getConnectionById(datasourceId);
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData data = rs.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				FieldBean fieldBean = new FieldBean();
				fieldBean.setSqlFielName(data.getColumnName(i));
				fieldBean.setJspFielName(data.getColumnName(i));
				fieldBeans.add(fieldBean);
			}
			if (StringUtil.isNotEmpty(configId)) {
				String filePath = getXmlPath() + "/" + "con" + configId + ".xml";
				ConfigBean oldBean = XmlUtils.getBean(filePath, ConfigBean.class);
				if (oldBean != null) {
					fieldBeans = toCheckeFieldList(fieldBeans, oldBean.getResults().getFieldBeans());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<FieldBean>();
		}
		return fieldBeans;
	}

	/**
	 * 
	 * 功能说明 : 更具存储过程获取返回列
	 * 
	 * @author : fei yang
	 * @version ：2017年3月16日 下午2:57:42
	 * @param configId
	 * @param datasourceId
	 * @param sql
	 * @param beans
	 * @return
	 */
	public List<FieldBean> getFieldByProcedureSql(String configId, String datasourceId, String sql) {
		List<FieldBean> fieldBeans = new ArrayList<FieldBean>();
		if (StringUtil.isBlank(sql) || StringUtil.isBlank(datasourceId)) {
			return fieldBeans;
		}
		String[] newStr = sql.split(";");
		if (newStr.length != 2) {
			return fieldBeans;
		}
		JsonConfigServiceImpl impl = new JsonConfigServiceImpl();
		DatasourceBean datasourceBean = impl.getDatasourceById(datasourceId);
		TableBean bean = getTable(sql, datasourceId, datasourceBean.getUser());
		if (bean.getColumns() != null) {
			List<TableColumnBean> columns = bean.getColumns();
			if (columns.size() > 0) {
				fieldBeans = getFieldBySql(configId, datasourceId, newStr[1]);
			}
		}
		return fieldBeans;
	}

	/**
	 * 
	 * 功能说明 : 判断当前列值的LIST 与原有的列值 List比较返回新的LIST
	 * 
	 * @author : fei yang
	 * @version ：2017年3月3日 下午3:14:05
	 * @param newFieldList
	 * @param oldFieldList
	 * @return
	 */
	public static List<FieldBean> toCheckeFieldList(List<FieldBean> newFieldList, List<FieldBean> oldFieldList) {
		for (int i = 0; i < newFieldList.size(); i++) {
			for (int j = 0; j < oldFieldList.size(); j++) {
				if (newFieldList.get(i).getSqlFielName().equals(oldFieldList.get(j).getSqlFielName())) {
					if (!newFieldList.get(i).getJspFielName().equals(oldFieldList.get(j).getJspFielName())
							&& newFieldList.get(i).getSqlFielName().equals(newFieldList.get(i).getJspFielName())) {
						newFieldList.get(i).setJspFielName(oldFieldList.get(j).getJspFielName());
					}
				}
			}
		}
		return newFieldList;
	}

	/**
	 * 
	 * 功能说明 : 获取条件集合
	 * 
	 * @author : fei yang
	 * @version ：2017年3月10日 下午4:06:16
	 * @param sql
	 * @return
	 */
	public static List<ConditionBean> getConditionListBySql(String sql) {
		List<ConditionBean> conditionBeans = new ArrayList<ConditionBean>();
		if (StringUtil.isNotEmpty(sql)) {
			String[] newStr = sql.split("@");
			int length = newStr.length;
			if (length % 2 == 0 && length > 1) {
				length = length - 1;
			}
			for (int i = 0; i < length; i++) {
				if (i % 2 != 0) {
					ConditionBean conditionBean = new ConditionBean();
					conditionBean.setConditionId(newStr[i]);
					conditionBean.setDispname(newStr[i]);
					conditionBeans.add(conditionBean);
				}
			}
		}
		return duplicateRemovalList(conditionBeans);
	}

	/**
	 * 
	 * 功能说明 :增加条件
	 * 
	 * @author : fei yang
	 * @version ：2017年3月10日 下午3:19:04
	 * @param conditionBean
	 * @param id
	 * @return
	 */
	public boolean addCondition(ConditionBean conditionBean, String id) {
		if (StringUtil.isNotEmpty(id)) {
			String xmlPath = getXmlPath() + "/" + "con" + id + ".xml";
			ConfigBean bean = XmlUtils.getBean(xmlPath, ConfigBean.class);
			if (bean != null && StringUtil.isNotEmpty(bean.getId())) {
				bean.getConditionBeans().add(conditionBean);
				return XmlUtils.saveOrUpdateXml(xmlPath, bean);
			}
		}
		return false;
	}

	/**
	 * 
	 * 功能说明 :删除条件
	 * 
	 * @author : fei yang
	 * @version ：2017年3月10日 下午4:21:40
	 * @param id
	 * @param conditionId
	 * @return
	 */
	public boolean delCondition(String id, String conditionId) {
		if (StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(conditionId)) {
			String xmlPath = getXmlPath() + "/" + "con" + id + ".xml";
			ConfigBean bean = XmlUtils.getBean(xmlPath, ConfigBean.class);
			List<ConditionBean> conditionBeans = bean.getConditionBeans();
			if (conditionBeans.size() > 0) {
				for (int i = 0; i < conditionBeans.size(); i++) {
					if (conditionBeans.get(i).getConditionId().equals(conditionId)) {
						conditionBeans.remove(i);
						bean.setConditionBeans(conditionBeans);
						return XmlUtils.saveOrUpdateXml(xmlPath, bean);
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * 功能说明 :查询条件上下移动
	 * 
	 * @author : feiyue yang
	 * @version ：2017年3月10日 下午4:21:40
	 * @param id
	 * @param conditionId
	 * @return
	 */
	public int upOrDown(String id, String conditionId, String type) {
		int a = 4;
		if (StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(conditionId)) {
			String xmlPath = getXmlPath() + "/" + "con" + id + ".xml";
			ConfigBean bean = XmlUtils.getBean(xmlPath, ConfigBean.class);
			List<ConditionBean> conditionBeans = bean.getConditionBeans();
			ConditionBean[] ary = new ConditionBean[conditionBeans.size()];
			for (int i = 0; i < conditionBeans.size(); i++) {
				ary[i] = conditionBeans.get(i);
			}
			if (conditionBeans.size() > 0) {
				for (int i = 0; i < conditionBeans.size(); i++) {
					// Object[] ary1 = conditionBeans.toArray();
					if (conditionBeans.get(i).getConditionId().equals(conditionId)) {
						if (type.equals("1")) {
							if (i == 0) {
								a = 2;
								break;
							}
							ConditionBean temp = (ConditionBean) ary[i];
							ary[i] = ary[i - 1];
							ary[i - 1] = temp;
						} else {
							if (i == conditionBeans.size() - 1) {
								a = 3;
								break;
							}
							ConditionBean temp = (ConditionBean) ary[i];
							ary[i] = ary[i + 1];
							ary[i + 1] = temp;
						}
						conditionBeans = Arrays.asList(ary);
						bean.setConditionBeans(conditionBeans);
						XmlUtils.saveOrUpdateXml(xmlPath, bean);
						a = 1;
						break;
					}
				}
			}
		}
		return a;
	}

	
	/**
	 * 
	 * 功能说明 : 更具存储过程的SQL 获取内容
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午12:28:02
	 * @param sql
	 * @param datasourceId
	 * @param datasourceUser
	 * @return
	 */
	public static TableBean getTable(String sql, String datasourceId, String datasourceUser) {
		TableBean bean = new TableBean();
		try {
			if (StringUtil.isBlank(sql) || StringUtil.isBlank(datasourceId) || StringUtil.isBlank(datasourceUser)) {
				return bean;
			}
			sql = sql.toUpperCase();
			String procedureName = sql.substring(sql.indexOf("CALL") + "CALL".length(), sql.indexOf("("));
			if (StringUtil.isNotEmpty(procedureName)) {
				procedureName = procedureName.trim();
			}
			String newSql = DbUntil.getSelectProcedureSql(procedureName, datasourceUser);
			bean = DbUntil.toQuerysSql(newSql, datasourceId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 
	 * 功能说明 :检验SQL
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午2:03:50
	 * @param bean
	 * @return
	 */
	public String toCheckSql(ConfigBean bean) {
		List<FieldBean> fieldBeans = new ArrayList<FieldBean>();
		if ("SQL".equals(bean.getResultType())) {
			fieldBeans = getFieldBySql("", bean.getDatasource(), bean.getResults().getSql());
		} else if ("存储过程".equals(bean.getResultType())) {
			String[] sql = Constant.getProcedureSql(bean.getResults().getSql());
			if (sql != null) {
				DatasourceBean datasourceBean = jsonConfigService.getDatasourceById(bean.getDatasource());
				TableBean tableBean = getTable(sql[0], datasourceBean.getId(), datasourceBean.getUser());
				List<TableRowBean> rows = tableBean.getRows();
				List<ConditionBean> conditionBeans = getConditionListBySql(sql[0]);
				if (rows != null && conditionBeans != null && rows.size() == conditionBeans.size()) {// 参数个数一致
					fieldBeans = getFieldBySql("", datasourceBean.getId(), sql[1]);
				} else {
					return "-1";
				}
			}
		}
		if (fieldBeans.size() > 0) {
			return "1";
		}
		return "0";
	}

	/**
	 * 
	 * 功能说明 :查询存储过程获取参数列表
	 * 
	 * @author : fei yang
	 * @version ：2017年3月17日 下午2:32:29
	 * @param rows
	 * @return
	 */
	public static List<ProcedureParameterBean> getParameters(List<TableRowBean> rows,
			List<ConditionBean> conditionBeans) {
		List<ProcedureParameterBean> parameterBeans = new ArrayList<ProcedureParameterBean>();
		if (rows.size() == conditionBeans.size()) {
			for (int i = 0; i < rows.size(); i++) {
				ProcedureParameterBean bean = new ProcedureParameterBean();
				Map<String, String> map = rows.get(i).getValue();
				bean.setId(Integer.valueOf(map.get("SEQUENCE")));
				bean.setType(map.get("DATA_TYPE"));
				bean.setConditionId(conditionBeans.get(i).getConditionId());
				parameterBeans.add(bean);
			}
		}
		return parameterBeans;
	}

	/**
	 * 
	 * 功能说明 :去重条件ConditionBean ID相同的
	 * 
	 * @author : fei yang
	 * @version ：2017年3月28日 上午11:09:21
	 * @param list
	 * @return
	 */
	public static List<ConditionBean> duplicateRemovalList(List<ConditionBean> list) {
		List<ConditionBean> newList = new ArrayList<ConditionBean>();
		for (int i = 0; i < list.size(); i++) {
			boolean falg = true;
			ConditionBean bean = list.get(i);
			for (int j = 0; j < newList.size(); j++) {
				if (bean.getConditionId().equals(newList.get(j).getConditionId())) {
					falg = false;
					break;
				}
			}
			if (falg) {
				newList.add(bean);
			}
		}

		return newList;
	}

}
