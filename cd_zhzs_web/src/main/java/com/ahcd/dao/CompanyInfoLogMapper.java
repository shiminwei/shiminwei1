package com.ahcd.dao;

import com.ahcd.pojo.CompanyInfoLog;

public interface CompanyInfoLogMapper {
    /**新增
     */
    int insert(CompanyInfoLog record);

    /**新增
     */
    int insertSelective(CompanyInfoLog record);
}