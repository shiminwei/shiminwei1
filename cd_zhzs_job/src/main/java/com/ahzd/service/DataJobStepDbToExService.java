package com.ahzd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepDbToEx;

@Service("dataJobStepDbToExService")
public class DataJobStepDbToExService {
	@Autowired
	private DataJobMongoDao dataJobMongoDao;
	
	/**
	 * 功能说明 :DB===>Excel保存到mongodb文件修改 
	 * */
	public  int updateJobStep(DataJobStepDbToEx bean,String jobId){
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(bean.getStepId()));
		Update update = new Update();
		//update.set("steps", bean);
		update.set("steps.$.name", bean.getName());
		update.set("steps.$.filePath", bean.getFilePath());
		update.set("steps.$.fileNamePatterns", bean.getFileNamePatterns());
		update.set("steps.$.datasourceId", bean.getDatasourceId());
		update.set("steps.$.sql", bean.getSql());
		update.set("steps.$.paramMap", bean.getParamMap());
		update.set("steps.$.type", bean.getType());
		DataJob job = dataJobMongoDao.findAndModify(query, update);
		if(job == null){
			return -1 ;
		}
		return 1 ;
	}
	
	
}
