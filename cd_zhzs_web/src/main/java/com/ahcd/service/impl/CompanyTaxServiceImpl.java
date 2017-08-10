package com.ahcd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.dao.CompanyTaxMapper;
import com.ahcd.pojo.Page;
import com.ahcd.service.CompanyTaxService;
@Service("companyTaxServiceImpl")
public class CompanyTaxServiceImpl implements CompanyTaxService {
	@Resource
	private CompanyTaxMapper companyTaxMapper;

	@Override
	public Page<Map<String,String>> getLocalPage(Page<Map<String,String>> page) {
		int totalCount=companyTaxMapper.countLocalPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<Map<String,String>>resultList=companyTaxMapper.selectLocalPage(page);
		List<Map<String,String>>resultListSum=companyTaxMapper.selectLocalSum(page);
		if(resultListSum != null){
			page.setTotalaMap(resultListSum.get(0));
		}else{
			page.setTotalaMap(new HashMap<String, String>());
		}
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
	
	@Override
	public Page<Map<String,String>> getSZLocalPage(Page<Map<String,String>> page) {
		int totalCount=companyTaxMapper.countSZLocalPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<Map<String,String>>resultList=companyTaxMapper.selectSZLocalPage(page);
		List<Map<String,String>>resultListSum=companyTaxMapper.selectSZLocalSum(page);	
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		if(resultListSum != null){
			page.setTotalaMap(resultListSum.get(0));
		}else{
			page.setTotalaMap(new HashMap<String, String>());
		}
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
	@Override
	public Page<Map<String, String>> selectTaxInGSJ(Page<Map<String, String>> page) {
		int totalCount=companyTaxMapper.countPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<Map<String,String>>resultList=companyTaxMapper.selectTaxInGSJ(page);
		List<Map<String,String>>resultListSum=companyTaxMapper.selectTaxInGSJSum(page);
		if(resultListSum != null){
			page.setTotalaMap(resultListSum.get(0));
		}else{
			page.setTotalaMap(new HashMap<String, String>());
		}
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

	@Override
	public Page<Map<String, String>> selectTaxInHG(Page<Map<String, String>> page) {
		int totalCount=companyTaxMapper.countHGPage(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<Map<String,String>>resultList=companyTaxMapper.selectTaxInHG(page);
		List<Map<String,String>>resultListSum=companyTaxMapper.selectTaxInHGSum(page);
		if(resultListSum != null){
			page.setTotalaMap(resultListSum.get(0));
		}else{
			page.setTotalaMap(new HashMap<String, String>());
		}
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}
	
	@Override
	public Page<Map<String,String>> getDetailPage(Page<Map<String,String>> page) {
		int totalCount=companyTaxMapper.countDetailPage(page);
		String totalSum=companyTaxMapper.selectDetailSum(page);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		List<Map<String,String>>resultList=companyTaxMapper.selectDetailPage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		List<String> list = new ArrayList<String>();
		list.add(totalSum);
		page.setTotalaList(list);
		return page;
	}
}
