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

import com.ahcd.common.ExcelUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysDepartmentTemplateMapper;
import com.ahcd.dbutil.DBconnUtil;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.ExcelTemplateCol;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.pojo.SysMessage;
import com.ahzd.dao.ExcelTemplateMongoDao;

@Service("excelTemplateMongoService")
public class ExcelTemplateMongoService {

	@Autowired
	private ExcelTemplateMongoDao excelTemplateMongoDao;
	@Resource
	private SysDepartmentTemplateMapper sysDepartmentTemplateMapper;
	
	public boolean checkExistByName(String name){
		return excelTemplateMongoDao.checkExistByName(name);
	}
	public ExcelTemplate findByName(String name){
		return excelTemplateMongoDao.findByName(name);
	}
	public ExcelTemplate findById(String id){
		return excelTemplateMongoDao.findById(id);
	}
	public OpreateResult save(ExcelTemplate excelTemplate){
		OpreateResult op = new OpreateResult();
		if (excelTemplateMongoDao.checkExistByName(excelTemplate.getName())) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件已存在");
		} else {
			// 保存excel
			ExcelUtil.writeExcel(excelTemplate);
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
			excelTemplateMongoDao.save(excelTemplate);
			op.setStatusCode("200");
			op.setMessage("保存成功");
			op.setNavTabId("templateManage");
			op.setCallbackType("closeCurrent");
		}
		return op;
	}
	
	public List<ExcelTemplate> list(){
		List<ExcelTemplate> list =excelTemplateMongoDao.findAll();
		return list!=null?list:new ArrayList<ExcelTemplate>();
	}
	
	public  Page<ExcelTemplate> getExcelTemplatePage(Page<ExcelTemplate> page,ExcelTemplate queryBean){
		if(queryBean!=null){
			Query query=new Query();
			if(!StringUtil.isBlank(queryBean.getName())){
				query.addCriteria(Criteria.where("name").regex(".*?"+queryBean.getName().trim()+".*"));
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
			if(!StringUtil.isBlank(excelTemplate.getDatasource())){
				update.set("datasource", excelTemplate.getDatasource());
			}
			update.set("zqgqsj", excelTemplate.getZqgqsj());
			if(!StringUtil.isBlank(excelTemplate.getIdcode())){
				update.set("idcode", excelTemplate.getIdcode());
			}
			if(!StringUtil.isBlank(excelTemplate.getDesc())){
				update.set("desc", excelTemplate.getDesc());
			}
			if(excelTemplate.getCols()!=null && excelTemplate.getCols().size() > 0 ){
				update.set("cols", excelTemplate.getCols());
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
	
	/** 功能说明:根据当前用户ID获取部门的报送模板
	 */
	public Page<ExcelTemplate> getPageListByUserId(Page<ExcelTemplate> page, BigDecimal userId){
		Page<SysDepartmentTemplate> pager = new Page<SysDepartmentTemplate>();
		Map<String,BigDecimal> map = new HashMap<String,BigDecimal>();
		pager.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		pager.setEndRow(page.getPageNum() * page.getNumPerPage());
		map.put("userId", userId);
		pager.setQueryBean(map);		
		int totalCount=sysDepartmentTemplateMapper.countDepartmentTemplateByuserIdPage(pager);
		List<SysDepartmentTemplate> dtList = sysDepartmentTemplateMapper.selectDepartmentTemplateByuserIdPage(pager);
		if(dtList != null && dtList.size() > 0){
			List<ExcelTemplate> results = new ArrayList<ExcelTemplate>();
			List<ExcelTemplate> etList = excelTemplateMongoDao.findAll();
			Map<String,ExcelTemplate> mapt = new HashMap<String,ExcelTemplate>();
			for(ExcelTemplate excelTemplate : etList){
				if(excelTemplate == null) continue;
				mapt.put(excelTemplate.getName(), excelTemplate);
			}
			for(SysDepartmentTemplate template : dtList){
				ExcelTemplate excelTemplate = mapt.get(template.getTemplateName());
				if(excelTemplate == null) continue;
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
}
