package com.ahzd.service;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.ahcd.common.DbUntil;
import com.ahcd.common.FileUtil;
import com.ahcd.common.OpreateResult;
import com.ahcd.common.StringUtil;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.OperateResult;
import com.ahcd.pojo.TableBean;
import com.ahcd.xml.XMLDocument;
import com.ahcd.xml.XMLNode;
import com.ahzd.dao.DataJobMongoDao;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.pojo.DataJobStepDbToXml;

@Service("dataJobStepDbToXmlService")
public class DataJobStepDbToXmlService {
	@Autowired
	private DataJobMongoDao dataJobMongoDao;

	@Resource
	private DataJobStepMongoService dataJobStepMongoService;

	private static DataSourceMongoService dataSourceMongoService;

	@Autowired
	public void setDataSourceBeanService(DataSourceMongoService dataSourceMongoService) {
		DataJobStepDbToXmlService.dataSourceMongoService = dataSourceMongoService;
	}

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
	 * 功能说明 : 修改步骤（数据源导出为XML）
	 * 
	 * @author : fei yang
	 * @version ：2017年5月3日 下午3:23:44
	 * @param bean
	 * @param jobId
	 * @return
	 */
	public int updateJobStep(DataJobStepDbToXml bean, String jobId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("jobId").is(jobId));
		query.addCriteria(Criteria.where("steps.stepId").is(bean.getStepId()));
		Update update = new Update();
		update.set("steps.$.beginRowIndex", bean.getBeginRowIndex());
		update.set("steps.$.endRowIndex", bean.getEndRowIndex());
		update.set("steps.$.filePath", bean.getFilePath());
		update.set("steps.$.sql", bean.getSql());
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
	public OpreateResult addOrEditExportXmlToMongo(String jobId, DataJobStepDbToXml dataJobStepDbToXml) {
		OpreateResult opreateResult = new OpreateResult("300", "操作失败", "", "", "");
		int result = 0;
		if (StringUtil.isNotEmpty(dataJobStepDbToXml.getStepId())) {// 修改
			result = updateJobStep(dataJobStepDbToXml, jobId);
		} else {// 新增
			dataJobStepDbToXml.setStepId(new DataJobStepBase().getStepId());
			result = dataJobStepMongoService.addJobStep(dataJobStepDbToXml, jobId);
		}
		if (result == 1) {
			opreateResult = new OpreateResult("200", "操作成功", "jobsteplist", "closeCurrent", "");
		}
		return opreateResult;
	}

	/**
	 * 
	 * 功能说明 : 根据数据导出XML格式实体 返回导出处理结果
	 * 
	 * @author : fei yang
	 * @version ：2017年5月4日 下午4:26:30
	 * @param dbToXmlBean
	 * @return
	 */
	public OperateResult dbToXml(DataJobStepDbToXml dbToXmlBean) {
		OperateResult opreateResult = new OperateResult(false, "导出XML格式数据异常", dbToXmlBean.getSql());
		try {
			if (StringUtil.isBlank(dbToXmlBean.getFilePath())) {
				return new OperateResult(false, "保存路径不存在", dbToXmlBean.getSql());
			} else if (!FileUtil.vidateFileType(dbToXmlBean.getFilePath(), "xml")) {
				return new OperateResult(false, "文件保存格式错误", dbToXmlBean.getSql());
			}
			if (StringUtil.isBlank(dbToXmlBean.getSql())) {
				return new OperateResult(false, "导出SQL语句不存在", "");
			}
			if (StringUtil.isBlank(dbToXmlBean.getDatasourceId())) {
				return new OperateResult(false, "数据源不存在", dbToXmlBean.getSql());
			}
			DatasourceBean db = dataSourceMongoService.findById(dbToXmlBean.getDatasourceId());
			if (db != null) {
				TableBean tableBean = DbUntil.toQuerysSql(dbToXmlBean.getSql(), db);
				if (tableBean != null && tableBean.getRows() != null && tableBean.getRows().size() > 0) {// 查询有数据结果
					XMLDocument doc = new XMLDocument();
					doc.create("ROOTS");
					XMLNode rootsNode = doc.getRoot();
					rootsNode.setAttribute("dataType", db.getType());
					int beginIndex = 0;
					int endIndex = tableBean.getRows().size();
					if (!StringUtil.isBlank(dbToXmlBean.getBeginRowIndex())&&dbToXmlBean.getBeginRowIndex()>1) {
						beginIndex = dbToXmlBean.getBeginRowIndex()-1;
					}
					if (!StringUtil.isBlank(dbToXmlBean.getEndRowIndex())) {
						endIndex = dbToXmlBean.getEndRowIndex();
					}
					if (beginIndex>tableBean.getRows().size()||endIndex>tableBean.getRows().size()) {			
							return new OperateResult(true,
									"导出数据开始行以及结束行存在问题,数据总条数为:" + tableBean.getRows().size() + "开始行："
											+ dbToXmlBean.getBeginRowIndex() + "结束行：" + dbToXmlBean.getEndRowIndex(),
									dbToXmlBean.getSql());				
					}
						writeXml(rootsNode, tableBean, beginIndex, endIndex);
						doc.saveToFile(dbToXmlBean.getFilePath());
						return new OperateResult(true, "导出XML格式数据成功,文件保存路径为：" + dbToXmlBean.getFilePath()+"数据条数："+(endIndex-beginIndex),
								dbToXmlBean.getSql());
				} else {
					return new OperateResult(false, "导出数据异常", dbToXmlBean.getSql());
				}
			}
		} catch (Exception e) {
			return new OperateResult(false, e.getMessage(), dbToXmlBean.getSql());
		}

		return opreateResult;
	}

	/**
	 * 
	   * 功能说明    : 写XML
	   * @author   : fei yang 
	   * @version ：2017年5月5日 上午9:09:45 
	   * @param rootsNode 根元素
	   * @param tableBean 查询表数据
	   * @param beginIndex 开始行
	   * @param endIndex 结束行
	 */
	public static void writeXml(XMLNode rootsNode,TableBean tableBean,int beginIndex,int endIndex){
		for (int i = beginIndex; i < endIndex; i++) {
			XMLNode rootNode = rootsNode.createChildNode("ROOT");
			for (int j = 0; j < tableBean.getColumns().size(); j++) {
				String colName = tableBean.getColumns().get(j).getName();
				XMLNode node = rootNode.createChildNode(colName);
				node.setAttribute("colType", tableBean.getColumns().get(j).getType());
				String value = tableBean.getRows().get(i).getValue().get(colName);
				if (StringUtil.isBlank(value)) {
					value = "";
				}
				node.setText(value);
			}
		}
	}
}
