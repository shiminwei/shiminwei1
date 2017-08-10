package com.ahcd.dao;

import java.util.List;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysMessageLead;

public interface SysMessageLeadMapper {
	
    int insert(SysMessageLead record);
    
    int insertSelective(SysMessageLead record);
    
    /**根据消息ID获取中间表列表
     * */
    List<SysMessageLead> getPageByMessageId(String messageId);
    
    /**根据中间表查询前置机消息条数
     */
	int countSysDepartmentLeadPage(Page<SysMessageLead> page);
	
	/**根据中间表查询前置机消息列表
     */
	List<SysMessageLead> selectSysDepartmentLeadPage(Page<SysMessageLead> page);

}