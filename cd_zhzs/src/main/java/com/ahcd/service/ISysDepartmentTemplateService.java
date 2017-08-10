package com.ahcd.service;

import java.math.BigDecimal;
import java.util.List;

import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;

public interface ISysDepartmentTemplateService {
	public List<SysDepartmentTemplate> getDepartmentTemplate(String departmentId);

	public OpreateResult addDepartmentTemplate(SysDepartmentTemplate sysUserTemplate);
	
	public OpreateResult deleteDepartmentTemplate(SysDepartmentTemplate sysUserTemplate);

	public OpreateResult batchAddDepartmentTemplate(List<SysDepartmentTemplate> sysDepartmentTemplateList);

	public OpreateResult batchDeleteDepartmentTemplate(List<SysDepartmentTemplate> sysDepartmentTemplateList);

	public Page<SysDepartmentTemplate> getDepartmentTemplatePage(Page<SysDepartmentTemplate> page,
			SysDepartmentTemplate sysDepartmentTemplate);
	
	public List<SysDepartmentTemplate> selectDepartmentTemplateByuserId(BigDecimal userId);
}
