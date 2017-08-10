package com.ahzd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import com.ahcd.pojo.DatasourceBean;
import com.ahzd.framework.common.mongodb.MongodbBaseDao;
@Repository("ExcelDataSourceMongoDao")
public class ExcelDataSourceMongoDao extends MongodbBaseDao<DatasourceBean>{

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

}
