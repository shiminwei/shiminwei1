package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.IndustryInfoMapper;
import com.ahcd.pojo.IndustryInfo;
import com.ahcd.pojo.Page;
import com.ahcd.service.IndustryInfoService;

/**行业信息表Service
 * */
@Service("industryInfoService")
public class IndustryInfoServiceImpl implements IndustryInfoService {
	@Resource
	private  IndustryInfoMapper industryInfoMapper;

	@Override
	public Page<IndustryInfo> selectIndustryInfoPage(Page<IndustryInfo> page,
			IndustryInfo bean) {
		page.setQueryBean(bean);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		page.setResult(industryInfoMapper.selectIndustryInfoPage(page));
		page.setTotalCount(industryInfoMapper.selectCountIndustryInfoPage(page));
		return page;
	}

	@Override
	public IndustryInfo selectByPrimaryKey(BigDecimal industryId) {
		return industryInfoMapper.selectByPrimaryKey(industryId);
		
	}
	
	@Override
	public int saveOrUpdate(IndustryInfo bean) {
		if (StringUtil.isBlank(bean.getIndustryId())) {
			return industryInfoMapper.insert(bean);
		} else {
			return industryInfoMapper.updateByPrimaryKeySelective(bean);
		}
	}
	
	@Override
	public int deleteById(String industryId) {
		int flag = 0;
		if (StringUtil.isBlank(industryId)) {
			return flag;
		}
		String[] id = industryId.split(",");
		for (int i = 0; i < id.length; i++) {
			flag = industryInfoMapper.deleteByPrimaryKey(new BigDecimal(id[i]));
			if (flag == 0) {
				return flag;
			}
		}
		return flag;
	}

	@Override
	public List<IndustryInfo> selectIndustryInfoList(IndustryInfo bean) {
		return industryInfoMapper.selectIndustryInfoList(bean);
	}
}
