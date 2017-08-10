package com.ahzd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahzd.dao.DatasourceBeanDao;

@Service("dataSourceBeanService")
public class DataSourceBeanService {
	@Autowired
	private DatasourceBeanDao datasourceBeanDao;

	public List<DatasourceBean> getAllDatasource() {

		return datasourceBeanDao.getAllDatasource();
	}

	public DatasourceBean save(DatasourceBean cb) {
		return (DatasourceBean) datasourceBeanDao.save(cb);
	}

	// 通过ID获取数据源的对象
	public DatasourceBean getDataSourceById(String id) {

		return datasourceBeanDao.getDataSourceById(id);
	}

	// 获得配置文件并且进行分页显示
	public Page<DatasourceBean> getList(Page<DatasourceBean> page, DatasourceBean queryBean) {
		if (queryBean != null) {
			Query query = new Query();
//			if (!StringUtil.isBlank(queryBean.getFunctionId())) {
//				query.addCriteria(Criteria.where("functionId").regex(
//						".*?" + queryBean.getFunctionId().trim() + ".*"));
//				page.setQueryBean(query);
//			}
//			if (!StringUtil.isBlank(queryBean.getName())) {
//				query.addCriteria(Criteria.where("name").regex(
//						".*?" + queryBean.getName().trim() + ".*"));
//				page.setQueryBean(query);
//			}
		}
		return datasourceBeanDao.getList(page);
	}
}
