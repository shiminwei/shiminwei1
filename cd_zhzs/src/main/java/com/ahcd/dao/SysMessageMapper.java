package com.ahcd.dao;

import java.util.List;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysMessage;

public interface SysMessageMapper {
	
	/**查询条数
     */
	int countPage(Page<SysMessage> page);
	
	/**查询列表
     */
	List<SysMessage> selectPage(Page<SysMessage> page);

	/**删除
     */
    int deleteByPrimaryKey(String messageId);

    /**新增
     */
    int insert(SysMessage record);

    /**新增
     */
    int insertSelective(SysMessage record);

    /**查询实体
     */
    SysMessage selectByPrimaryKey(String messageId);

    /**更新
     */
    int updateByPrimaryKeySelective(SysMessage record);

    /**更新
     */
    int updateByPrimaryKeyWithBLOBs(SysMessage record);

    /**更新
     */
    int updateByPrimaryKey(SysMessage record);

}