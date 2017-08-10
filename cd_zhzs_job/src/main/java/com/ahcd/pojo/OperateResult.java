package com.ahcd.pojo;

public class OperateResult {
	private boolean result;
	private String msg;
	private String sql;
	
	public OperateResult(){
		
	}
	public OperateResult(boolean result, String msg, String sql) {
		super();
		this.result = result;
		this.msg = msg;
		this.sql = sql;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
}
