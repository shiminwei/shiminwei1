package com.ahcd.service;

import java.math.BigDecimal;
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

	int updateByPrimaryKeySelective(ZdConstant record);

	Page<ZdConstant> selectTypePage(Page<ZdConstant> page, ZdConstant bean);

	Page<ZdConstant> selectChileListPage(Page<ZdConstant> page, ZdConstant bean);

	int deleteByIdsOrType(String ids, String type);

	int saveOrUpdate(ZdConstant bean);

	Map<String, String> getConstantByType(String type);
}
