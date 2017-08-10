package com.ahcd.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class SysMessage {
    /**ID
     */
    private String messageId;

    /**消息标题
     */
    private String messageTitle;

    /**创建时间
     */
    private Date createTime;
    
    /**创建时间字符串
     */
    private String createTimeStr;
    /**创建人
     */
    private BigDecimal userId;
    /**创建人姓名
     */
    private String userName;

    /**发送内容
     */
    private String messageContent;
    
    /**返回前置机ID
     * */
    private String leadId;

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getUserId() {
        return userId;
    }

    public void setUserId(BigDecimal userId) {
        this.userId = userId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}