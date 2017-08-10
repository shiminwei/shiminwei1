package com.ahzd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import com.ahzd.framework.common.mongodb.MongodbBaseDao;
import com.ahzd.pojo.DataJobLogger;
@Repository("dataJobLoggerMongoDao")
public class DataJobLoggerMongoDao  extends MongodbBaseDao<DataJobLogger>{

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<DataJobLogger> getEntityClass() {
		return DataJobLogger.class;
	}

}
