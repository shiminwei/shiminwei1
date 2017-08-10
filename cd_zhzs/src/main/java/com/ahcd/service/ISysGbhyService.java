package com.ahcd.service;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysGbhy;

public interface ISysGbhyService {

	Page<SysGbhy> getNoticePage(Page<SysGbhy> page);
}
