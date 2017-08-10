package com.ahcd.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SysNotice {
    private String noticeId;

    private String noticeTitle;

    private Date createTime;

    private BigDecimal userId;

    private String noticeSynopsis;

    private String noticeContent;
    
    private String filePath;
    
    //已读表主键id
    private String readedId;
    //是否已读，0否 1是
    private int isReaded;
    
    List<SysNoticeFile> sysNoticeFileList;
   
    List<SysNoticeDepartment> sysNoticeDepartmentList;
    
    
    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId == null ? null : noticeId.trim();
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle == null ? null : noticeTitle.trim();
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

    public String getNoticeSynopsis() {
        return noticeSynopsis;
    }

    public void setNoticeSynopsis(String noticeSynopsis) {
        this.noticeSynopsis = noticeSynopsis == null ? null : noticeSynopsis.trim();
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent == null ? null : noticeContent.trim();
    }

	public String getReadedId() {
		return readedId;
	}

	public void setReadedId(String readedId) {
		this.readedId = readedId;
	}

	public int getIsReaded() {
		return isReaded;
	}

	public void setIsReaded(int isReaded) {
		this.isReaded = isReaded;
	}

	public List<SysNoticeFile> getSysNoticeFileList() {
		return sysNoticeFileList;
	}

	public void setSysNoticeFileList(List<SysNoticeFile> sysNoticeFileList) {
		this.sysNoticeFileList = sysNoticeFileList;
	}

	public List<SysNoticeDepartment> getSysNoticeDepartmentList() {
		return sysNoticeDepartmentList;
	}

	public void setSysNoticeDepartmentList(List<SysNoticeDepartment> sysNoticeDepartmentList) {
		this.sysNoticeDepartmentList = sysNoticeDepartmentList;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	private SysNoticeFile sysNoticeFile;


	public SysNoticeFile getSysNoticeFile() {
		return sysNoticeFile;
	}

	public void setSysNoticeFile(SysNoticeFile sysNoticeFile) {
		this.sysNoticeFile = sysNoticeFile;
	}
	
}