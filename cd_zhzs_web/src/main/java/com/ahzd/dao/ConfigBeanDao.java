package com.ahzd.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ResultBean;
import com.ahzd.framework.common.mongodb.MongodbBaseDao;

@SuppressWarnings("static-access")
@Repository("ConfigBeanDao")
public class ConfigBeanDao extends MongodbBaseDao<ConfigBean> {
	
	// 获得配置文件并且进行分页显示
	public Page<ConfigBean> getList(Page<ConfigBean> page) {
		return super.getPageList(page);
	}

	// 根据ID删除对应的数据
	public ConfigBean deleteRecord(String id) {
		Query query = new Query(Criteria.where("functionId").is(id));
		return super.findAndRemove(query);
	}

	// 通过functionId获得相应的配置文件
	public ConfigBean findOne(String id) {
		Query query = new Query(Criteria.where("functionId").is(id));
		return super.findOne(query);

	}

	// 获得所有的配置文件
	public List<ConfigBean> findAll() {

		return super.findAll();
	}

	// 新增文件并保存
	public ConfigBean saveOrUpdate(ConfigBean bean) {

		return super.save(bean);
	}

	// 修改文件

	public void update(ConfigBean bean) {
		Update update = new Update();
		if (bean != null) {
			update.set("name", bean.getName());
			update.set("datasource", bean.getDatasource());
			update.set("resultType", bean.getResultType());
			update.set("unitName", bean.getUnitName());
			update.set("maxRowNum", bean.getMaxRowNum());
		}
		if (bean.getResults() != null) {
			update.set("results", bean.getResults());
		}
		if (bean.getParameters() != null) {
			update.set("parameters", bean.getParameters());
		}
		if (bean.getConditionBeans() != null
				&& bean.getConditionBeans().size() > 0) {
			update.set("conditionBeans", bean.getConditionBeans());
		}
		super.updateFirst(new Query(Criteria.where("functionId").is(bean.getFunctionId())),update);
	}

	@Autowired
	@Qualifier("mongoTemplate")
	@Override
	protected void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class<ConfigBean> getEntityClass() {
		return ConfigBean.class;
	}

}
