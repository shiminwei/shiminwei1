package com.ahzd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.ahzd.framework.common.mongodb.MongodbBaseDao;
import com.ahzd.pojo.DataJobStepExToDb;
@Repository("DataJobStepExToDbDao")
public class DataJobStepExToDbDao  extends MongodbBaseDao<DataJobStepExToDb>{
	@Override
	protected Class<DataJobStepExToDb> getEntityClass() {
		// TODO Auto-generated method stub
		return DataJobStepExToDb.class;
	}
	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
		
	}
}
