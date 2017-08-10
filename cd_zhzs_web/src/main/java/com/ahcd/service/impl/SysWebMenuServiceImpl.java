package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.ahcd.common.Constant;
import com.ahcd.common.StringUtil;
import com.ahcd.dao.SysWebMenuMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysWebMenu;
import com.ahcd.service.ISysWebMenuService;

@Service("sysWebMenuService")
public class SysWebMenuServiceImpl implements ISysWebMenuService {
	@Resource
	private SysWebMenuMapper sysWebMenuMapper;

	/**
	 * @author 作者 : fei yang
	 * @version 创建时间：2016年10月23日 下午12:16:44 类说明
	 */
	@Override
	public Page<SysWebMenu> getAllByBean(Page<SysWebMenu> page, SysWebMenu bean) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("menuLevel", bean.getMenuLevel());
		String parentCode=null;
		if (!StringUtil.isBlank(bean.getParentCode())) {
			parentCode=bean.getParentCode();
		}
		map.put("parentCode", parentCode);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		map.put("beginRow", beginRow);
		map.put("endRow", endRow);
		
		page.setResult(sysWebMenuMapper.getAllByBean(map));
		page.setTotalCount(sysWebMenuMapper.getCount(map));
		return page;

	}

	/**
	 * @author 作者 : fei yang
	 * @version 创建时间：2016年10月23日 下午12:54:23 类说明 删除
	 */
	@Override
	public boolean deleteByPrimaryKey(String menuId) {
		String[] ids = menuId.split(",");
		int re = 1;
		for (int i = 0; i < ids.length; i++) {
			re = sysWebMenuMapper.deleteByPrimaryKey(ids[i]);
			if (re <= 0) {
				return false;
			}
		}
		return true;

	}

	/**
	 * @author 作者 : fei yang
	 * @version 创建时间：2016年10月23日 下午1:42:23 类说明 根据ID 查询对象
	 */
	@Override
	public SysWebMenu getBeanById(String menuId) {
		return sysWebMenuMapper.selectByPrimaryKey(menuId);

	}

	/**
	 * @author 作者 : fei yang
	 * @version 创建时间：2016年10月23日 下午1:56:06 类说明 新增获取修改
	 */
	@Override
	public int saveOrUpdateBean(SysWebMenu bean) {

		if (StringUtil.isBlank(bean.getMenuId())) {
			String code = getCodeByType(String.valueOf(bean.getMenuLevel()));
			BigDecimal menuLevel = bean.getMenuLevel();
			int orderNumber = 0;
			int	MaxOrderNumber = 0;
			if(bean.getParentCode() == null || StringUtil.isBlank(bean.getParentCode())){
				MaxOrderNumber = sysWebMenuMapper.getAMaxOrderNumber(menuLevel);
				if(MaxOrderNumber == 0){
					orderNumber = 1;
				}else{
					orderNumber = sysWebMenuMapper.getAMaxOrderNumber(menuLevel)+1;
				}
			}
			else{
				MaxOrderNumber = sysWebMenuMapper.getBCMaxOrderNumber(bean.getParentCode());
				if(MaxOrderNumber == 0){
					orderNumber = 1;
				}else{
					orderNumber = sysWebMenuMapper.getBCMaxOrderNumber(bean.getParentCode())+1;
				}
			}
			bean.setOrderNumber(new BigDecimal(orderNumber));
			bean.setCode(code);
			return sysWebMenuMapper.insertSelective(bean);
		} else {
			return sysWebMenuMapper.updateByPrimaryKey(bean);
		}

	}

	/**
	 * 
	 * 类说明: 根据类型获取CODE
	 * 
	 * @author 作者 : fei yang
	 * @version 创建时间:2016年10月23日 下午3:21:04
	 * @param menuLevel
	 * @return:
	 */
	public String getCodeByType(String menuLevel) {
		String code = "";
		if (menuLevel.equals("1")) {
			code += Constant.MENU_ONE_CODE;
		} else if (menuLevel.equals("2")) {
			code += Constant.MENU_TWO_CODE;
		} else if (menuLevel.equals("3")) {
			code += Constant.MENU_THTREE_CODE;
		}
		return code;
	}

	@Override
	public List<SysWebMenu> getAllByType(SysWebMenu bean) {
		return sysWebMenuMapper.getAllByType(bean);
	}

	@Override
	public List<SysWebMenu> getWebMenuByUserID(String userId) {
		return sysWebMenuMapper.getWebMenuByUserID(userId);
	}

	@Override
	public int getAMaxOrderNumber(BigDecimal menuLevel) {
		return sysWebMenuMapper.getAMaxOrderNumber(menuLevel);
	}

	@Override
	public int getBCMaxOrderNumber(String parentName) {
		return sysWebMenuMapper.getBCMaxOrderNumber(parentName);
	}

	@Override
	public int countBCMaxOrderNumber(BigDecimal menuLevel) {
		return sysWebMenuMapper.countBCMaxOrderNumber(menuLevel);
	}

	@Override
	public int countAMaxOrderNumber(BigDecimal menuLevel) {
		return sysWebMenuMapper.countAMaxOrderNumber(menuLevel);
	}

	@Override
	public SysWebMenu selectByOrderNumber(BigDecimal orderNumber) {
		return sysWebMenuMapper.selectByOrderNumber(orderNumber);
	}

	@Override
	public int updateByPrimaryKeySelective(SysWebMenu record) {
		return sysWebMenuMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public SysWebMenu selectByOrderNumberAndparentCode(HashMap map) {
		return sysWebMenuMapper.selectByOrderNumberAndparentCode(map);
	}

	@Override
	public Page<SysWebMenu> getAllByBean1(Page<SysWebMenu> page, SysWebMenu bean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("menuLevel", bean.getMenuLevel());
		map.put("name", bean.getName());
		String parentCode=null;
		if (!StringUtil.isBlank(bean.getParentCode())) {
			parentCode=bean.getParentCode();
		}
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		
		map.put("parentCode", parentCode);
		map.put("beginRow", beginRow);
		map.put("endRow", endRow);
		if(parentCode == null){
			page.setResult(sysWebMenuMapper.getAllByBean1(map));
			page.setTotalCount(sysWebMenuMapper.getACount(bean.getMenuLevel()));
		}else{
			page.setResult(sysWebMenuMapper.getAllByBean(map));
			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("menuLevel", bean.getMenuLevel());
			map1.put("parentCode", parentCode);
			page.setTotalCount(sysWebMenuMapper.getCount(map1));
		}
		return page;
	}

	@Override
	public Integer getACount(BigDecimal menuLevel) {
		return sysWebMenuMapper.getACount(menuLevel);
	}

	@Override
	public List<SysWebMenu> getWebMenuByUserIDNew(BigDecimal userId) {
		// 首先根据userId获取第一级菜单
//		List<SysWebMenu> menuListFirst=sysWebMenuMapper.getWebMenuByUserIDAndLevelAndParentCode(userId,null,1);
//		if(menuListFirst!=null && menuListFirst.size()>0){
//			//开始查询二级菜单
//			for(SysWebMenu firstMenu:menuListFirst){
//				List<SysWebMenu> menuListSecond=sysWebMenuMapper.getWebMenuByUserIDAndLevelAndParentCode(userId,firstMenu.getCode(),2);
//				if(menuListSecond!=null && menuListSecond.size()>0){
//					firstMenu.setChildrenMenuList(menuListSecond);
//					for(SysWebMenu secondMenu:menuListSecond){
//						List<SysWebMenu> menuListThird=sysWebMenuMapper.getWebMenuByUserIDAndLevelAndParentCode(userId,secondMenu.getCode(),3);
//						if(menuListThird!=null && menuListThird.size()>0){
//							secondMenu.setChildrenMenuList(menuListThird);
//						}
//					}
//					
//				}
//			}
//		}
		
		// 首先根据userId所有菜单
		List<SysWebMenu> menuListFirst=new ArrayList<SysWebMenu>();
		List<SysWebMenu> menuList=sysWebMenuMapper.getWebMenuByUserID(userId);
		if(menuList!=null && menuList.size()>0){
			//取出所有的一级菜单
			for(int i=0;i<menuList.size();i++){
				if(menuList.get(i).getMenuLevel()!=null && menuList.get(i).getMenuLevel().intValue()==1){
					SysWebMenu firstMenu = menuList.get(i);
//					menuList.remove(i);
					//开始查询二级菜单
					List<SysWebMenu> menuListSecond =new ArrayList<SysWebMenu>();
					for(int j=0;j<menuList.size();j++){
						if(menuList.get(j).getMenuLevel()!=null && menuList.get(j).getMenuLevel().intValue()==2 && menuList.get(j).getParentCode().equals(firstMenu.getCode())){
							SysWebMenu secondMenu=menuList.get(j);
							//开始查询三级级菜单
							List<SysWebMenu> menuListThird=new ArrayList<SysWebMenu>();
							for(int m=0;m<menuList.size();m++){
								if(menuList.get(m).getMenuLevel()!=null && menuList.get(m).getMenuLevel().intValue()==3 && menuList.get(m).getParentCode().equals(secondMenu.getCode())){
									SysWebMenu thirdMenu=menuList.get(m);
									menuListThird.add(thirdMenu);
								}
						}
						secondMenu.setChildrenMenuList(menuListThird);	
						menuListSecond.add(secondMenu);
					}
				}
				firstMenu.setChildrenMenuList(menuListSecond);	
				menuListFirst.add(firstMenu);
				}
			}
		}	
		return menuListFirst;
	}

	@Override
	public SysWebMenu getDefaultIndexMenu() {
		return sysWebMenuMapper.getDefaultIndexMenu();
	}

}
