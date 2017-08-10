package com.ahcd.service;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentLead;
import com.ahcd.pojo.SysMessage;
import com.ahcd.pojo.SysMessageLead;

public interface SysMessageService {
		/**新增
		 * */
		int insertSysMessage(SysMessage sysMessage);

		/**查询实体
		 * */
		SysMessage getSysMessageById(String messageId);

		/**更新
		 * */
	    int updateSysMessage(SysMessage sysMessage);
	    
	    /**删除
		 * */
	    int deleteByMessageId(String messageId);
	    
	    /**查询列表
		 * */
	    Page<SysMessage> getSysMessagePage(Page<SysMessage> page);
	    
	    /**新增消息中间表
		 * */
	    int insertSysMessageLead(String leadIds ,String messageId);
	    /**查询前置机消息列表
		 * */
	    Page<SysMessageLead> getSysDepartmentLeadPage(Page<SysMessageLead> page);
}
