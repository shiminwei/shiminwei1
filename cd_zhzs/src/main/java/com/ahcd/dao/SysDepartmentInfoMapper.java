package com.ahcd.dao;

import java.util.List;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentInfo;

public interface SysDepartmentInfoMapper {
	//TODO 假删用户
	int isdelete(String departmentId);

    int insertSelective(SysDepartmentInfo record);

    SysDepartmentInfo selectDapartmentInfoByID(String departmentId);

    int updateDapartmentInfoByID(SysDepartmentInfo sysDepartmentInfo);

    int updateByPrimaryKey(SysDepartmentInfo record);
    
    List<SysDepartmentInfo> selectSysDepartmentInfo();
    
    List<SysDepartmentInfo> selectSysDepartmentInfoByAreaId(String departmentAreaId);
    
    void insertDepartmentInfo(SysDepartmentInfo sysDepartmentInfo);
    
    String selectSysDepartmentInfo2(String departmentAreaId);
    
    List<SysDepartmentInfo> selectDapartmentInfo2();

    SysDepartmentInfo selectDapartmentInfoBydepartmentCode(String departmentCode);

    List<SysDepartmentInfo> selectSysDepartmentInfoBySub(String departmentCode);

    int countPage(Page<SysDepartmentInfo> page);

	List<SysDepartmentInfo> selectPage(Page<SysDepartmentInfo> page);
	
	//TODO查找某个地区id查具体部门
	List<SysDepartmentInfo> selectSysDepartmentInfoByID(Page<SysDepartmentInfo> page);
	
	//TODO查找某个地区id查具体部门的总条数
	int countPageAreaDepartment(Page<SysDepartmentInfo> page);
	
	//TODO根据name查找部门
	List<SysDepartmentInfo> selectSysDepartmentInfoByName(String name);
	
	//TODO根据area新增后查找部门
	List<SysDepartmentInfo> selectSysDepartmentInfoAreaID2(String id);
}




