package com.ahcd.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * @author 作者 : fei yang
 * @version 创建时间：2016年9月29日 上午11:04:21 类说明
 */
@Service
public interface ZyjjzbService {
	public List<Map<String,Object>> selectAllResult(String k_date,String k_date2);
	public List<Map<String,Object>> selectAllJjzbFqy(String k_date);
	public List<Map<String,Object>> selectAllJjzbQs(String k_date);
	List<Map<String,Object>> selectFjfkm(String k_date,String yskm);   
	public List<Map<String,Object>>selectQssrqk(String k_date,String k_date2);
	public List<Map<String,Object>>selectFqyfsz(String k_date,String k_date2);
}

