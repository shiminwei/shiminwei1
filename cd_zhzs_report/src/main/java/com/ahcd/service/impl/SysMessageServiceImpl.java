package com.ahcd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysMessageLeadMapper;
import com.ahcd.dao.SysMessageMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysMessage;
import com.ahcd.pojo.SysMessageLead;
import com.ahcd.service.SysMessageService;

@Service("sysMessageService")
public class SysMessageServiceImpl implements SysMessageService {
	@Resource
	private SysMessageMapper sysMessageMapper;
	@Resource
	private SysMessageLeadMapper sysMessageLeadMapper;
	@Override
	public int insertSysMessage(SysMessage sysMessage) {
		return sysMessageMapper.insertSelective(sysMessage);
	}

	@Override
	public SysMessage getSysMessageById(String messageId) {
		return sysMessageMapper.selectByPrimaryKey(messageId);
	}

	@Override
	public int updateSysMessage(SysMessage sysMessage) {
		SysMessage tempSysMessage = sysMessageMapper.selectByPrimaryKey(sysMessage.getMessageId());
		if(tempSysMessage == null){
			return -1;
		}
		tempSysMessage.setMessageTitle(sysMessage.getMessageTitle());
		tempSysMessage.setMessageContent(sysMessage.getMessageContent());
		return sysMessageMapper.updateByPrimaryKeyWithBLOBs(tempSysMessage);
	}

	@Override
	public int deleteByMessageId(String messageId) {
		int flag = 0;
		if(StringUtil.isBlank(messageId)){
			return flag;
		}
		for(String id:messageId.split(",")){
			
			//删除中间表中的messageId为id的记录
			flag = sysMessageLeadMapper.delete(id);
			if (flag < 0) {
				return flag;
			}
			flag = sysMessageMapper.deleteByPrimaryKey(id);
			if (flag == 0) {
				return flag;
			}
		}
		return  flag;
	}

	@Override
	public Page<SysMessage> getSysMessagePage(Page<SysMessage> page) {
		int totalCount=sysMessageMapper.countPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<SysMessage>resultList=sysMessageMapper.selectPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

	@Override
	public int insertSysMessageLead(String leadIds, String messageId) {
		int flag = 1;
		if(StringUtil.isBlank(messageId)|| StringUtil.isBlank(leadIds)){
			return 0;
		}
		SysMessageLead sysMessageLead = getSysMessageLead(messageId) ;
		for(String sys : leadIds.split(",")){
			if(StringUtil.isBlank(sys)) continue;	
			sysMessageLead.setLeadId(sys);
			flag = sysMessageLeadMapper.insert(sysMessageLead);
			if(flag == 0) return flag;
		}
		return flag;
	}
	/**获取中间表Map
	 * */
	@SuppressWarnings("unused")
	private Map<String,SysMessageLead> getSysMessageLeadMap(String messageId){
		List<SysMessageLead> list = sysMessageLeadMapper.getPageByMessageId(messageId);
	    
		if(list != null && list.size()>0){
			Map<String,SysMessageLead> map = new HashMap<String,SysMessageLead>();
			for(SysMessageLead sys:list ){
				map.put(sys.getLeadId(), sys);
			}
			return map;
	    }
	    return null;
	}
	/**获取中间表对象
	 * */
	private SysMessageLead getSysMessageLead(String messageId){
		SysMessageLead sysMessageLead = new SysMessageLead();
		sysMessageLead.setMessageId(messageId);
		sysMessageLead.setStates("0");
		sysMessageLead.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
		return sysMessageLead ;
	}

	@Override
	public Page<SysMessageLead> getSysDepartmentLeadPage(
			Page<SysMessageLead> page) {
		int totalCount=sysMessageLeadMapper.countSysDepartmentLeadPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<SysMessageLead> resultList =sysMessageLeadMapper.selectSysDepartmentLeadPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
}
