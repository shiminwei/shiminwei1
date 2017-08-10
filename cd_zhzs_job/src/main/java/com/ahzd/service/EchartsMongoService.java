package com.ahzd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ahcd.pojo.Page;
import com.ahzd.dao.DataJobLoggerMongoDao;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobLogger;

@Service("echartsMongoService")
public class EchartsMongoService {
	@Autowired
	private DataJobMongoDao dataJobMongoDao;
	@Autowired
	private DataJobLoggerMongoDao dataJobLoggerMongoDao;
	
	/** 分组并得到对应的count
	 * */
	public GroupByResults<DataJob> fingUsedGroupCount(Criteria criteria,String collectionName,GroupBy groupBy){
		return dataJobMongoDao.groupBy(criteria, collectionName, groupBy);
	}
	
	/** 根据日期获得当前日期的日志列表
	 * */
	public Page<DataJobLogger> loggerList(Page<DataJobLogger> page,String date){
		Query query = new Query();
		if(!"".equals(date) && date != null){
			query.addCriteria(Criteria.where("executioTime").regex(".*?" + date.trim() + ".*"));
		}
		query.with(new Sort(new Order(Direction.DESC, "executioTime"))); 
		page.setQueryBean(query);
		return dataJobLoggerMongoDao.getPageList(page);
	}
	
	/** 根据日期和执行情况获得count
	 * */
	public long logcount(String executioTime , int result){
		Query query = new Query();
		if(!"".equals(executioTime)&&executioTime!=null){
			query.addCriteria(Criteria.where("result").is(result)
					.and("executioTime").regex(".*?" + executioTime.trim() + ".*"));
		}else{
			query.addCriteria(Criteria.where("result").is(result));
		}
		return dataJobLoggerMongoDao.getCount(query);
	}
}
