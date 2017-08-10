package com.ahcd.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月29日 上午11:04:21 类说明
 */
@Service
public interface CzsrService {
	public List<Map<String,Object>> selectAllResult(String k_date);

}
