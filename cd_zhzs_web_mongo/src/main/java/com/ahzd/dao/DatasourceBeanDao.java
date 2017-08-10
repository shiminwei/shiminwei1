package com.ahzd.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahzd.framework.common.mongodb.MongodbBaseDao;

@SuppressWarnings("static-access")
@Repository("datasourceBeanDao")
public class DatasourceBeanDao extends MongodbBaseDao<DatasourceBean> {
	// 获取所有的数据源
	public List<DatasourceBean> getAllDatasource() {
		return super.findAll();
	}

	// 通过ID获取数据源的对象

	public DatasourceBean getDataSourceById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return super.findOne(query);
	}

	// 获得配置文件并且进行分页显示
	public Page<DatasourceBean> getList(Page<DatasourceBean> page) {
		return super.getPageList(page);
	}

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
