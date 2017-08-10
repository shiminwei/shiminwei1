package com.ahcd.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.ahcd.common.ExcelUtil;
import com.ahcd.common.FileUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.PropertyManager;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysDepartmentTemplateMapper;
import com.ahcd.dbutil.DBconnUtil;
import com.ahcd.dbutil.JDBCUtils;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.ExcelTemplateCol;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.service.IJsonConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Service("jsonConfigService")
public class JsonConfigServiceImpl implements IJsonConfigService {


	@Resource
	private SysDepartmentTemplateMapper sysDepartmentTemplateMapper;
	
	private final static String datasourcePath = PropertyManager.getConfigProperty("config_path")
			+ PropertyManager.getConfigProperty("datasource");
	private final static String excelTemplateConfigPath = PropertyManager.getConfigProperty("config_path")
			+ PropertyManager.getConfigProperty("upload_excel_path") + "config/";
	private final static String excelTemplatePath = PropertyManager.getConfigProperty("config_path")
			+ PropertyManager.getConfigProperty("upload_excel_path") + "excel/";

	/**
	 * 
	 * @param page
	 *            分页对象
	 * @param queryBean
	 *            查询条件
	 * @return
	 */
	public Page<DatasourceBean> getDatasourcePage(Page<DatasourceBean> page, DatasourceBean queryBean) {
		Page<DatasourceBean> pageList = new Page<DatasourceBean>();
		try {
			File file = new File(datasourcePath);
			String[] filelist = file.list();
			List<DatasourceBean> allResultList = new ArrayList<DatasourceBean>();
			int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
			int endRow = page.getPageNum() * page.getNumPerPage();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = datasourcePath + File.separator + filelist[i];
					if(!fileName.endsWith(".json")) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						DatasourceBean db = JSONObject.parseObject(fileContent, DatasourceBean.class);
						if (db == null)
							continue;
						boolean flag = true;
						if (queryBean != null) {
							if (db.getName() != null && queryBean.getName() != null
									&& !db.getName().contains(queryBean.getName())) {
								flag = false;
							}
							if (db.getId() != null && queryBean.getId() != null
									&& !db.getId().contains(queryBean.getId())) {
								flag = false;
							}
						}
						if (flag)
							allResultList.add(db);
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
			List<DatasourceBean> resultList = new ArrayList<DatasourceBean>();
			for (int i = 0; i < allResultList.size(); i++) {
				if (i >= beginRow && i < endRow) {
					resultList.add(allResultList.get(i));
				}
			}
			pageList.setResult(resultList);
			pageList.setTotalCount(allResultList.size());
			pageList.setPageNum(page.getPageNum());
			pageList.setNumPerPage(page.getNumPerPage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageList;
	}

	public List<String> getAllDatasourceId() {
		List<String> allResultList = new ArrayList<String>();
		File file = new File(datasourcePath);
		String[] filelist = file.list();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				String fileName=filelist[i];
				if(!fileName.endsWith(".json")) continue;
				fileName = filelist[i].contains(".") ? filelist[i].substring(0, filelist[i].lastIndexOf("."))
						: filelist[i];
				allResultList.add(fileName);
			}
		} else {
			System.out.println("文件夹不不存在");
		}
		return allResultList;
	}

