package com.ahzd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.Page;
import com.ahzd.dao.ConfigBeanDao;

@Service("configBeanService")
public class ConfigBeanService {
	@Autowired
	private ConfigBeanDao configBeanDao;

	public ConfigBean save(ConfigBean cb) {
		return (ConfigBean) configBeanDao.save(cb);
	}

	// 获得配置文件并且进行分页显示
	public Page<ConfigBean> getList(Page<ConfigBean> page, ConfigBean queryBean) {
		if (queryBean != null) {
			Query query = new Query();
			if (!StringUtil.isBlank(queryBean.getFunctionId())) {
				query.addCriteria(Criteria.where("functionId").regex(".*?" + queryBean.getFunctionId().trim() + ".*"));
				page.setQueryBean(query);
			}
			if (!StringUtil.isBlank(queryBean.getName())) {
				query.addCriteria(Criteria.where("name").regex(".*?" + queryBean.getName().trim() + ".*"));
				page.setQueryBean(query);
			}
		}
		return configBeanDao.getList(page);
	}

	// 通过ID删除对应的记录
	public ConfigBean deleteRecord(String id) {
		return (ConfigBean) configBeanDao.deleteRecord(id);
	}

	// 通过ID获得相应的配置文件
	public ConfigBean findOne(String functionId) {
		return configBeanDao.findOne(functionId);

	}

	// 获得所有的配置文件
	public List<ConfigBean> findAll() {
		return configBeanDao.findAll();
	}
}
