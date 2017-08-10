package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.TaxInfoMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.TaxInfo;
import com.ahcd.service.TaxInfoService;
/**税种信息表Service
 * */
@Service("taxInfoService")
public class TaxInfoServiceImpl implements TaxInfoService {

	@Resource
	private  TaxInfoMapper taxInfoMapper;
	
	@Override
	public Page<TaxInfo> selectTaxInfoPage(Page<TaxInfo> page, TaxInfo bean) {
		page.setQueryBean(bean);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		page.setResult(taxInfoMapper.selectTaxInfoPage(page));
		page.setTotalCount(taxInfoMapper.selectCountTaxInfoPage(page));
		return page;
	}

	@Override
	public TaxInfo selectByPrimaryKey(BigDecimal taxId) {
		return taxInfoMapper.selectByPrimaryKey(taxId);
	}

	@Override
	public int saveOrUpdate(TaxInfo bean) {
		if (StringUtil.isBlank(bean.getTaxId())) {
			return taxInfoMapper.insert(bean);
		} else {
			return taxInfoMapper.updateByPrimaryKeySelective(bean);
		}
	}

	@Override
	public int deleteById(String taxId) {
		int flag = 0;
		if (StringUtil.isBlank(taxId)) {
			return flag;
		}
		String[] id = taxId.split(",");
		for (int i = 0; i < id.length; i++) {
			flag = taxInfoMapper.deleteByPrimaryKey(new BigDecimal(id[i]));
			if (flag == 0) {
				return flag;
			}
		}
		return flag;
	}

	@Override
	public List<TaxInfo> selectTaxInfoList(TaxInfo bean) {
		return taxInfoMapper.selectTaxInfoList(bean);
	}

}