	public OpreateResult saveDatasource(DatasourceBean db) {
		OpreateResult op = new OpreateResult();
		try {
			String filePath = datasourcePath + db.getId() + ".json";
			FileUtil.makeDirectory(filePath);
			if (FileUtil.isFileExist(filePath)) {
				op.setStatusCode("300");
				op.setMessage("保存错误，文件已存在");
			} else {
				if (DBconnUtil.testConnection(db)) {
					String jsonString = JSON.toJSONString(db, SerializerFeature.WriteMapNullValue);
					Writer write = new FileWriter(filePath);
					write.write(jsonString.toString());
					write.flush();
					write.close();
					op.setStatusCode("200");
					op.setMessage("保存成功");
					op.setNavTabId("datasourceConfigList");
					op.setCallbackType("closeCurrent");
				} else {
					op.setStatusCode("300");
					op.setMessage("数据库连接失败，请确认无误后提交");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return op;
	}

	public OpreateResult updateDatasource(DatasourceBean db) {
		OpreateResult op = new OpreateResult();
		try {
			String filePath = datasourcePath + db.getId() + ".json";
			if (!FileUtil.isFileExist(filePath)) {
				op.setStatusCode("300");
				op.setMessage("保存错误，文件不存在");
			} else {
				JDBCUtils.close(db.getId());
				if (DBconnUtil.testConnection(db)) {
					String jsonString = JSON.toJSONString(db, SerializerFeature.WriteMapNullValue);
					Writer write = new FileWriter(filePath);
					write.write(jsonString.toString());
					write.flush();
					write.close();
					JDBCUtils.reloadDatasource();
					op.setStatusCode("200");
					op.setMessage("保存成功");
					op.setNavTabId("datasourceConfigList");
					op.setCallbackType("closeCurrent");
				} else {
					op.setStatusCode("300");
					op.setMessage("数据库连接失败，请确认无误后提交");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return op;
	}

	public DatasourceBean getDatasourceById(String id) {
		DatasourceBean datasourceBean = new DatasourceBean();
		boolean isExist = false;
		try {
			File file = new File(datasourcePath);
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = datasourcePath + File.separator + filelist[i];
					if(!fileName.endsWith(".json")) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						DatasourceBean db = JSONObject.parseObject(fileContent, DatasourceBean.class);
						if (db != null && db.getId() != null && db.getId().equals(id)) {
							datasourceBean = db;
							isExist = true;
						}
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isExist) {
			return datasourceBean;
		} else {
			return null;
		}
	}

	public OpreateResult deleteDatasourceById(String id) {
		OpreateResult op = new OpreateResult();
		String filePath = datasourcePath + id + ".json";
		if (!FileUtil.isFileExist(filePath)) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			JDBCUtils.close(id);
			FileUtil.deleteFile(filePath);
			op.setStatusCode("200");
			op.setMessage("删除成功");
		}
		return op;
	}

	/**
	 * 
	 * @param page
	 *            分页对象
	 * @param queryBean
	 *            查询条件
	 * @return
	 */
	public Page<ExcelTemplate> getExcelTemplatePage(Page<ExcelTemplate> page, ExcelTemplate queryBean) {
		Page<ExcelTemplate> pageList = new Page<ExcelTemplate>();
		try {
			File file = new File(excelTemplateConfigPath);
			String[] filelist = file.list();
			List<ExcelTemplate> allResultList = new ArrayList<ExcelTemplate>();
			int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
			int endRow = page.getPageNum() * page.getNumPerPage();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = excelTemplateConfigPath + File.separator + filelist[i];
					if(!fileName.endsWith(".json")) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						ExcelTemplate excelTemplate = JSONObject.parseObject(fileContent, ExcelTemplate.class);
						if (excelTemplate == null)
							continue;
						boolean flag = true;
						if (queryBean != null) {
							if (excelTemplate.getName() != null && queryBean.getName() != null
									&& !excelTemplate.getName().contains(queryBean.getName())) {
								flag = false;
							}
						}
						if (flag)
							allResultList.add(excelTemplate);
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
			List<ExcelTemplate> resultList = new ArrayList<ExcelTemplate>();
			for (int i = 0; i < allResultList.size(); i++) {
				if (i >= beginRow && i < endRow) {
					resultList.add(allResultList.get(i));
				}
			}
			pageList.setResult(resultList);
			pageList.setTotalCount(allResultList.size());
			pageList.setPageNum(page.getPageNum());
			pageList.setNumPerPage(page.getNumPerPage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageList;
	}

	public OpreateResult saveTemplateConifg(ExcelTemplate excelTemplate) {
		OpreateResult op = new OpreateResult();
		try {
			String filePath = excelTemplateConfigPath + excelTemplate.getName() + ".json";
			FileUtil.makeDirectory(filePath);
			if (FileUtil.isFileExist(filePath)) {
				op.setStatusCode("300");
				op.setMessage("保存错误，文件已存在");
			} else {
				ExcelUtil.writeExcel(excelTemplate);
				String jsonString = JSON.toJSONString(excelTemplate, SerializerFeature.WriteMapNullValue);
				StringBuffer sql = new StringBuffer();
				if (excelTemplate.getCols() != null && excelTemplate.getCols().size() > 0) {
					sql.append("create table ").append(excelTemplate.getTableName()).append(" ( ");
					sql.append("k_id     VARCHAR2(36) primary key  not null,");
					sql.append("k_idcode VARCHAR2(20),");
					sql.append("k_date DATE,");
					sql.append("k_year NUMBER(4),");
					sql.append("k_month NUMBER(2),");
					sql.append("k_del    CHAR(1),");
					sql.append("k_area_id     VARCHAR2(36),");
					sql.append("k_department_id     VARCHAR2(36),");
					sql.append("k_user_id     VARCHAR2(36) ,");
					for (ExcelTemplateCol excelTemplateCol : excelTemplate.getCols()) {
						sql.append(excelTemplateCol.getColumnName()).append(" ");
						if (excelTemplateCol.getColType() == 1) {
							sql.append("VARCHAR2(").append(excelTemplateCol.getColLength()).append("),");
						} else if (excelTemplateCol.getColType() == 2) {
							sql.append("INTEGER").append(",");
						} else if (excelTemplateCol.getColType() == 3) {
							sql.append("BINARY_DOUBLE").append(",");
						} else if (excelTemplateCol.getColType() == 4) {
							sql.append("DATE").append(",");
						}
					}

					if (sql.substring(sql.length() - 1, sql.length()).equals(",")) {
						sql = new StringBuffer(sql.substring(0, sql.length() - 1));
					}
					sql.append(")");

					Connection conn = DBconnUtil.getConnection(excelTemplate.getDatasource());
					if (conn != null) {
						PreparedStatement stmt;

						try {
							stmt = conn.prepareStatement(sql.toString());
							stmt.executeUpdate();
							conn.commit();
						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
				}

				Writer write = new FileWriter(filePath);
				write.write(jsonString.toString());
				write.flush();
				write.close();
				// 保存excel
				op.setStatusCode("200");
				op.setMessage("保存成功");
				op.setNavTabId("templateManage");
				op.setCallbackType("closeCurrent");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return op;
	}

	@Override
	public boolean checkExistByTableName(String tableName) {
		boolean flag = false;
		try {
			File file = new File(excelTemplateConfigPath);
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = excelTemplateConfigPath + File.separator + filelist[i];
					if(!fileName.endsWith(".json")) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						ExcelTemplate excelTemplate = JSONObject.parseObject(fileContent, ExcelTemplate.class);
						if (excelTemplate == null)
							continue;
						else if (excelTemplate.getTableName().equalsIgnoreCase(tableName)) {
							flag = true;
						}
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public OpreateResult deleteTemplateConifgByName(String name) {
		OpreateResult op = new OpreateResult();
		String filePath = excelTemplateConfigPath + name + ".json";
		if (!FileUtil.isFileExist(filePath)) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			FileUtil.deleteFile(filePath);
			op.setStatusCode("200");
			op.setMessage("删除成功");
		}
		return op;
	}

	@Override
	public List<DatasourceBean> getAllDatasource() {
		List<DatasourceBean> allResultList = new ArrayList<DatasourceBean>();
		try {
			File file = new File(datasourcePath);
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = datasourcePath + File.separator + filelist[i];
					if(!fileName.endsWith(".json")) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						DatasourceBean db = JSONObject.parseObject(fileContent, DatasourceBean.class);
						if (db == null)
							continue;
						allResultList.add(db);
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allResultList;
	}

	@Override
	public ExcelTemplate getExcelTemplateByName(String name) {
		ExcelTemplate excelTemplate = new ExcelTemplate();
		boolean isExist = false;
		try {
			File file = new File(excelTemplateConfigPath);
			String[] filelist = file.list();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = excelTemplateConfigPath + File.separator + filelist[i];
					if(!fileName.endsWith(".json")) continue;
 					String fileContent = FileUtil.getFileContent(fileName);
 					if (!StringUtil.isBlank(fileContent)) {
						ExcelTemplate excelTemplateTmp = JSONObject.parseObject(fileContent, ExcelTemplate.class);
						if (excelTemplateTmp == null)
							continue;
						else if (excelTemplateTmp.getName().equals(name)) {
							excelTemplate = excelTemplateTmp;
							isExist = true;
						}
					}
				}
			} else {
				System.out.println("文件夹不不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (isExist) {
			return excelTemplate;
		} else {
			return null;
		}
	}

	@Override
	public OpreateResult updateTemplateConifg(ExcelTemplate excelTemplate) {
		OpreateResult op = new OpreateResult();
		try {
			String filePath = excelTemplateConfigPath + excelTemplate.getName() + ".json";
			FileUtil.makeDirectory(filePath);
			if (!FileUtil.isFileExist(filePath)) {
				op.setStatusCode("300");
				op.setMessage("保存错误，文件不存在");
			} else {
				String jsonString = JSON.toJSONString(excelTemplate, SerializerFeature.WriteMapNullValue);
				Writer write = new FileWriter(filePath);
				write.write(jsonString.toString());
				write.flush();
				write.close();
				// 保存excel
				op.setStatusCode("200");
				op.setMessage("保存成功");
				op.setNavTabId("templateManage");
				op.setCallbackType("closeCurrent");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return op;
	}

	/**
	 * 
	   * 功能说明    : 根据当前用户ID获取部门的报送模板
	   * @author   : fei yang 
	   * @version ：2016年11月2日 下午3:54:18 
	   * @param page
	   * @param userId
	   * @return
	 */
	public Page<ExcelTemplate> getPageListByUserId(Page<ExcelTemplate> page, BigDecimal userId) {
		List<SysDepartmentTemplate> list = sysDepartmentTemplateMapper.selectDepartmentTemplateByuserId(userId);
		try {
			File file = new File(excelTemplateConfigPath);
			String[] filelist = file.list();
			List<ExcelTemplate> allResultList = new ArrayList<ExcelTemplate>();
			int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
			int endRow = page.getPageNum() * page.getNumPerPage();
			if (filelist != null) {
				for (int i = 0; i < filelist.length; i++) {
					String fileName = excelTemplateConfigPath + File.separator + filelist[i];
					if(!fileName.endsWith(".json")) continue;
					String fileContent = FileUtil.getFileContent(fileName);
					if (!StringUtil.isBlank(fileContent)) {
						ExcelTemplate excelTemplate = JSONObject.parseObject(fileContent, ExcelTemplate.class);
						if (excelTemplate == null)
							continue;
						boolean flag = true;
						if (flag)
							allResultList.add(excelTemplate);
					}
				}
			} else {
				System.out.println("文件夹不存在");
			}
			List<ExcelTemplate> resultList = new ArrayList<ExcelTemplate>();
			int size = 0; // size=1 改為 size=0;  裴习柱   2017-02-23 16:51
			for (int i = 0; i < allResultList.size(); i++) {
				for (int j = 0; j < list.size(); j++) {
					if (allResultList.get(i).getName().equals(list.get(j).getTemplateName())) {
						if (size >= beginRow && size < endRow) {
							allResultList.get(i).setZq(list.get(j).getReportPeroid());//替换周期
							resultList.add(allResultList.get(i));
							size++;
						}
					}
				}
			}
			page.setResult(resultList);
			page.setTotalCount(size); // list.size() 改為 size;  裴习柱   2017-02-23 16:51
		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

}
