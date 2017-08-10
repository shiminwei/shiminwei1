package com.ahcd.dao;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ZdConstantMapper {
	int deleteByPrimaryKey(BigDecimal constantId);

	int insert(ZdConstant record);

	int insertSelective(ZdConstant record);

	ZdConstant selectByPrimaryKey(BigDecimal constantId);

	//TODO跟新序号
	int updateByPrimaryKeySelective(ZdConstant record);

	int updateByPrimaryKey(ZdConstant record);

	List<ZdConstant> selectPage(Page<ZdConstant> page);

	int selectCountPage(Page<ZdConstant> page);

	List<ZdConstant> selectTypePage(Page<ZdConstant> page);

	int selectCountTypePage(Page<ZdConstant> page);

	int deleteByType(String type);

	public  List<ZdConstant> selectByType(String type);
	
	List<ZdConstant> selectorderNumbertByType(String type);
	
	int selectMaxOrderNumbertByType(String type);
	
	ZdConstant selectInfoByTypeAndOrderNumber(String type2,BigDecimal td);
	

	List<ZdConstant> getConstantListByType(String type);
	
	//根据类型获取相应的字典集合
	List selectDicByType(String type);

	
}