package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;

/**
 * @author : fei yang
 * @version ：2016年11月24日 下午1:35:58
 */
public interface ZdConstantService {

	int deleteByPrimaryKey(BigDecimal constantId);

	int insertSelective(ZdConstant record);

	ZdConstant selectByPrimaryKey(BigDecimal constantId);

	//TODO 更新序号
	int updateByPrimaryKeySelective(ZdConstant record);

	Page<ZdConstant> selectTypePage(Page<ZdConstant> page, ZdConstant bean);

	Page<ZdConstant> selectChileListPage(Page<ZdConstant> page, ZdConstant bean);

	int deleteByIdsOrType(String ids, String type);

	int saveOrUpdate(ZdConstant bean);

	Map<String, String> getConstantByType(String type);
	
	//TODO 查询序号
	List<ZdConstant> selectorderNumbertByType(String type);
	
	//TODO 根据类型查询最大序号
	int selectMaxOrderNumbertByType(String type);
	
	//TODO 根据类型和序号查询
	ZdConstant selectInfoByTypeAndOrderNumber(String type2,BigDecimal td);
	
	//TODO查税种
	List<ZdConstant> getConstantListByType(String type);
	
	//根据类型获取相应的字典集合
	List selectDicByType(String type);
	
	//获取相应的年份区间 
	

}
