package com.ahcd.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;

public interface SysDepartmentTemplateMapper {
    int insert(SysDepartmentTemplate record);

    int insertSelective(SysDepartmentTemplate record);

	List<SysDepartmentTemplate> selectDepartmentTemplate(String departmentId);

	int delete(SysDepartmentTemplate sysDepartmentTemplate);
	
	int batchAdd(@Param("sysDepartmentTemplateList")List<SysDepartmentTemplate> sysDepartmentTemplateList);
	
	int batchDelete(@Param("departmentId")String departmentId,@Param("sysDepartmentTemplateList")List<SysDepartmentTemplate> sysDepartmentTemplateList);

	int countDepartmentTemplatePage(Page<SysDepartmentTemplate> page);

	List<SysDepartmentTemplate> selectDepartmentTemplatePage(Page<SysDepartmentTemplate> page);
	
	List<SysDepartmentTemplate> selectDepartmentTemplateByuserId(BigDecimal userId);
	
	int countDepartmentTemplateByuserIdPage(Page<SysDepartmentTemplate> page);

	List<SysDepartmentTemplate> selectDepartmentTemplateByuserIdPage(Page<SysDepartmentTemplate> page);
}