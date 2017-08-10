package com.ahzd.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahcd.common.FieldTypeEnum;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.PingYinUtil;
import com.ahcd.common.StringUtil;
import com.ahcd.dbutil.DBconnUtil;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.ExcelBean;
import com.ahcd.pojo.ExcelColumnBean;
import com.ahcd.pojo.ExcelRowBean;
import com.ahcd.pojo.ExcelSheetBean;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.ExcelTemplateCol;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.service.impl.SysDepartmentTemplateServiceImpl;
import com.ahzd.dao.ExcelTemplateMongoDao;
import com.github.drinkjava2.jdialects.Dialect;

@Service("excelTemplateMongoService")
public class ExcelTemplateMongoService {

	@Autowired
	private ExcelTemplateMongoDao excelTemplateMongoDao;

	@Resource
	private SysDepartmentTemplateServiceImpl departmentTemplateService;
	
	@Resource
	private DataSourceMongoService dataSourceMongoService;

	public boolean checkExistByName(String name) {
		return excelTemplateMongoDao.checkExistByName(name);
	}

	public ExcelTemplate findByName(String name) {
		return excelTemplateMongoDao.findByName(name);
	}

	public ExcelTemplate findById(String id) {
		return excelTemplateMongoDao.findById(id);
	}

	public OpreateResult save(ExcelTemplate excelTemplate) {
		OpreateResult op = new OpreateResult();
		if (excelTemplateMongoDao.checkExistByName(excelTemplate.getName())) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件已存在");
		} else {
			// 保存excel
			//ExcelUtil.writeExcel(excelTemplate);
			DatasourceBean datasourceBean = dataSourceMongoService.findById(excelTemplate.getDatasource());
			if(datasourceBean == null ){
				op.setStatusCode("300");
				op.setMessage("保存错误，数据源不存在");
				return op;
			}
			StringBuffer sql = new StringBuffer();
			if (excelTemplate.getCols() != null && excelTemplate.getCols().size() > 0) {
				Dialect d;
				if ("oracle".equals(datasourceBean.getType())) {// oracle数据源
					 d=Dialect.Oracle10gDialect;  
				} else if ("mysql".equals(datasourceBean.getType())) {// mysql数据源
					 d=Dialect.MySQL55Dialect;  
				} else if ("sqlserver".equals(datasourceBean.getType())) {// sqlserver数据源
					 d=Dialect.SQLServer2008Dialect;  
				} else {
					op.setStatusCode("300");
					op.setMessage("保存错误，数据源不存在");
					return op;
				}
				sql.append("create table ").append(d.check(excelTemplate.getTableName())).append(" ( ");
				sql.append(d.VARCHAR("k_id", 36)+" primary key  not null"+",");
				sql.append(d.VARCHAR("k_idcode", 20)+",");
				sql.append(d.DATE("k_create_date ")+",");
				sql.append(d.NCHAR("k_year ", 4)+",");
				sql.append(d.NCHAR("k_month ", 2)+",");
				sql.append(d.CHAR("k_del ", 1)+",");
				sql.append(d.VARCHAR("k_area_id", 36)+",");
				sql.append(d.VARCHAR("k_department_id", 36)+",");
				sql.append(d.VARCHAR("k_user_id", 36)+",");
				for (ExcelTemplateCol excelTemplateCol : excelTemplate.getCols()) {
					if (excelTemplateCol.getColType() == FieldTypeEnum.VARCHAR.getIndex()) {
						sql.append(d.VARCHAR(excelTemplateCol.getColumnName(), excelTemplateCol.getColLength())+",");
					} else if (excelTemplateCol.getColType() == FieldTypeEnum.INTEGER.getIndex()) {
						sql.append(d.INTEGER(excelTemplateCol.getColumnName())+",");
					} else if (excelTemplateCol.getColType() == FieldTypeEnum.FLOAT.getIndex()) {
						sql.append(d.FLOAT(excelTemplateCol.getColumnName())+",");
					} else if (excelTemplateCol.getColType() == FieldTypeEnum.DATE.getIndex()) {
						sql.append(d.DATE(excelTemplateCol.getColumnName())+",");
					}
				}

				if (sql.substring(sql.length() - 1, sql.length()).equals(",")) {
					sql = new StringBuffer(sql.substring(0, sql.length() - 1));
				}
				sql.append(")"+ d.engine(" DEFAULT CHARSET=utf8"));

				Connection conn = DBconnUtil.getConnection(excelTemplate.getDatasource());
				if (conn != null) {

					PreparedStatement stmt;

					try {
						conn.setAutoCommit(false);
						stmt = conn.prepareStatement(sql.toString());
						stmt.executeUpdate();
						conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
						return new OpreateResult("300","新增失败,新建表异常");
					} finally {
						try {
							conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
							return new OpreateResult("300","新增失败,新建表异常");
						}
					}
				}
			}
			excelTemplateMongoDao.save(excelTemplate);
			op.setStatusCode("200");
			op.setMessage("保存成功");
			op.setNavTabId("templateManage");
			op.setCallbackType("closeCurrent");
		}
		return op;
	}

	public List<ExcelTemplate> list() {
		List<ExcelTemplate> list = excelTemplateMongoDao.findAll();
		return list != null ? list : new ArrayList<ExcelTemplate>();
	}

	public Page<ExcelTemplate> getExcelTemplatePage(Page<ExcelTemplate> page, ExcelTemplate queryBean) {
		if (queryBean != null) {
			Query query = new Query();
			if (!StringUtil.isBlank(queryBean.getName())) {
				query.addCriteria(Criteria.where("name").regex(".*?" + queryBean.getName().trim() + ".*"));
				page.setQueryBean(query);
			}
		}
		return excelTemplateMongoDao.getPageList(page);
	}

	public boolean checkExistByTableName(String tableName) {
		return excelTemplateMongoDao.checkExistByTableName(tableName);
	}

	public OpreateResult updateTemplateConifg(ExcelTemplate excelTemplate) {
		OpreateResult op = new OpreateResult();
		if (!excelTemplateMongoDao.checkExistByName(excelTemplate.getName())) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			Update update = new Update();
			if (!StringUtil.isBlank(excelTemplate.getDatasource())) {
				update.set("datasource", excelTemplate.getDatasource());
			}
			update.set("zqgqsj", excelTemplate.getZqgqsj());
			if (!StringUtil.isBlank(excelTemplate.getIdcode())) {
				update.set("idcode", excelTemplate.getIdcode());
			}
			if (!StringUtil.isBlank(excelTemplate.getDesc())) {
				update.set("desc", excelTemplate.getDesc());
			}
			if (StringUtil.isNotEmpty(excelTemplate.getName())) {
				update.set("name", excelTemplate.getName());
			}
			if (!StringUtil.isBlank(excelTemplate.getStart())) {
				update.set("start", excelTemplate.getStart());
			}
			if (!StringUtil.isBlank(excelTemplate.getEnd())) {
				update.set("end", excelTemplate.getEnd());
			}
			excelTemplateMongoDao.updateFirst(new Query(Criteria.where("name").is(excelTemplate.getName())), update);
			op.setStatusCode("200");
			op.setMessage("保存成功");
			op.setNavTabId("templateManage");
			op.setCallbackType("closeCurrent");
		}
		return op;
	}

	public OpreateResult deleteTemplateConifgByName(String name) {
		OpreateResult op = new OpreateResult();
		if (!excelTemplateMongoDao.checkExistByName(name)) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			excelTemplateMongoDao.deleteByName(name);
			op.setStatusCode("200");
			op.setMessage("删除成功");
		}
		return op;
	}

	/**
	 * 
	 * 功能说明 : 删除模板
	 * 
	 * @author : fei yang
	 * @version ：2017年4月12日 下午3:08:54
	 * @param id
	 * @return
	 */
	@Transactional
	public OpreateResult deleteTemplateConifgById(String id) {
		OpreateResult op = new OpreateResult("300", "删除失败");
		ExcelTemplate excelTemplate = excelTemplateMongoDao.findById(id);
		if (excelTemplate != null && StringUtil.isNotEmpty(excelTemplate.getName())) {
			departmentTemplateService.deleteByTemplateName(excelTemplate.getName());//删除关联表的已经存模板名称
			excelTemplateMongoDao.deleteById(id);
			op = new OpreateResult("200", "删除成功");
		}
		return op;
	}

	/**
	 * 功能说明:根据当前用户ID获取部门的报送模板
	 */
	public Page<ExcelTemplate> getPageListByUserId(Page<ExcelTemplate> page, BigDecimal userId) {
		Page<SysDepartmentTemplate> pager = new Page<SysDepartmentTemplate>();
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		pager.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		pager.setEndRow(page.getPageNum() * page.getNumPerPage());
		map.put("userId", userId);
		pager.setQueryBean(map);
		int totalCount = departmentTemplateService.countDepartmentTemplateByuserIdPage(pager);
		List<SysDepartmentTemplate> dtList = departmentTemplateService.selectDepartmentTemplateByuserIdPage(pager);
		if (dtList != null && dtList.size() > 0) {
			List<ExcelTemplate> results = new ArrayList<ExcelTemplate>();
			List<ExcelTemplate> etList = excelTemplateMongoDao.findAll();
			Map<String, ExcelTemplate> mapt = new HashMap<String, ExcelTemplate>();
			for (ExcelTemplate excelTemplate : etList) {
				if (excelTemplate == null)
					continue;
				mapt.put(excelTemplate.getName(), excelTemplate);
			}
			for (SysDepartmentTemplate template : dtList) {
				ExcelTemplate excelTemplate = mapt.get(template.getTemplateName());
				if (excelTemplate == null)
					continue;
				excelTemplate.setZq(template.getReportPeroid());
				results.add(excelTemplate);
			}
			page.setTotalCount(totalCount);
			page.setResult(results);
			page.setPageNum(pager.getPageNum());
			page.setNumPerPage(pager.getNumPerPage());
		}
		return page;
	}

	/**
	 * 
	 * 功能说明 :添加模板时候检验模板合法性
	 * 
	 * @author : fei yang
	 * @version ：2017年4月11日 上午11:37:44
	 * @param uploadExcel
	 * @return
	 */
	public int toCheckTemplate(ExcelBean excelBean) {
		if (excelBean == null) {
			return -1;// 需要上传模板存在问题
		} else if (excelBean.getExcelSheetBeans() == null || excelBean.getExcelSheetBeans().size() <= 0) {
			return -2;// 模板Excel为空文件
		} else {
			for (int i = 0; i < excelBean.getExcelSheetBeans().size(); i++) {
				ExcelSheetBean excelSheetBean = excelBean.getExcelSheetBeans().get(i);
				if (i == 0) {// 第一张表
					if (excelSheetBean == null) {
						return -3;// 为空表
					} else if (excelSheetBean.getTableHeader() == null) {
						return -4;// 为空表头
					} else if (excelSheetBean.getRows() != null) {
						return -5;// 不需要内容
					} else if (excelSheetBean.getTableHeader() != null) {
						List<ExcelColumnBean> columnBeans = excelSheetBean.getTableHeader().getExcelColumnBeans();
						if (columnBeans == null || columnBeans.size() <= 0) {
							return -4;// 为空表头
						} else {
							for (int j = 0; j < columnBeans.size(); j++) {
								if (!StringUtil.isBlank(columnBeans.get(j).getContent())
										&& StringUtil.isBlank(columnBeans.get(j).getContent().toString())) {
									return -6;// 表头列名称存在空值
								}
							}
						}
					}
				} else {
					if (excelSheetBean != null) {
						return -7;// 其他表不未空
					}
				}
			}
		}
		return 1;// 没问题的模板可以上传
	}

	/**
	 * 
	 * 功能说明 : 更具ExcelBean 获取初始模板 对象
	 * 
	 * @author : fei yang
	 * @version ：2017年4月11日 下午1:16:25
	 * @param bean
	 * @return
	 */
	public ExcelTemplate geTemplateByExcelBean(ExcelBean bean) {
		ExcelTemplate template = new ExcelTemplate();
		if (bean != null) {
			if (StringUtil.isNotEmpty(bean.getFileName()) && bean.getExcelSheetBeans() != null
					&& bean.getExcelSheetBeans().size() > 0) {
				ExcelSheetBean excelSheetBean = bean.getExcelSheetBeans().get(0);// 目前只取第一个表的内容
				if (excelSheetBean != null && excelSheetBean.getTableHeader() != null) {
					template.setName(bean.getFileName().substring(0, bean.getFileName().lastIndexOf(".")));
					List<ExcelTemplateCol> cols = geTemplateColsByExcelHerder(excelSheetBean.getTableHeader());
					template.setCols(cols);
				}
			}
		}
		return template;
	}

	/**
	 * 
	 * 功能说明 :根据表头获取新的 ExcelTemplateCol
	 * 
	 * @author : fei yang
	 * @version ：2017年4月11日 下午1:40:42
	 * @param tableHeader
	 * @return
	 */
	public List<ExcelTemplateCol> geTemplateColsByExcelHerder(ExcelRowBean tableHeader) {
		List<ExcelTemplateCol> cols = new ArrayList<ExcelTemplateCol>();
		if (tableHeader != null && tableHeader.getExcelColumnBeans().size() > 0) {
			for (int i = 0; i < tableHeader.getExcelColumnBeans().size(); i++) {
				ExcelColumnBean columnBean = tableHeader.getExcelColumnBeans().get(i);
				ExcelTemplateCol col = new ExcelTemplateCol();
				if (StringUtil.isBlank(columnBean.getContent())) {// 表头存在空的字符串
					return new ArrayList<ExcelTemplateCol>();
				} else {
					col.setColOrder(columnBean.getColumnIndex());// 排序
					col.setName(columnBean.getContent().toString().trim());
					col.setColumnName(PingYinUtil.getFirstSpell(columnBean.getContent().toString().trim()));
					cols.add(col);
				}
			}
		}
		return PingYinUtil.getFirstSpellColList(cols);
	}
}
