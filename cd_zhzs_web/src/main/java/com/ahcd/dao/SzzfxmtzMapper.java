package com.ahcd.dao;

import java.util.List;
import java.util.Map;

/**
 * @author: fei_yang
 * @version:2017年8月9日 下午6:27:44
 */
public interface SzzfxmtzMapper {

	List<Map<String, Object>> selectPage(int beginRow, int endRow, String year, String xmmc);

	Integer selectCountPage(String year, String xmmc);
}
