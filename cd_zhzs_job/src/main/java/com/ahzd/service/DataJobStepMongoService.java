package com.ahzd.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.ahcd.pojo.Page;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepBase;

@Service("dataJobStepMongoService")
public class DataJobStepMongoService {
	@Autowired
	private DataJobMongoDao dataJobMongoDao;
	/**
	 * 功能说明 :步骤--查询列表
	 * */
	@SuppressWarnings("unchecked")
	public Page<DataJobStepBase> selectJobSteps(Page<DataJobStepBase> page,String jobId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		DataJob job = dataJobMongoDao.findOne(query);
		List<DataJobStepBase> dlist = (List<DataJobStepBase>) job.getSteps();
		if(dlist != null && dlist.size()>= 0){
			Collections.sort(dlist, new Comparator<DataJobStepBase>(){
				@Override
				public int compare(DataJobStepBase o1, DataJobStepBase o2) {	
					return new Double(o1.getSortNum()).compareTo(new Double(o2.getSortNum()));
				}});
			int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
			int endRow = page.getPageNum() * page.getNumPerPage();
			if(page.getNumPerPage() < dlist.size()){
				List<DataJobStepBase> tlist = new ArrayList<DataJobStepBase>();
				for(int i =0;i<dlist.size();i++){
					if(i>=beginRow && i<endRow){
						tlist.add(dlist.get(i));
					}
				}
				page.setResult(tlist);
			}else{
				page.setResult(dlist);
			}
			page.setTotalCount(dlist.size());
		}	
		return page;
	}

	/**
	 * 功能说明 :步骤--新增
	 * */
	@SuppressWarnings("unchecked")
	public  int addJobStep(DataJobStepBase bean,String _id){
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(_id));
		DataJob job = dataJobMongoDao.findOne(query);
		if(job == null){
			return -1;
		}
		List<DataJobStepBase> dlist = (List<DataJobStepBase>) job.getSteps();
		if(dlist == null || dlist.size() < 1){
			bean.setSortNum(1);
		}else{
			Collections.sort(dlist, new Comparator<DataJobStepBase>(){
				@Override
				public int compare(DataJobStepBase o1, DataJobStepBase o2) {	
					return new Double(o1.getSortNum()).compareTo(new Double(o2.getSortNum()));
				}});
			bean.setSortNum(dlist.get(dlist.size()-1).getSortNum()+1);
		}	
		query = Query.query(Criteria.where("jobId").is(_id));
        Update update = new Update();
        update.addToSet("steps", bean);
        job = dataJobMongoDao.findAndModify(query, update);	
		if(job == null){
			return -1 ;
		}
		return 1 ;
	}
	
	/**
	 * 功能说明 :步骤--删除
	 * */
	public  int deleteJobStep(String jobId,String _id,int sortnum){
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		DataJob job = dataJobMongoDao.findOne(query);
		if(job == null){
			return -1;
		}
		@SuppressWarnings("unchecked")
		List<DataJobStepBase> dlist = (List<DataJobStepBase>) job.getSteps();
		List<DataJobStepBase> tList = new ArrayList<DataJobStepBase>();
		for(DataJobStepBase temp : dlist){
			if(_id != null && !_id.equals(temp.getStepId())){
				tList.add(temp);
			}
		}
		Update update = new Update();
		update.set("steps", tList);
		job = dataJobMongoDao.findAndModify(query, update);
		if(job == null){
			return -1;
		}
		return 1;
	}
	
	
	/**
	 * 功能说明 :步骤--根据JobId和StepId更新步骤的sortNum字段
	 * */
	public  int updateStepSort(String jobId,String stepid,int stepSort){
		Query query = new Query(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(stepid));
		Update update=new Update();
		update.set("steps.$.sortNum", stepSort);
		DataJob job = dataJobMongoDao.findAndModify(query, update);
		if(job == null)return -1;
		return 1;
	}
}
