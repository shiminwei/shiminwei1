package com.ahzd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.pojo.Page;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;

@Service("dataJobMongoService")
public class DataJobMongoService {
	@Autowired
	private DataJobMongoDao dataJobMongoDao;
	
	public  void save(DataJob bean){
		dataJobMongoDao.save(bean);
	}
	
	public  void editStepSort(DataJob bean,String stepName,int stepSort){
		Query query = new Query(Criteria.where("name").is(bean.getName()));
		query.addCriteria(Criteria.where("steps.name").is(stepName));
		Update update=new Update();
		update.set("steps.$.sortNum", stepSort);
		dataJobMongoDao.updateFirst(query, update);
	}
	
	/**
	 * @deprecated  开始调度，成功之后修改状态 
	 * @author pxz 
	 * @category 2017-04-19
	 */
	public void editJobInfo(DataJob bean){
		Query query = new Query(Criteria.where("jobId").is(bean.getJobId()));
		Update update=new Update();
		update.set("used", bean.getUsed());
		update.set("status", bean.getStatus());
		dataJobMongoDao.updateFirst(query, update);
	}

	public List<DataJob>  findAll() {
		List<DataJob> list=dataJobMongoDao.findAll();
		return list;
	}
	
	/**
	 * 说明 ：获取調度任务列表
	 * @author pxz 时间 ： 2017-04-12
	 */
	public Page<DataJob> getJobPage(Page<DataJob> page, DataJob queryBean,String order,String typeOrder,String usedOrder,String runOrder) {
		if (queryBean != null) {
			Query query = new Query();
			if (!StringUtil.isBlank(queryBean.getName())) {
				query.addCriteria(Criteria.where("name").regex(".*?" + queryBean.getName().trim() + ".*"));
				page.setQueryBean(query);
			}
			if (queryBean.get_id()!=null) {
				query.addCriteria(Criteria.where("jobId").regex(".*?" + queryBean.getJobId() + ".*"));
				page.setQueryBean(query);
			}
			if (queryBean.getStatus()!=0) {
				query.addCriteria(Criteria.where("status").is(queryBean.getStatus()));
				page.setQueryBean(query);
			}
			if (queryBean.getType()!=0) {
				query.addCriteria(Criteria.where("type").is(queryBean.getType()));
				page.setQueryBean(query);
			}
			if(typeOrder!=null&&typeOrder!=""){
				//query.with(new Sort(new Order(Direction.DESC, "type")));
				
				if(typeOrder.equals("desc")){
					query.with(new Sort(new Order(Direction.DESC, "type")));
				}else{
					query.with(new Sort(new Order(Direction.ASC, "type")));
				}
				
				page.setQueryBean(query);
			}else if(usedOrder!=null&&usedOrder!=""){
				//query.with(new Sort(new Order(Direction.DESC, "used")));
				if(usedOrder.equals("desc")){
					query.with(new Sort(new Order(Direction.DESC, "used")));
				}else{
					query.with(new Sort(new Order(Direction.ASC, "used")));
				}
				
				page.setQueryBean(query);
			}else if(runOrder!=null&&runOrder!=""){
				//query.with(new Sort(new Order(Direction.DESC, "status")));
				if(runOrder.equals("desc")){
					query.with(new Sort(new Order(Direction.DESC, "status")));
				}else{
					query.with(new Sort(new Order(Direction.ASC, "status")));
				}
				
				page.setQueryBean(query);
			}else{
				if(order!=null&&order.equals("desc")){
					query.with(new Sort(new Order(Direction.DESC, "jobId")));
				}else{
					query.with(new Sort(new Order(Direction.ASC, "jobId")));
				}
				page.setQueryBean(query);
			}
		}
		return dataJobMongoDao.getPageList(page);
	}
	//按照used状态进行排
	
	public Page<DataJob> byUsed(Page<DataJob> page,Query query){
		query.with(new Sort(new Order(Direction.DESC, "used")));
		page.setQueryBean(query);
		return page;
	}
	
	
	public DataJob findById(String id){
		return dataJobMongoDao.findById(id);
	}
	
	public DataJob findByName(String name){
		return dataJobMongoDao.findByName(name);
	}
	
	public DataJob findOne(String jobId){
		Query query = new Query(Criteria.where("jobId").is(jobId));
		return dataJobMongoDao.findOne(query);
	}
	//h获得JOB
	public DataJob findOne(String id,String name){
		Query query = new Query(Criteria.where("_id").is(id));
		query.addCriteria(Criteria.where("steps.name").is(name));
		return dataJobMongoDao.findOne(query);
	}
}
