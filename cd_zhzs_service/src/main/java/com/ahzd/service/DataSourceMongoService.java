package com.ahzd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.dbutil.DBconnUtil;
import com.ahcd.dbutil.JDBCUtils;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahzd.dao.DataSourceMongoDao;

@Service("dataSourceMongoService")
public class DataSourceMongoService {
	@Autowired
	private DataSourceMongoDao dataSourceMongoDao;

	public boolean checkExistByName(String name) {
		return dataSourceMongoDao.checkExistByName(name);
	}

	public DatasourceBean findByName(String name) {
		return dataSourceMongoDao.findByName(name);
	}

	public DatasourceBean findById(String id) {
		return dataSourceMongoDao.findById(id);
	}

	/**
	 * 说明 ： 获取数据源列表
	 * 
	 * @author pxz 时间 ： 2017-04-05
	 */
	public Page<DatasourceBean> getDataSourcePage(Page<DatasourceBean> page, DatasourceBean queryBean) {
		if (queryBean != null) {
			Query query = new Query();
			if (!StringUtil.isBlank(queryBean.getName())) {
				query.addCriteria(Criteria.where("name").regex(".*?" + queryBean.getName().trim() + ".*"));
				page.setQueryBean(query);
			}
			if (queryBean.getId()!=null) {
				query.addCriteria(Criteria.where("_id").regex(".*?" + queryBean.getId() + ".*"));
				page.setQueryBean(query);
			}
		}
		return dataSourceMongoDao.getPageList(page);
	}

	/**
	 * 说明 ： 新增数据源
	 * 
	 * @author pxz 时间 ： 2017-04-05
	 */
	public OpreateResult save(DatasourceBean datasourceBean) {
		OpreateResult result = new OpreateResult();
		if (DBconnUtil.testConnection(datasourceBean)) {
			dataSourceMongoDao.save(datasourceBean);
			JDBCUtils.reloadDatasource();
			result.setStatusCode("200");
			result.setMessage("保存成功");
			result.setNavTabId("datasourceConfigList");
			result.setCallbackType("closeCurrent");
		} else {
			result.setStatusCode("300");
			result.setMessage("数据库连接失败，请确认无误后提交");
		}
	
		return result;
	}
	/**
	 * 说明 ： 新增数据源
	 * 
	 * @author pxz 时间 ： 2017-04-05
	 */
	public DatasourceBean getDataSourceById(String dataSourceId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").regex(".*?" +dataSourceId+ ".*"));
		return dataSourceMongoDao.findOne(query);
	}
	/**
	 * 说明 ：修改数据源
	 * 
	 * @author pxz 时间 ： 2017-04-05
	 */
	public OpreateResult update(DatasourceBean datasourceBean) {
		OpreateResult op = new OpreateResult();
		if (!dataSourceMongoDao.checkExistById(datasourceBean.getId())) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			JDBCUtils.close(datasourceBean.getId());
			if (DBconnUtil.testConnection(datasourceBean)) {
				Update update = new Update();
				if (!StringUtil.isBlank(datasourceBean.getName())) {
					update.set("name", datasourceBean.getName());
				}
				if (!StringUtil.isBlank(datasourceBean.getType())) {
					update.set("type", datasourceBean.getType());
				}
				if (!StringUtil.isBlank(datasourceBean.getAliasName())) {
					update.set("aliasName", datasourceBean.getAliasName());
				}
				if (!StringUtil.isBlank(datasourceBean.getServer())) {
					update.set("server", datasourceBean.getServer());
				}
				if (!StringUtil.isBlank(datasourceBean.getDatabase())) {
					update.set("database", datasourceBean.getDatabase());
				}
				if (!StringUtil.isBlank(datasourceBean.getHost())) {
					update.set("host", datasourceBean.getHost());
				}
				if (!StringUtil.isBlank(datasourceBean.getPort())) {
					update.set("port", datasourceBean.getPort());
				}
				if (!StringUtil.isBlank(datasourceBean.getCharset())) {
					update.set("charset", datasourceBean.getCharset());
				}
				if (!StringUtil.isBlank(datasourceBean.getUser())) {
					update.set("user", datasourceBean.getUser());
				}
				if (!StringUtil.isBlank(datasourceBean.getPwd())) {
					update.set("pwd", datasourceBean.getPwd());
				}
				if (!StringUtil.isBlank(datasourceBean.getSid())) {
					update.set("sid", datasourceBean.getSid());
				}
				if (!StringUtil.isBlank(datasourceBean.getDesc())) {
					update.set("desc", datasourceBean.getDesc());
				}
				dataSourceMongoDao.updateFirst(new Query(Criteria.where("id").is(datasourceBean.getId())), update);
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
		return op;
	}

	/**
	 * 说明 ：删除数据源
	 * 
	 * @author pxz 时间 ： 2017-04-05
	 */
	public OpreateResult deleteDataSourceConifgById(String  id) {
		OpreateResult op = new OpreateResult();
		if (!dataSourceMongoDao.checkExistById(id)) {
			op.setStatusCode("300");
			op.setMessage("保存错误，文件不存在");
		} else {
			dataSourceMongoDao.deleteById(id);
			op.setStatusCode("200");
			op.setMessage("删除成功");
		}
		return op;
	}

	/**
	 * 
	   * 功能说明    : 获取所有的数据源集合
	   * @author   : fei yang 
	   * @version ：2017年4月7日 下午4:08:28 
	   * @return
	 */
	public List<DatasourceBean> getAllDatasource() {
		return dataSourceMongoDao.findAll();
	}

	public List<String> getAllDatasourceId() {
		 List<String> datasourceIds=new ArrayList<String>();
		List<DatasourceBean> list=dataSourceMongoDao.findAll();
		if(list!=null && list.size()>0){
			for(DatasourceBean datasourceBean:list){
				datasourceIds.add(datasourceBean.getId());
			}
		}
		return datasourceIds;
	}

}
