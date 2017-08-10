package com.ahcd.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ahzd.dao.ConfigBeanDao;
import com.ahzd.service.DataSourceMongoService;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月29日 上午11:04:21 类说明
 */
@Service
public class ConfigService {

	@Autowired
	private ConfigBeanDao configBeanDao;

	@Resource
	private DataSourceMongoService dataSourceMongoService;

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
		return configBeanDao.findOne(id);
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
			if (StringUtil.isBlank(bean.getFunctionId()) && StringUtil.isBlank(bean.getName())) {
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
				if (StringUtil.isNotEmpty(bean.getFunctionId()) || StringUtil.isNotEmpty(bean.getName())) {
					if (StringUtil.isNotEmpty(bean.getFunctionId()) && StringUtil.isNotEmpty(bean.getName())) {// ID和名称模糊查询
						if (StringUtil.isInclude(readfile.getName(), bean.getFunctionId())
								&& StringUtil.isInclude(oldbean.getName(), bean.getName())) {
							list.add(oldbean);
						}
					} else if (StringUtil.isNotEmpty(bean.getFunctionId())) {// ID模糊查询
						if (StringUtil.isInclude(readfile.getName(), bean.getFunctionId())) {
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
	 *            type 1新增配置 3 结果集 4条件区域 4修改SQL
	 * @return:
	 */
	public OpreateResult saveOrUpdate(ConfigBean bean, ResultBean resultBean, Integer type) {
		OpreateResult opreateResult = new OpreateResult("300", "操作失败", "configList", "closeCurrent", "");
		// 获取配置文件的路径
		// String xmlPath = getXmlPath();
		String id = "";
		String flag = "";
		ConfigBean oldBean = null;
		if (StringUtil.isBlank(bean.getFunctionId())) {
			id = Constant.getNowStr();
			bean.setFunctionId(id);
			flag = "add";
		} else {
			id = bean.getFunctionId();
			oldBean = getConfigBeanById(id);
		}
		// xmlPath = xmlPath + "/" + "con" + id + ".xml";
		if (type == 1 || type == 4) { // SQL管理
			List<FieldBean> newFieldBeans = new ArrayList<FieldBean>();
			List<ConditionBean> conditionBeans = getConditionListBySql(resultBean.getSql());
			if ("SQL".equals(bean.getResultType())) {
				newFieldBeans = getFieldBySql(id, bean.getDatasource(), resultBean.getSql());
			} else if ("存储过程".equals(bean.getResultType())) {
				newFieldBeans = getFieldByProcedureSql(id, bean.getDatasource(), resultBean.getSql());
				String[] newStr = Constant.getProcedureSql(resultBean.getSql());
				// 根据ID获取对应的数据源对象
				DatasourceBean datasourceBean = dataSourceMongoService.findById(bean.getDatasource());
				TableBean tableBean = getTable(newStr[0], datasourceBean.getId(), datasourceBean.getUser());
				List<ConditionBean> procedureConditionBeans = getConditionListBySql(newStr[0]);
				List<ProcedureParameterBean> parameterBeans = getParameters(tableBean.getRows(),
						procedureConditionBeans);
				bean.setParameters(parameterBeans);
			}

			if (type == 1) {
				// 新增结果区域配置
				resultBean.setFieldBeans(newFieldBeans);
				bean.setResults(resultBean);
				// 新增条件区域配置
				bean.setConditionBeans(conditionBeans);
			} else {
				if (needChangeCondition(oldBean, conditionBeans)) {// 检查条件区域
					bean.setConditionBeans(conditionBeans);
				} else {
					bean.setConditionBeans(oldBean.getConditionBeans());
				}
				if (needChangeField(oldBean, newFieldBeans)) {
					resultBean.setFieldBeans(newFieldBeans);
					bean.setResults(resultBean);
				} else {
					resultBean.setFieldBeans(oldBean.getResults().getFieldBeans());
					bean.setResults(resultBean);
				}
			}

		} else if (type == 2) {// 结果集
			if (resultBean!=null&&resultBean.getFieldBeans()!=null&&resultBean.getFieldBeans().size()>0) {
				for (int i = 0; i < resultBean.getFieldBeans().size(); i++) {
					String linkUrl=resultBean.getFieldBeans().get(i).getLink();
					if (StringUtil.isNotEmpty(linkUrl)) {
						if (linkUrl.indexOf("?")<0) {
							resultBean.getFieldBeans().get(i).setLink(linkUrl+"?1=1");
						}
					}
				}
			}
			oldBean.setResults(resultBean);
			bean = oldBean;
		} else if (type == 3) {// 条件区域
			oldBean.setConditionBeans(bean.getConditionBeans());
			bean = oldBean;
		}
		bean.setFunctionId(id);
		ConfigBean result = null;
		changeShowType(bean.getResults());
		

		
		if (flag.equals("add")) {
			bean.setMaxRowNum(getMaxRowNum(bean));
			result = configBeanDao.save(bean);
			if (result != null) {
				opreateResult = new OpreateResult("200", "操作成功", "configList", "closeCurrent", "");
				return opreateResult;
			}

		} else {
			bean.setMaxRowNum(getMaxRowNum(bean));
			configBeanDao.update(bean);
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
	 * @param id
	 *            :
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
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData data = rs.getMetaData();
			for (int i = 1; i <= data.getColumnCount(); i++) {
				FieldBean fieldBean = new FieldBean();
				fieldBean.setSqlFielName(data.getColumnName(i));
				fieldBean.setJspFielName(data.getColumnName(i));
				fieldBeans.add(fieldBean);
			}
			if (StringUtil.isNotEmpty(configId)) {
				// String filePath = getXmlPath() + "/" + "con" + configId
				// + ".xml";
				// ConfigBean oldBean = XmlUtils.getBean(filePath,
				// ConfigBean.class);

				ConfigBean oldBean = configBeanDao.findById(configId);
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
		// JsonConfigServiceImpl impl = new JsonConfigServiceImpl();
		// DatasourceBean datasourceBean = impl.getDatasourceById(datasourceId);

		// 通过ID从ｍｏｎｇｏＤＢ中获取对应的数据源对象
		DatasourceBean datasourceBean = dataSourceMongoService.findById(datasourceId);
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
			// String xmlPath = getXmlPath() + "/" + "con" + id + ".xml";
			// ConfigBean bean = XmlUtils.getBean(xmlPath, ConfigBean.class);
			ConfigBean bean = configBeanDao.findOne(id);
			bean.setFunctionId(id);
			if (bean != null && StringUtil.isNotEmpty(bean.getFunctionId())) {
				bean.getConditionBeans().add(conditionBean);
				// return XmlUtils.saveOrUpdateXml(xmlPath, bean);
				configBeanDao.update(bean);
				return true;
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
			// String xmlPath = getXmlPath() + "/" + "con" + id + ".xml";
			// ConfigBean bean = XmlUtils.getBean(xmlPath, ConfigBean.class);
			ConfigBean bean = configBeanDao.findOne(id);
			List<ConditionBean> conditionBeans = bean.getConditionBeans();
			if (conditionBeans.size() > 0) {
				for (int i = 0; i < conditionBeans.size(); i++) {
					if (conditionBeans.get(i).getConditionId().equals(conditionId)) {
						conditionBeans.remove(i);
						bean.setConditionBeans(conditionBeans);
						bean.setFunctionId(id);
						// return XmlUtils.saveOrUpdateXml(xmlPath, bean);
						configBeanDao.update(bean);
						return true;
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
			// String xmlPath = getXmlPath() + "/" + "con" + id + ".xml";
			// ConfigBean bean = XmlUtils.getBean(xmlPath, ConfigBean.class);
			ConfigBean bean = configBeanDao.findOne(id);
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
						// XmlUtils.saveOrUpdateXml(xmlPath, bean);
						configBeanDao.update(bean);
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
	 * 功能说明 : 判断是否需要检查预警字符串，避免不必要的循环判断，
	 * 
	 * @author : fei yang
	 * @version ：2017年3月13日 上午10:39:23
	 * @param fieldBeans
	 * @return
	 */
	public static Map<Integer, String> needCheckWarningStr(List<FieldBean> fieldBeans) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if (fieldBeans != null) {
			for (int i = 0; i < fieldBeans.size(); i++) {
				if (StringUtil.isNotEmpty(fieldBeans.get(i).getWarningStr())) {
					map.put(i, fieldBeans.get(i).getWarningStr());
				}
			}
		}
		return map;
	}

	public static Map<Integer, String> needCheckByValue(List<FieldBean> fieldBeans) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		if (fieldBeans != null) {
			for (int i = 0; i < fieldBeans.size(); i++) {
				if (StringUtil.isNotEmpty(fieldBeans.get(i).getIsByValue())) {
					map.put(i, fieldBeans.get(i).getIsByValue());
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * 功能说明 :添加预警标识
	 * 
	 * @author : fei yang
	 * @version ：2017年3月16日 下午1:44:34
	 * @param map
	 * @param contents
	 * @return
	 */
	public static String[] toGetWarningStr(Map<Integer, String> map, String[] contents) {
		try {
			if (map != null && contents != null) {
				for (Map.Entry<Integer, String> entry : map.entrySet()) {
					if (contents[entry.getKey() + 1].equals(entry.getValue())) {
						contents[0] = contents[0] + ",style='color: red'";
					}
				}
			}
			return contents;
		} catch (Exception e) {
			return contents;
		}
	}

	/**
	 * 
	 * 功能说明 :添加预警标识
	 * 
	 * @author : fei yang
	 * @version ：2017年3月16日 下午1:44:34
	 * @param map
	 * @param contents
	 * @return
	 */
	public static String[] toGetByValueStr(Map<Integer, String> map, String[] contents) {
		try {
			if (map != null && contents != null) {
				contents[0] = contents[0] + "@";
				for (Map.Entry<Integer, String> entry : map.entrySet()) {
					for (int i = 0; i < contents.length; i++) {
						if (i + 1 == entry.getKey()) {
							contents[0] = contents[0] + "&" + entry.getValue() + "=" + contents[entry.getKey() + 1];
						} else if (i == 0 && i == entry.getKey()) {
							contents[0] = contents[0] + "&" + entry.getValue() + "=" + contents[entry.getKey() + 1];
						}
					}
				}
			}
			return contents;
		} catch (Exception e) {
			return contents;
		}
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
			// fieldBeans = getFieldBySql("", bean.getDatasource(), bean
			// .getResults().getSql());、

			fieldBeans = getFieldBySql(bean.getFunctionId(), bean.getDatasource(), bean.getResults().getSql());
		} else if ("存储过程".equals(bean.getResultType())) {
			String[] sql = Constant.getProcedureSql(bean.getResults().getSql());
			if (sql != null) {

				// 通过ID在ｍｏｎｇｏＤＢ获取对应的数据源对象
				DatasourceBean datasourceBean = dataSourceMongoService.findById(bean.getDatasource());
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

	public static int getMaxRowNum(ConfigBean bean) {
		int maxRowNum = 1;
		if (bean != null && bean.getResults() != null && bean.getResults().getFieldBeans() != null
				&& bean.getResults().getFieldBeans().size() > 0) {
			for (int i = 0; i < bean.getResults().getFieldBeans().size(); i++) {
				FieldBean fieldBean = bean.getResults().getFieldBeans().get(i);
				if (StringUtil.isNotEmpty(fieldBean.getRowNum()) && StringUtil.isInteger(fieldBean.getRowNum())) {
					if (Integer.valueOf(fieldBean.getRowNum()) > maxRowNum) {
						maxRowNum = Integer.valueOf(fieldBean.getRowNum());
					}
				}
				bean.setMaxRowNum(maxRowNum);
			}
		}
		return maxRowNum;
	}

	/**
	 * 
	 * 功能说明 :是否需要重新替换条件区域
	 * 
	 * @author : fei yang
	 * @version ：2017年5月11日 下午1:51:06
	 * @param oldBean
	 * @param conditionBeans
	 * @return
	 */
	public static boolean needChangeCondition(ConfigBean oldBean, List<ConditionBean> conditionBeans) {
		boolean needChang = false;
		if (oldBean != null) {
			if (oldBean.getConditionBeans() != null && oldBean.getConditionBeans().size() == conditionBeans.size()) {// 检查条件区域
				for (int i = 0; i < oldBean.getConditionBeans().size(); i++) {
					if (!oldBean.getConditionBeans().get(i).getConditionId()
							.equals(conditionBeans.get(i).getConditionId())) {
						needChang = true;
						break;
					}
				}
			} else {
				needChang = true;
			}
		} else {
			needChang = true;
		}
		return needChang;
	}

	/**
	 * 
	 * 功能说明 : 是否需要重新替换结果集
	 * 
	 * @author : fei yang
	 * @version ：2017年5月11日 下午1:53:24
	 * @param oldBean
	 * @param fieldBeans
	 * @return
	 */
	public static boolean needChangeField(ConfigBean oldBean, List<FieldBean> fieldBeans) {
		boolean needChang = false;
		if (oldBean != null) {
			if (oldBean.getResults() != null && oldBean.getResults().getFieldBeans() != null
					&& oldBean.getResults().getFieldBeans().size() == fieldBeans.size()) {
				for (int i = 0; i < oldBean.getResults().getFieldBeans().size(); i++) {
					if (!oldBean.getResults().getFieldBeans().get(i).getSqlFielName()
							.equals(fieldBeans.get(i).getSqlFielName())) {
						needChang = true;
						break;
					}
				}
			} else {
				needChang = true;
			}
		} else {
			needChang = true;
		}
		return needChang;
	}

	/**
	 * 
	   * 功能说明    : 改变表头展现类型 （合并列展示）
	   * @author   : fei yang 
	   * @version ：2017年5月11日 下午4:05:37 
	   * @param resultBean
	 */
	public static void changeShowType(ResultBean resultBean) {
		for (int i = 0; i < resultBean.getFieldBeans().size(); i++) {
			String mergeNum = resultBean.getFieldBeans().get(i).getMergeNum();
			if (StringUtil.isInteger(mergeNum) && Integer.valueOf(mergeNum) > 1) {
				resultBean.setShowType(2); // 合并类类型
				break;
			}
		}
	}
	/**
	 * 
	   * 功能说明    :去除重复展现类型的条件区域 
	   * @author   : fei yang 
	   * @version ：2017年5月22日 下午2:43:58 
	   * @param conditionBeans
	   * @return
	 */
	public  List<ConditionBean> getDistinctList(List<ConditionBean> conditionBeans) {
		List<ConditionBean> newList = new ArrayList<ConditionBean>();
		if (conditionBeans != null && conditionBeans.size() > 0) {
			ConditionBean conditionBeani = null;
			ConditionBean conditionBeanj = null;
			for (int i = 0; i < conditionBeans.size(); i++) {
				if (i == 0) {
					newList.add(conditionBeans.get(i));
				} else {
					conditionBeani = conditionBeans.get(i);
					for (int j = 0; j < newList.size(); j++) {
						conditionBeanj = newList.get(j);
						if (StringUtil.isNotEmpty(conditionBeani.getDisptype())
								&& StringUtil.isNotEmpty(conditionBeanj.getDisptype())) {
							if (!conditionBeani.getDisptype().equals(conditionBeanj.getDisptype())) {
								newList.add(conditionBeani);
							}
						}
					}
				}
			}
		}
		return newList;
	}
}
