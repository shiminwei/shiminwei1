package com.ahzd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahzd.dao.ExcelDataSourceMongoDao;
@Service("excelDataSourceMongoService")
public class ExcelDataSourceMongoService {
	@Autowired
	private ExcelDataSourceMongoDao excelDataSourceMongoDao;
	
	public boolean checkExistByName(String name){
		return excelDataSourceMongoDao.checkExistByName(name);
	}
	public DatasourceBean findByName(String name){
		return excelDataSourceMongoDao.findByName(name);
	}
	public DatasourceBean findById(String id){
		return excelDataSourceMongoDao.findById(id);
	}
	
	/**
	 * 说明 ： 获取数据源列表
	 * @author pxz
	 * 时间 ： 2017-04-05 
	 */
	public  Page<DatasourceBean> getDataSourceExcelTemplatePage(Page<DatasourceBean> page,DatasourceBean queryBean){
		if(queryBean!=null){
			Query query=new Query();
			if(!StringUtil.isBlank(queryBean.getName()) || !StringUtil.isBlank(queryBean.getId())){
				query.addCriteria(Criteria.where("_id").regex(".*?"+queryBean.getId().trim()+".*")
						.and("name").regex(".*?"+queryBean.getName().trim()+".*"));
				page.setQueryBean(query);
			}
		}
		return excelDataSourceMongoDao.getPageList(page);
	}
	
	/**
	 * 说明 ： 新增数据源
	 * @author pxz
	 * 时间 ： 2017-04-05 
	 */
	public OpreateResult save(DatasourceBean datasourceBean){
		OpreateResult result = new OpreateResult();
		if (excelDataSourceMongoDao.checkExistById(datasourceBean.getId())) {
			result.setStatusCode("300");
			result.setMessage("保存错误，文件已存在");
		} else {
			excelDataSourceMongoDao.save(datasourceBean);
			result.setStatusCode("200");
			result.setMessage("保存成功");
			result.setNavTabId("dataSourceManage");
			result.setCallbackType("closeCurrent");
		}
		return result;
	}
	
	/**
	 * 说明 ：修改数据源
	 * @author pxz
	 * 时间 ： 2017-04-05 
	 */
	public OpreateResult updateDataSourceConifg(DatasourceBean datasourceBean) {
		OpreateResult op = new OpreateResult();
		if (!excelDataSourceMongoDao.checkExistById(datasourceBean.getId())) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			Update update = new Update();
			if(!StringUtil.isBlank(datasourceBean.getName())){
				update.set("name", datasourceBean.getName());
			}
			if(!StringUtil.isBlank(datasourceBean.getType())){
				update.set("type", datasourceBean.getType());
			}
			if(!StringUtil.isBlank(datasourceBean.getAliasName())){
				update.set("aliasName", datasourceBean.getAliasName());
			}
			if(!StringUtil.isBlank(datasourceBean.getServer())){
				update.set("server", datasourceBean.getServer());
			}
			if(!StringUtil.isBlank(datasourceBean.getDatabase())){
				update.set("database", datasourceBean.getDatabase());
			}
			if(!StringUtil.isBlank(datasourceBean.getHost())){
				update.set("host", datasourceBean.getHost());
			}
			if(!StringUtil.isBlank(datasourceBean.getPort())){
				update.set("port", datasourceBean.getPort());
			}
			if(!StringUtil.isBlank(datasourceBean.getCharset())){
				update.set("charset", datasourceBean.getCharset());
			}
			if(!StringUtil.isBlank(datasourceBean.getUser())){
				update.set("user", datasourceBean.getUser());
			}
			if(!StringUtil.isBlank(datasourceBean.getPwd())){
				update.set("pwd", datasourceBean.getPwd());
			}
			if(!StringUtil.isBlank(datasourceBean.getSid())){
				update.set("sid", datasourceBean.getSid());
			}
			if(!StringUtil.isBlank(datasourceBean.getDesc())){
				update.set("desc", datasourceBean.getDesc());
			}
			excelDataSourceMongoDao.updateFirst(new Query(Criteria.where("id").is(datasourceBean.getId())), update);
			op.setStatusCode("200");
			op.setMessage("保存成功");
			op.setNavTabId("dataSourceManage");
			op.setCallbackType("closeCurrent");
		}
		return op;
	}
	
	
	/**
	 * 说明 ：删除数据源
	 * @author pxz
	 * 时间 ： 2017-04-05 
	 */
	public OpreateResult deleteDataSourceConifgById(String id) {
		OpreateResult op = new OpreateResult();
    	if (!excelDataSourceMongoDao.checkExistById(id)) {
		op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			excelDataSourceMongoDao.deleteById(id);
			op.setStatusCode("200");
			op.setMessage("删除成功");
		}
		return op;
	}
	
}
