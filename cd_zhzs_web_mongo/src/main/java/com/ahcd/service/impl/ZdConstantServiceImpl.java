package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.common.StringUtil;
import com.ahcd.dao.ZdConstantMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.ZdConstant;
import com.ahcd.service.ZdConstantService;

@Service("zdConstantService")
public class ZdConstantServiceImpl implements ZdConstantService {
	@Resource
	private  ZdConstantMapper zdConstantMapper;

	@Override
	public int deleteByPrimaryKey(BigDecimal constantId) {
		return zdConstantMapper.deleteByPrimaryKey(constantId);
	}

	@Override
	public int insertSelective(ZdConstant record) {
		return zdConstantMapper.insertSelective(record);
	}

	@Override
	public ZdConstant selectByPrimaryKey(BigDecimal constantId) {
		return zdConstantMapper.selectByPrimaryKey(constantId);
	}

	//TODO 更新序号
	@Override
	public int updateByPrimaryKeySelective(ZdConstant record) {
		return zdConstantMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Page<ZdConstant> selectTypePage(Page<ZdConstant> page, ZdConstant bean) {
		page.setQueryBean(bean);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		page.setResult(zdConstantMapper.selectTypePage(page));
		page.setTotalCount(zdConstantMapper.selectCountTypePage(page));
		return page;
	}

	/**
	 * 
	 * 功能说明 :获取子码表管理
	 * 
	 * @author : fei yang
	 * @version ：2016年11月24日 下午3:35:31
	 * @param page
	 * @param bean
	 * @return
	 */
	public Page<ZdConstant> selectChileListPage(Page<ZdConstant> page, ZdConstant bean) {
		page.setQueryBean(bean);
		page.setBeginRow((page.getPageNum() - 1) * page.getNumPerPage());
		page.setEndRow(page.getPageNum() * page.getNumPerPage());
		page.setResult(zdConstantMapper.selectPage(page));
		page.setTotalCount(zdConstantMapper.selectCountPage(page));
		return page;
	}

	@Override
	public int deleteByIdsOrType(String ids, String type) {
		int flag = 0;
		if (StringUtil.isBlank(type)) {// 根据IDS删除
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				flag = zdConstantMapper.deleteByPrimaryKey(new BigDecimal(id[i]));
				if (flag == 0) {
					return flag;
				}
			}
			return flag;
		} else {
			String[] types = type.split(",");
			for (int i = 0; i < types.length; i++) {
				flag = zdConstantMapper.deleteByType(types[i]);
				if (flag == 0) {
					return flag;
				}
			}
		}
		return flag;
	}

	@Override
	public int saveOrUpdate(ZdConstant bean) {
		if (StringUtil.isBlank(bean.getConstantId())) {// 新增
			return zdConstantMapper.insert(bean);
		} else {
			return zdConstantMapper.updateByPrimaryKeySelective(bean);
		}
	}
	/**
	 *根据类型获取码表MAP 
	 */
	@Override
	public   Map<String, String> getConstantByType(String type) {
		Map<String, String> map =new LinkedHashMap<String, String>();
		if (!StringUtil.isBlank(type)) {
			List<ZdConstant> list=zdConstantMapper.selectByType(type);
			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getCode(), list.get(i).getValue());
			}
		}
		return map;
	}

	@Override
	public List<ZdConstant> selectorderNumbertByType(String type) {
		return zdConstantMapper.selectorderNumbertByType(type);
	}

	@Override
	public int selectMaxOrderNumbertByType(String type) {
		return zdConstantMapper.selectMaxOrderNumbertByType(type);
	}

	@Override
	public ZdConstant selectInfoByTypeAndOrderNumber(String type2, BigDecimal td) {
		return zdConstantMapper.selectInfoByTypeAndOrderNumber(type2, td);
	}


	/**
	 * 根据
	 */
	@Override
	public List<ZdConstant> getConstantListByType(String type) {
		return zdConstantMapper.getConstantListByType(type);
	}

	//根据类型获取相应的字典集合
	public List selectDicByType(String type) {
		
		return zdConstantMapper.selectDicByType(type);
	}

}
