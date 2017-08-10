package com.ahcd.pojo;

import java.util.Date;

public class SysMessageLead {
    /**消息ID
     */
    private String messageId;

    /**前置机ID
     */
    private String leadId;

    /**状态 备注：0-未发送、1-已发送未读、2-已发送已读
     */
    private String states;
    
    /**发送时间
     */
    private Date sendTime;

    /**根据中间表查询前置机消息列表
     * */
    private String areaName;
    private String departmentName;
    
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }
}