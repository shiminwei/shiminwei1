package com.ahzd.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import com.ahcd.pojo.DatasourceBean;
import com.ahzd.framework.common.mongodb.MongodbBaseDao;
@Repository("DataSourceMongoDao")
public class DataSourceMongoDao extends MongodbBaseDao<DatasourceBean>{

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<DatasourceBean> getEntityClass() {
		return DatasourceBean.class;
	}

	/**
	 * 
	   * 功能说明    :获取所有的数据源集合 
	   * @author   : fei yang 
	   * @version ：2017年4月7日 下午4:08:59 
	   * @return
	 */
	public List<DatasourceBean> findDatasource() {
		return this.mongoTemplate.findAll(getEntityClass());
	}
}
