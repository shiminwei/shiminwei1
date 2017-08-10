package com.ahcd.dao;

import java.util.List;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysGbhy;

public interface SysGbhyMapper {
	int countPage(Page<SysGbhy> page);

	List<SysGbhy> selectPage(Page<SysGbhy> page);
}