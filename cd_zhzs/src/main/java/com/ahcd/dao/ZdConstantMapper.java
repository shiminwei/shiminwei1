package com.ahcd.dao;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;
import java.math.BigDecimal;
import java.util.List;

public interface ZdConstantMapper {
	int deleteByPrimaryKey(BigDecimal constantId);

	int insert(ZdConstant record);

	int insertSelective(ZdConstant record);

	ZdConstant selectByPrimaryKey(BigDecimal constantId);

	int updateByPrimaryKeySelective(ZdConstant record);

	int updateByPrimaryKey(ZdConstant record);

	List<ZdConstant> selectPage(Page<ZdConstant> page);

	int selectCountPage(Page<ZdConstant> page);

	List<ZdConstant> selectTypePage(Page<ZdConstant> page);

	int selectCountTypePage(Page<ZdConstant> page);

	int deleteByType(String type);

	public  List<ZdConstant> selectByType(String type);
	
	//TODO 收入预期
	ZdConstant selectShouRuYuQi(BigDecimal constantId);
}