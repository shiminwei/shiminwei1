package com.ahcd.service.impl;

import java.math.BigDecimal;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.AreaIndustryTaxInfoMapper;
import com.ahcd.pojo.AreaIndustryTaxInfo;
import com.ahcd.pojo.Page;
import com.ahcd.service.AreaIndustryTaxInfoService;

/**分行业分税种分地区税率表Service
 * */
@Service("areaIndustryTaxInfoService")
public class AreaIndustryTaxInfoServiceImpl implements
		AreaIndustryTaxInfoService {
	
	@Resource
	private  AreaIndustryTaxInfoMapper areaIndustryTaxInfoMapper;
	
	@Override
	public Page<AreaIndustryTaxInfo> selectAreaIndustryTaxInfoPage(
			Page<AreaIndustryTaxInfo> page, AreaIndustryTaxInfo bean) {
		page.setQueryBean(bean);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		page.setResult(areaIndustryTaxInfoMapper.selectAreaIndustryTaxInfoPage(page));
		page.setTotalCount(areaIndustryTaxInfoMapper.selectCountAreaIndustryTaxInfoPage(page));
		return page;
	}

	@Override
	public AreaIndustryTaxInfo selectByPrimaryKey(BigDecimal aitiId) {
		return areaIndustryTaxInfoMapper.selectByPrimaryKey(aitiId);
	}

	@Override
	public int saveOrUpdate(AreaIndustryTaxInfo bean) {
		if (StringUtil.isBlank(bean.getAitiId())) {
			return areaIndustryTaxInfoMapper.insert(bean);
		} else {
			return areaIndustryTaxInfoMapper.updateByPrimaryKeySelective(bean);
		}
	}

	@Override
	public int deleteById(String aitiId) {
		int flag = 0;
		if (StringUtil.isBlank(aitiId)) {
			return flag;
		}
		String[] id = aitiId.split(",");
		for (int i = 0; i < id.length; i++) {
			flag = areaIndustryTaxInfoMapper.deleteByPrimaryKey(new BigDecimal(id[i]));
			if (flag == 0) {
				return flag;
			}
		}
		return flag;
	}

}
