package com.ahcd.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.CompanyNumberInfoMapper;
import com.ahcd.pojo.CompanyNumberInfo;
import com.ahcd.pojo.Page;
import com.ahcd.service.CompanyNumberInfoService;

@Service("companyNumberInfoService")
public class CompanyNumberInfoServiceImpl implements CompanyNumberInfoService {
	
	@Resource
	private CompanyNumberInfoMapper companyNumberInfoMapper;
	
	@Override
	public int insertCompanyNumberInfo(CompanyNumberInfo companyNumberInfo) {
		return companyNumberInfoMapper.insertSelective(companyNumberInfo);
	}

	@Override
	public CompanyNumberInfo getCompanyNumberInfoByNumberId(String numberId) {
		return companyNumberInfoMapper.selectByPrimaryKey(numberId);
	}

	@Override
	public int updateCompanyNumberInfo(CompanyNumberInfo companyNumberInfo) {
		CompanyNumberInfo temp = companyNumberInfoMapper.selectByPrimaryKey(companyNumberInfo.getNumberId());
		if(temp == null){
			return -1;
		}
		temp.setIdentifyNumber(companyNumberInfo.getIdentifyNumber());
		return companyNumberInfoMapper.updateByPrimaryKey(temp);
	}

	@Override
	public int deleteByNumberId(String numberId) {
		int flag = 0;
		if(StringUtil.isBlank(numberId)){
			return flag;
		}
		for(String id:numberId.split(",")){
			flag = companyNumberInfoMapper.deleteByPrimaryKey(id);
			if (flag == 0) {
				return flag;
			}
		}
		return  flag;
	}

	@Override
	public Page<CompanyNumberInfo> getCompanyNumberInfoPage(
			Page<CompanyNumberInfo> page) {
		int totalCount=companyNumberInfoMapper.countPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<CompanyNumberInfo>resultList=companyNumberInfoMapper.selectPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

}
