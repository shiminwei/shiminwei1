package com.ahzd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ahcd.common.FileUtil;
import com.ahcd.common.FtpUtils;
import com.ahcd.pojo.OperateResult;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepFileToFtp;
import com.ahzd.pojo.DataJobStepFtpToLocal;

@Service("fileToFtpService")
public class DataJobStepFileToFtpService {
	@Autowired
	private DataJobMongoDao dataJobMongoDao;
	//上传
	public OperateResult toStartUpLoadFile(DataJobStepFileToFtp needStep){//传入step对象并获取参数
		OperateResult opreateResult = new OperateResult();
		boolean ft = true;
		try{
			FtpUtils.uploadFile(needStep.getAddress(), needStep.getPort(), needStep.getUsername(),
					needStep.getPassword(), "", FileUtil.getFileName(needStep.getFilePath()), needStep.getFilePath(),
					needStep.getIsDelete());
		//	ft = true;
	  }catch (Exception e){
		  ft = false;
		  opreateResult.setResult(ft);
		  opreateResult.setMsg(e.getMessage());
		  return opreateResult;
	  }
		opreateResult.setResult(ft);
		opreateResult.setMsg("上传成功!");
		return opreateResult;
	}
	
	//下载
	public OperateResult toStartDownLoadFile(DataJobStepFtpToLocal needStep){//传入step对象并获取参数
		OperateResult opreateResult = new OperateResult();
		boolean ft = true;
		try{
			FtpUtils.downFile(needStep.getAddress(), needStep.getPort(),
					needStep.getUsername(), needStep.getPassword(), "", needStep.getFilePath(),needStep.getLocalPath());
			ft = true;
		}catch(Exception e){
			ft = false;
			  opreateResult.setResult(ft);
			  opreateResult.setMsg(e.getMessage());
			  return opreateResult;
	   }
		opreateResult.setResult(ft);
		opreateResult.setMsg("下载成功!");
		return opreateResult;
	}
	/**
	 * 修改FTP 下载修改
	 * @param dbFtp
	 * @param jobId
	 * @return
	 */
	public  int updateJobStepDownLoad(DataJobStepFtpToLocal dbFtp,String jobId){
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(dbFtp.getStepId()));
		Update update = new Update();
		update.set("steps.$.address", dbFtp.getAddress());
		update.set("steps.$.port", dbFtp.getPort());
		update.set("steps.$.username", dbFtp.getUsername());
		update.set("steps.$.password", dbFtp.getPassword());
		update.set("steps.$.filePath", dbFtp.getFilePath());
		update.set("steps.$.localPath", dbFtp.getLocalPath());
		update.set("steps.$.stepId", dbFtp.getStepId());
		update.set("steps.$.sortNum", dbFtp.getSortNum());
		update.set("steps.$.fileNamePatterns", dbFtp.getFileNamePatterns());
		update.set("steps.$.name", dbFtp.getName());
		update.set("steps.$.type", dbFtp.getType());
		DataJob job = dataJobMongoDao.findAndModify(query, update);
		if(job == null){
			return -1 ;
		}
		return 1 ;
	}
	
	/**
	 * 修改FTP 上传修改
	 * @param dbFtp
	 * @param jobId
	 * @return
	 */
	public  int updateJobStep(DataJobStepFileToFtp dbFtp,String jobId){
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(dbFtp.getStepId()));
		Update update = new Update();
		update.set("steps.$.address", dbFtp.getAddress());
		update.set("steps.$.port", dbFtp.getPort());
		update.set("steps.$.username", dbFtp.getUsername());
		update.set("steps.$.password", dbFtp.getPassword());
		update.set("steps.$.isDelete", dbFtp.getIsDelete());
		update.set("steps.$.stepId", dbFtp.getStepId());
		update.set("steps.$.sortNum", dbFtp.getSortNum());
		update.set("steps.$.filePath", dbFtp.getFilePath());
		update.set("steps.$.fileNamePatterns", dbFtp.getFileNamePatterns());
		update.set("steps.$.name", dbFtp.getName());
		update.set("steps.$.type", dbFtp.getType());
		DataJob job = dataJobMongoDao.findAndModify(query, update);
		if(job == null){
			return -1 ;
		}
		return 1 ;
	}
}
