package com.ahcd.dao;

import java.util.List;
import java.util.Map;


public interface CzsrMapper {
	List<Map<String,Object>> selectAllResult(String k_date);
	
}