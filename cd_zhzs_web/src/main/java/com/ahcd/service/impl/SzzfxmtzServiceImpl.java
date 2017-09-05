package com.ahcd.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.dao.SzzfxmtzMapper;
import com.ahcd.pojo.Page;
import com.ahcd.service.SzzfxmtzService;

/**
 * @author: fei_yang
 * @version:2017年8月9日 下午6:30:24
 */
@Service("szzfxmtzService")
public class SzzfxmtzServiceImpl implements SzzfxmtzService {
	@Resource
	private SzzfxmtzMapper szzfxmtzMapper;

	@Override
	public Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, String year, String xmmc) {
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		if (StringUtil.isNotEmpty(xmmc)) {
			xmmc= "%"+xmmc+"%";
		}else {
			xmmc="%%";
		}
		List<Map<String, Object>> reList = szzfxmtzMapper.selectPage(page.getBeginRow(), page.getEndRow(), year, xmmc);
		page.setResult(reList);
		Integer count=szzfxmtzMapper.selectCountPage(year, xmmc);
		page.setTotalCount(count);
		return page;

	}

	@Override
	public Integer selectCountPage(String year, String xmmc) {
		return szzfxmtzMapper.selectCountPage(year, xmmc);
	}

}
