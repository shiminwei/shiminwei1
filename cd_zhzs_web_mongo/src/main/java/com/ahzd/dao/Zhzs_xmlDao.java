package com.ahzd.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ahzd.framework.common.mongodb.MongodbBaseDao;
import com.ahzd.pojo.Zhzs_query_xml;


@SuppressWarnings("static-access")
@Repository("zhzs_xml")
public class Zhzs_xmlDao extends MongodbBaseDao<Zhzs_query_xml> {

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<Zhzs_query_xml> getEntityClass() {
		return Zhzs_query_xml.class;
	}
	
	public Zhzs_query_xml findOne(Map<String, String> params) {
		Query query = new Query(new Criteria("conditionBeans.conditionId").regex(".*?"+params.get("id")+".*")).limit(9);  
		return super.findOne(query);
	}
	

}
