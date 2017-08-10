package com.ahcd.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.CompanyAddressInfoMapper;
import com.ahcd.pojo.CompanyAddressInfo;
import com.ahcd.pojo.Page;
import com.ahcd.service.CompanyAddressInfoService;

@Service("companyAddressInfoService")
public class CompanyAddressInfoServiceImpl implements CompanyAddressInfoService {
	
	@Resource
	private CompanyAddressInfoMapper companyAddressInfoMapper;
	
	@Override
	public int insertCompanyAddressInfo(CompanyAddressInfo addressInfo) {
		return companyAddressInfoMapper.insertSelective(addressInfo);
	}

	@Override
	public CompanyAddressInfo getCompanyAddressInfoById(String addressId) {
		return companyAddressInfoMapper.selectByPrimaryKey(addressId);
	}

	@Override
	public int updateCompanyAddressInfo(CompanyAddressInfo addressInfo) {
		CompanyAddressInfo temp = companyAddressInfoMapper.selectByPrimaryKey(addressInfo.getAddressId());
		if(temp == null){
			return -1;
		}
		temp.setAddress(addressInfo.getAddress());
		temp.setType(addressInfo.getType());
		temp.setAddressType(addressInfo.getAddressType());
		return companyAddressInfoMapper.updateByPrimaryKey(temp);
	}

	@Override
	public int deleteByAddressId(String addressId) {
		int flag = 0;
		if(StringUtil.isBlank(addressId)){
			return flag;
		}
		for(String id:addressId.split(",")){
			flag = companyAddressInfoMapper.deleteByPrimaryKey(id);
			if (flag == 0) {
				return flag;
			}
		}
		return  flag;
	}

	@Override
	public Page<CompanyAddressInfo> getCompanyAddressInfoPage(
			Page<CompanyAddressInfo> page) {
		int totalCount=companyAddressInfoMapper.countPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<CompanyAddressInfo>resultList=companyAddressInfoMapper.selectPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

}
