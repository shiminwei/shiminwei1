package com.ahzd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahzd.dao.DataJobLoggerMongoDao;
import com.ahzd.pojo.DataJobLogger;
@Service("dataJobLoggerMongoService")
public class DataJobLoggerMongoService {
	@Autowired
	private DataJobLoggerMongoDao dataJobLoggerMongoDao;
	
	public  DataJobLogger save(DataJobLogger bean){
		return dataJobLoggerMongoDao.save(bean);
	}
	
	/**
	 *  根据id查询实体
	 */
	public DataJobLogger findById(String _id){
		return dataJobLoggerMongoDao.findById(_id);
	}
	
	/**
	 *  查询所有的日志 
	 */
	public List<DataJobLogger> find(){
		Query query = new Query();
		query.with(new Sort(new Order(Direction.ASC, "jobId")));
		return dataJobLoggerMongoDao.find(query);
	}

	/**
	 * 说明 ：获取調度任务执行日志列表
	 * @author pxz 
	 * 时间 ： 2017-05-09
	 */
	public Page<DataJobLogger> getJobLoggerPage(Page<DataJobLogger> page, DataJobLogger queryBean) {
		Query query = new Query();
		if (queryBean != null) {
			if (!StringUtil.isBlank(queryBean.getJobId())) {
				query.addCriteria(Criteria.where("jobId").regex(".*?" + queryBean.getJobId().trim() + ".*"));
			}
			if (!StringUtil.isBlank(queryBean.getName())) {
				query.addCriteria(Criteria.where("name").regex(".*?" + queryBean.getName().trim() + ".*"));
			}
		}
		query.with(new Sort(new Order(Direction.ASC, "jobId")));
		page.setQueryBean(query);
		return dataJobLoggerMongoDao.getPageList(page);
	}
	
	
	/**
	 * 说明 ：获取調度任务执行日志列表
	 * @author pxz 
	 * 时间 ： 2017-05-09
	 */
	public Page<DataJobLogger> getJobLoggerPageById(Page<DataJobLogger> page, String jobId) {
		//if (queryBean != null) {
			Query query = new Query();
			if (!StringUtil.isBlank(jobId)) {
				query.addCriteria(Criteria.where("jobId").regex(".*?" + jobId.trim() + ".*"));
				page.setQueryBean(query);
			}
		//}
		return dataJobLoggerMongoDao.getPageList(page);
	}
	/**
	 *功能说明:获得所有的日志信息 
	 * 
	 */
	public List<DataJobLogger> getAllJobLogger(){
		
		return dataJobLoggerMongoDao.findAll();
		
	}
	
}
