package com.ahzd.pojo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.ahcd.pojo.OperateResult;

@Document(collection = "zhzs_job_log")
public class DataJobLogger {
	// 任务id
	private String _id;
	// 
	private String jobId;
	// 任务名称
	private String name;
	// 运行周期
	private String runPeriod;
	// 调度类型 1：立即调度 2：周期调度 3：指定时间
	private int type;
	// 步骤列表
	private List<? extends DataJobStepBase> steps;
	// 操作结果
	private List<OperateResult> operateResult; 
	// 状态     0 异常   1正常
	private int result;
	// 执行时间
	private String executioTime;
	// 日志内容
	private String content;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRunPeriod() {
		return runPeriod;
	}
	public void setRunPeriod(String runPeriod) {
		this.runPeriod = runPeriod;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<OperateResult> getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(List<OperateResult> operateResult) {
		this.operateResult = operateResult;
	}
	public List<? extends DataJobStepBase> getSteps() {
		return steps;
	}
	public void setSteps(List<? extends DataJobStepBase> steps) {
		this.steps = steps;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getExecutioTime() {
		return executioTime;
	}
	public void setExecutioTime(String executioTime) {
		this.executioTime = executioTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
		
	
}
