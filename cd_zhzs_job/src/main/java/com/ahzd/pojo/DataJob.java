package com.ahzd.pojo;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "zhzs_job_config")
public class DataJob {
	private String _id;
	// 任务id
	private String jobId;
	// 任务名称
	private String name;
	// 运行周期
	private String runPeriod;
	// 下次执行时间
	private String nextRunTime;
	// 调度类型 1：立即调度 2：周期调度 3：指定时间
	private int type;
	
	// 运行状态 1:正在运行 2:未运行 3:暂停运行
	private int status;
	// 使用状态 0 未使用 1 已使用
	private int used;
	// 步骤列表
	private List<? extends DataJobStepBase> steps;
	// 调度描述
	private String desc;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
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

	public List<? extends DataJobStepBase> getSteps() {
		return steps;
	}

	public void setSteps(List<? extends DataJobStepBase> steps) {
		this.steps = steps;
	}

	public String getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(String nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
