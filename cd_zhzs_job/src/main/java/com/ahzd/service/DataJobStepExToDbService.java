package com.ahzd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.dao.DataJobStepExToDbDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.pojo.DataJobStepExToDb;
import com.ahzd.pojo.DataJobStepFileToFtp;
import com.ahzd.pojo.DataJobStepFtpToLocal;

@Service("dataJobStepExToDbService")
public class DataJobStepExToDbService {
	@Autowired
	private DataJobStepExToDbDao dataJobStepExToDbDao;

	@Autowired
	private DataJobMongoDao dataJobMongoDao;
	
	/**
	 * 功能说明 :根据JobId和StepId查询特定步骤
	 * */
	@SuppressWarnings("unchecked")
	public <T> T selectJobStep(String jobId,String stepId,Class<T> clazz) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		DataJob job = dataJobMongoDao.findOne(query);
		if(job == null){
			return null ;
		}
		List<DataJobStepBase> list = (List<DataJobStepBase>) job.getSteps();
		for(DataJobStepBase step : list){
			if(stepId != null && stepId.equals(step.getStepId())){
				return (T)step;
			}
		}
		return null;
	}
	/**
	 * 功能说明 :Excel===>DB保存到mongodb文件修改 
	 * */
	public  int updateJobStep(DataJobStepExToDb bean,String jobId){
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(bean.getStepId()));
		Update update = new Update();
		update.set("steps.$.sheetIndex", bean.getSheetIndex());
		update.set("steps.$.beginRowIndex", bean.getBeginRowIndex());
		update.set("steps.$.endRowIndex", bean.getEndRowIndex());
		update.set("steps.$.filePath", bean.getFilePath());
		update.set("steps.$.tableName", bean.getTableName());
		update.set("steps.$.fileNamePatterns", bean.getFileNamePatterns());
		update.set("steps.$.name", bean.getName());
		update.set("steps.$.datasourceId", bean.getDatasourceId());
		update.set("steps.$.paramMap", bean.getParamMap());
		update.set("steps.$.type", bean.getType());
		DataJob job = dataJobMongoDao.findAndModify(query, update);
		if(job == null){
			return -1 ;
		}
		return 1 ;
	}
	public DataJobStepExToDb findOne(String id,String name){
		Query query = new Query(Criteria.where("jobId").is(id));
		query.addCriteria(Criteria.where("steps.name").is(name));
		return dataJobStepExToDbDao.findOne(query);
	}

	/**
	 * 功能说明 :查询原始文件名
	 * */
	public <T> String getOldName(String newName,String jobId,String stepId,Class<T> clazz) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		DataJobStepFileToFtp needStep = selectJobStep(jobId,stepId,DataJobStepFileToFtp.class);
		int num = needStep.getFileNamePatterns().length()-2;
		if(num == -2){
			return (String) newName;
		}
		String old[] = newName.split("\\.");
		String end = old[old.length-1];
		int sort = old[0].length();
		String oldName = old[0].substring(0, sort-num)+"."+end;
		return (String) oldName;
	}

	/**
	 * 功能说明 :查询原始文件名
	 * */
	public <T> String getOldNameTow(String newName,String jobId,String stepId,Class<T> clazz) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(jobId));
		DataJobStepFtpToLocal needStep = selectJobStep(jobId,stepId,DataJobStepFtpToLocal.class);
		int num = needStep.getFileNamePatterns().length()-2;
		if(num == -2){
			return (String) newName;
		}
		String old[] = newName.split("\\.");
		String end = old[old.length-1];
		int sort = old[0].length();
		String oldName = old[0].substring(0, sort-num)+"."+end;
		return (String) oldName;
	}
}
