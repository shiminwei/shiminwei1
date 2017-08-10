package com.ahzd.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import com.ahzd.framework.common.mongodb.MongodbBaseDao;
import com.ahzd.pojo.DataJob;
@Repository("DataJobMongoDao")
public class DataJobMongoDao  extends MongodbBaseDao<DataJob>{

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<DataJob> getEntityClass() {
		return DataJob.class;
	}
}
