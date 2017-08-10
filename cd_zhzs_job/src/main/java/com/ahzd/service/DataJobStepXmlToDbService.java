package com.ahzd.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.pojo.DataJobStepXmlToDb;

@Service("dataJobStepXmlToDbService")
public class DataJobStepXmlToDbService {

	@Autowired
	private DataJobMongoDao dataJobMongoDao;

	@Resource
	private DataJobStepMongoService dataJobStepMongoService;
	
	
	/**
	 * 
	 * 功能说明 :根据JobId和StepId查询特定步骤
	 * 
	 * @author : fei yang
	 * @version ：2017年5月3日 下午3:34:13
	 * @param jobId
	 * @param stepId
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T selectJobStep(String jobId, String stepId, Class<T> clazz) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		DataJob job = dataJobMongoDao.findOne(query);
		if (job == null) {
			return null;
		}
		List<DataJobStepBase> list = (List<DataJobStepBase>) job.getSteps();
		for (DataJobStepBase step : list) {
			if (stepId != null && stepId.equals(step.getStepId())) {
				return (T) step;
			}
		}
		return null;
	}

	/**
	 * 
	 * 功能说明 : 修改步骤（XML导入数据库）
	 * 
	 * @author : fei yang
	 * @version ：2017年5月3日 下午3:23:44
	 * @param bean
	 * @param jobId
	 * @return
	 */
	public int updateJobStep(DataJobStepXmlToDb bean, String jobId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(bean.getStepId()));
		Update update = new Update();
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
		if (job == null) {
			return -1;
		}
		return 1;
	}

	/**
	 * 
	 * 功能说明 : 新增或者修改步骤（数据源导出XML格式）
	 * 
	 * @author : fei yang
	 * @version ：2017年5月4日 上午9:16:53
	 * @param jobId
	 *            任务ID
	 * @param dataJobStepDbToXml
	 *            数据源导出XML实体
	 * @return
	 */
	public OpreateResult addOrEditImportXmlToMongo(String jobId, DataJobStepXmlToDb dataJobStepXmlToDb) {
		OpreateResult opreateResult = new OpreateResult("300", "操作失败", "", "", "");
		int result = 0;
		if (StringUtil.isNotEmpty(dataJobStepXmlToDb.getStepId())) {// 修改
			result = updateJobStep(dataJobStepXmlToDb, jobId);
		} else {// 新增
			dataJobStepXmlToDb.setStepId(new DataJobStepBase().getStepId());
			result = dataJobStepMongoService.addJobStep(dataJobStepXmlToDb, jobId);
		}
		if (result == 1) {
			opreateResult = new OpreateResult("200", "操作成功", "jobsteplist", "closeCurrent", "");
		}
		return opreateResult;
	}

}
