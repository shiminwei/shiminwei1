package com.ahzd.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ahcd.pojo.ExcelTemplate;
import com.ahzd.framework.common.mongodb.MongodbBaseDao;

@Repository("ExcelTemplateMongoDao")
public class ExcelTemplateMongoDao extends MongodbBaseDao<ExcelTemplate>{

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<ExcelTemplate> getEntityClass() {
		return ExcelTemplate.class;
	}

	public boolean checkExistByTableName(String tableName) {
		return super.findOne(new Query(Criteria.where("tableName").is(tableName)))!=null ? true:false;
	}

}
