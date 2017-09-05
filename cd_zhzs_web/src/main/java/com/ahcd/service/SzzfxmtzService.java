package com.ahcd.service;

import java.util.Map;
import com.ahcd.pojo.Page;

/**
 * @author: fei_yang
 * @version:2017年8月9日 下午6:29:26
 */
public interface SzzfxmtzService {
	/**
	 * 
	 * @Title: selectPage 市直政府性项目投资信息表
	 * @author: feiyang
	 * @date: 2017年8月9日 下午6:29:48
	 * @param page
	 * @return:q
	 */
	Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, String year, String xmmc);

	Integer selectCountPage(String year, String xmmc);
}
