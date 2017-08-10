package com.ahcd.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.ahcd.common.BigDecimalUtil;
import com.ahcd.common.DateUtil;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.CompanyInfoLogMapper;
import com.ahcd.dao.CompanyInfoMapper;
import com.ahcd.pojo.CompanyInfo;
import com.ahcd.pojo.CompanyInfoLog;
import com.ahcd.pojo.Page;
import com.ahcd.service.CompanyInfoService;

@Service("companyInfoService")
public class CompanyInfoServiceImpl implements CompanyInfoService {
	
	@Resource
	private CompanyInfoMapper companyInfoMapper;
	@Resource
	private CompanyInfoLogMapper companyInfoLogMapper;
	@Override
	public int insertCompanyInfo(CompanyInfo companyInfo) {
		return companyInfoMapper.insertSelective(companyInfo);
	}

	@Override
	public CompanyInfo getCompanyInfoById(BigDecimal id) {
		return companyInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateCompanyInfo(CompanyInfo companyInfo) {
		//变更历史表
		CompanyInfo db = companyInfoMapper.selectByPrimaryKey(companyInfo.getId());
		if(db == null){
			return -1;
		}
		//将DB中CompanyInfo的数据更新到CompanyInfoLog中
		try {
			CompanyInfoLog log = new CompanyInfoLog();
			Map<String,String> fieldMap = new HashMap<String, String>();
			fieldMap.put("updateTime","updateTime");
			fieldMap.put("remark","remark");
			getCompanyInfoLog(db,log,companyInfo,fieldMap);
			int ret = companyInfoLogMapper.insert(log);
			if(ret == 0){
				return ret ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		return companyInfoMapper.updateByPrimaryKeySelective(companyInfo);
	}

	@Override
	public int deleteById(String id) {
		int flag = 0;
		if(StringUtil.isBlank(id)){
			return flag;
		}
		for(String temp:id.split(",")){
			//变更历史表
			CompanyInfo db = companyInfoMapper.selectByPrimaryKey(new BigDecimal(temp));
			if(db == null)return -1;
			try {
				CompanyInfoLog log = new CompanyInfoLog();
				Map<String,String> fieldMap = new HashMap<String, String>();
				fieldMap.put("updateTime","updateTime");
				fieldMap.put("remark","remark");
				getCompanyInfoLog(db,log,null,fieldMap);
				log.setRemark("删除标志[0-未删除 1-已删除]变更为1。");
				int ret = companyInfoLogMapper.insert(log);
				if(ret == 0)return ret;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			} 
			flag = companyInfoMapper.deleteByPrimaryKey(new BigDecimal(temp));
			if (flag == 0) return flag;
		}
		return  flag;
	}

	@Override
	public Page<CompanyInfo> getCompanyInfoPage(Page<CompanyInfo> page) {
		int totalCount=companyInfoMapper.countPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<CompanyInfo>resultList=companyInfoMapper.selectPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

	@Override
	public int reverse(BigDecimal id,String type) {
		CompanyInfo db = companyInfoMapper.selectByPrimaryKey(id);
		if(db == null)return -1;
		String reverse = "1";
		StringBuffer sb = new StringBuffer();
		//规模以上企业
		if("1".equals(type)){
			sb.append("是否规模以上企业[1-是 0-否]变更为");
			if("1".equals(db.getIsAboveScale())){
				reverse = "0";
			}			
		//招商引资企业
		}else{
			sb.append("是否招商企业[1-是 0-否]变更为");
			if("1".equals(db.getIsCanvassBuisiness())){
				reverse = "0";
			}	
		}
		sb.append(reverse).append("。");
		try {
			CompanyInfoLog log = new CompanyInfoLog();
			Map<String,String> fieldMap = new HashMap<String, String>();
			fieldMap.put("updateTime","updateTime");
			fieldMap.put("remark","remark");
			getCompanyInfoLog(db,log,null,fieldMap);
			log.setRemark(sb.toString());
			int ret = companyInfoLogMapper.insert(log);
			if(ret == 0)return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
		if("1".equals(type)){
			db.setIsAboveScale(reverse);
		}else{
			db.setIsCanvassBuisiness(reverse);
		}
		return companyInfoMapper.updateByPrimaryKey(db);
	}

	@Override
	public int saveCompanyInfoExcel(List<CompanyInfo> list) {
		//返回数据库中企业名称已存在或者解析有误的记录
		List<CompanyInfo> templist = new ArrayList<CompanyInfo>();
		int flag = 1;
		for(CompanyInfo companyInfo : list){
			if(!StringUtil.isBlank(companyInfo.getCheckResult())){
				templist.add(companyInfo);
				continue ;
			}			
			if(getCount(companyInfo.getName())>0){
				companyInfo.setCheckResult("系统中该企业名称已经存在。");
				templist.add(companyInfo);
				continue ;
			}		
			companyInfo.setIsAboveScale("是".equals(companyInfo.getIsAboveScale())?"1":"0");
			companyInfo.setIsCanvassBuisiness("是".equals(companyInfo.getIsCanvassBuisiness())?"1":"0");
			companyInfo.setIsDelete("0");
			flag = companyInfoMapper.insertSelective(companyInfo);
			if(flag == 0) return flag;
		}
		list.removeAll(list);
		list.addAll(templist);
		return flag;
	}

	@Override
	public int getCount(Map<String, Object> map) {	
		return companyInfoMapper.selectCount(map);
	}
	
	/**检查数据库中企业名称是否已经存在
	 * */
	private int getCount(String name){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", name);
		return companyInfoMapper.selectCount(map);
	}
	
	/**
	 * 功能说明 :赋值CompanyInfoLog和组装更新后的数据
	 * @param dbCom[数据源]
	 * @param log[被赋值对象]
	 * @param upCom
	 * @param fieldMap 过滤掉存在[log]但不存在[dbCom]中的属性
	 * @return
	 */
	private void getCompanyInfoLog(CompanyInfo dbCom,
			CompanyInfoLog log, CompanyInfo upCom,Map<String,String> fieldMap)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		Map<String, String> map = getFieldDesc();
		StringBuffer sb = new StringBuffer();
		for (Field field : log.getClass().getDeclaredFields()) {
			if(field != null){
				String str = field.getName();
				if(fieldMap != null && fieldMap.get(str)!= null) continue;
				Field dbField = dbCom.getClass().getDeclaredField(str);
				if(!StringUtil.isBlank(dbField)){
					//赋值CompanyInfoLog
					Boolean bool = field.isAccessible();
					Boolean dbbool = dbField.isAccessible();
					field.setAccessible(true);
					dbField.setAccessible(true);
					field.set(log, dbField.get(dbCom));									
					//比较CompanyInfo是否更新,若更新则组装更新后的数据
					String remark = map.get(str);
					if(upCom != null && !StringUtil.isBlank(remark)){
						Field upField = upCom.getClass().getDeclaredField(str);
						Boolean upbool = upField.isAccessible();
						upField.setAccessible(true);
						String tempR = getPartRemark(dbField.getType(),dbField.get(dbCom),upField.get(upCom));
						upField.setAccessible(upbool);
						if(tempR != null){
							sb.append("；");
							sb.append(remark);
							if("".equals(tempR)){
								tempR = "''";
							}
							sb.append(tempR);						
						}
					}
					field.setAccessible(bool);
					dbField.setAccessible(dbbool);
				}	
			}
		}
		sb.append("。");
		log.setRemark(sb.substring(1).toString());
		log.setUpdateTime(new Date());
	}
	
	/**根据属性类型比较是否更新并将更新后的结果转化为String
	 * */
	private String getPartRemark(Class<?> type,Object db,Object up){
		if (Date.class == type) {
			if(((Date) db).compareTo((Date)up) != 0){
				return DateUtil.date2Str("yyyy-MM-dd hh:mm:ss", (Date)up);
			}
		} else if (Integer.class == type) {
			if(db != up){
				return String.valueOf(up);
			}
		} else if (BigDecimal.class == type) {
			if(!BigDecimalUtil.isSame((BigDecimal)db, (BigDecimal)up)){
				return String.valueOf(up);
			}
		} else {
			if((String)db == null || "".equals((String)db)){
				if(!"".equals((String)up)){
					return String.valueOf(up);
				}
			}else{
				if(!((String)db).equals((String)up)){
					return String.valueOf(up);
				}
			}
		}
		return null;
	}
	
	private Map<String,String> getFieldDesc(){		
		Map<String,String> map = new HashMap<String, String>();
		map.put("name", "企业名称变更为");
		map.put("legalPerson", "法人代表变更为");
		map.put("idGbhy", "国标行业变更为");
		map.put("nameGbhy", "国标行业名称变更为");
		map.put("isAboveScale", "是否规模以上企业[1-是 0-否]变更为");
		map.put("isCanvassBuisiness", "是否招商企业[1-是 0-否]变更为");
		map.put("status", "状态 [1-正常 0-不正常]变更为");
		map.put("companyAttr", "企业属性变更为");
		map.put("registerCapital", "注册资本变更为");
		map.put("companyType", "企业类型变更为");
		map.put("dealArea", "经营范围变更为");
		map.put("areaId", "所属区域ID变更为");
		map.put("registerDate", "登记时间变更为");	
		map.put("industryType", "行业类型变更为");
		map.put("linkman", "联络员变更为");
		map.put("linkmanMobile", "联络员电话变更为");
		map.put("politicalOutlook", "政治面貌变更为");
		map.put("currency", "币种变更为");		
		map.put("managementCategory", "经营类别变更为");	
		map.put("jurisdictionUnit", "管辖单位变更为");
		map.put("mainJurisdictionUnit", "主管管辖单位变更为");
		map.put("registrationUint", "(受委托)登记机关变更为");
		map.put("mainRegistrationUint", "主管登记机关变更为");
		map.put("approvalDate", "核准日期变更为");		
		map.put("contactMobile", "联系电话变更为");
		map.put("areaName", "所属区域名称变更为");
		map.put("gszjKId", "关联工商表id变更为");
		map.put("dsjKId", "关联地税表id变更为");
		map.put("gsjKId", "关联国税表id变更为");	
		map.put("isDelete", "是否删除 [0-未删 1-已删]变更为");	
		return map;
	}

	/**
     * 根据企业ID获取企业的基本信息以及纳税信息
	 */
	@Override
	public CompanyInfo selectInfoTaxById(BigDecimal id) {
		return companyInfoMapper.selectInfoTaxById(id);
	}
}