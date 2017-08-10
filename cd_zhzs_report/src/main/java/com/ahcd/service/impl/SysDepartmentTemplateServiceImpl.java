package com.ahcd.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.common.OpreateResult;
import com.ahcd.dao.SysDepartmentTemplateMapper;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.SysDepartmentTemplate;
import com.ahcd.service.ISysDepartmentTemplateService;

@Service("departmentTemplateService")
public class SysDepartmentTemplateServiceImpl implements ISysDepartmentTemplateService {
	@Resource
	private SysDepartmentTemplateMapper sysDepartmentTemplateMapper;

	@Override
	public List<SysDepartmentTemplate> getDepartmentTemplate(String departmentId) {
		return sysDepartmentTemplateMapper.selectDepartmentTemplate(departmentId);
	}

	@Override
	public OpreateResult addDepartmentTemplate(SysDepartmentTemplate sysDepartmentTemplate) {
		OpreateResult op = new OpreateResult();
		sysDepartmentTemplateMapper.delete(sysDepartmentTemplate);
		if (sysDepartmentTemplateMapper.insert(sysDepartmentTemplate) > 0) {
			op.setNavTabId("departmentSelectTemplate");
			op.setStatusCode("200");
			op.setMessage("选择成功");
		} else {
			op.setNavTabId("departmentSelectTemplate");
			op.setStatusCode("300");
			op.setMessage("选择失败");
		}
		return op;

	}

	@Override
	public OpreateResult deleteDepartmentTemplate(SysDepartmentTemplate sysUserTemplate) {
		OpreateResult op = new OpreateResult();
		if (sysDepartmentTemplateMapper.delete(sysUserTemplate) > 0) {
			op.setNavTabId("departmentSelectTemplate");
			op.setStatusCode("200");
			op.setMessage("取消成功!");
		} else {
			op.setNavTabId("departmentSelectTemplate");
			op.setStatusCode("300");
			op.setMessage("取消失败!");
		}
		return op;
	}

	@Override
	public OpreateResult batchAddDepartmentTemplate(List<SysDepartmentTemplate> sysDepartmentTemplateList) {
		OpreateResult op = new OpreateResult();
		if (sysDepartmentTemplateList == null || sysDepartmentTemplateList.size() < 1) {
			op.setNavTabId("departmentSelectTemplate");
			op.setStatusCode("300");
			op.setMessage("选择失败!");
		} else {
			sysDepartmentTemplateMapper.batchDelete(sysDepartmentTemplateList.get(0).getDepartmentId(),
					sysDepartmentTemplateList);
			if (sysDepartmentTemplateMapper.batchAdd(sysDepartmentTemplateList) > 0) {
				op.setNavTabId("departmentSelectTemplate");
				op.setStatusCode("200");
				op.setMessage("选择成功!");
			} else {
				op.setNavTabId("departmentSelectTemplate");
				op.setStatusCode("300");
				op.setMessage("选择失败!");
			}
		}
		return op;
	}

	@Override
	public OpreateResult batchDeleteDepartmentTemplate(List<SysDepartmentTemplate> sysDepartmentTemplateList) {
		OpreateResult op = new OpreateResult();
		if (sysDepartmentTemplateList == null || sysDepartmentTemplateList.size() < 1) {
			op.setNavTabId("departmentSelectTemplate");
			op.setStatusCode("300");
			op.setMessage("取消失败!");
		} else {
			if (sysDepartmentTemplateMapper.batchDelete(sysDepartmentTemplateList.get(0).getDepartmentId(),
					sysDepartmentTemplateList) > 0) {
				op.setNavTabId("departmentSelectTemplate");
				op.setStatusCode("200");
				op.setMessage("取消成功!");
			} else {
				op.setNavTabId("departmentSelectTemplate");
				op.setStatusCode("300");
				op.setMessage("取消失败!");
			}
		}
		return op;
	}

	@Override
	public Page<SysDepartmentTemplate> getDepartmentTemplatePage(Page<SysDepartmentTemplate> page,
			SysDepartmentTemplate sysDepartmentTemplate) {
		page.setQueryBean(sysDepartmentTemplate);
		int totalCount = sysDepartmentTemplateMapper.countDepartmentTemplatePage(page);
		int beginRow = (page.getPageNum() - 1) * page.getNumPerPage();
		int endRow = page.getPageNum() * page.getNumPerPage();
		page.setBeginRow(beginRow);
		page.setEndRow(endRow);

		List<SysDepartmentTemplate> resultList = sysDepartmentTemplateMapper.selectDepartmentTemplatePage(page);
		page.setTotalCount(totalCount);
		page.setResult(resultList);
		page.setPageNum(page.getPageNum());
		page.setNumPerPage(page.getNumPerPage());
		return page;
	}

	/**
	 * @author : fei yang
	 * @version ：2016年11月2日 下午3:26:03
	 */
	@Override
	public List<SysDepartmentTemplate> selectDepartmentTemplateByuserId(BigDecimal userId) {
		return sysDepartmentTemplateMapper.selectDepartmentTemplateByuserId(userId);
	}

	/**
	 * 同步关联表模板（已删模板数据同步）
	 */
	@Override
	public int deleteByTemplateName(String templateName) {
		return sysDepartmentTemplateMapper.deleteByTemplateName(templateName);
	}

	@Override
	public int countDepartmentTemplateByuserIdPage(Page<SysDepartmentTemplate> page) {
		return sysDepartmentTemplateMapper.countDepartmentTemplateByuserIdPage(page);
	}

	@Override
	public List<SysDepartmentTemplate> selectDepartmentTemplateByuserIdPage(Page<SysDepartmentTemplate> page) {
		return sysDepartmentTemplateMapper.selectDepartmentTemplateByuserIdPage(page);
	}

	@Override
	public int updateTemplateById(HashMap map) {
		return sysDepartmentTemplateMapper.updateTemplateById(map);
	}

	@Override
	public int delete(SysDepartmentTemplate sysDepartmentTemplate) {
		return sysDepartmentTemplateMapper.delete(sysDepartmentTemplate);
	}

	@Override
	public int deleteByDepartmentId(String departmentId) {
		return sysDepartmentTemplateMapper.deleteByDepartmentId(departmentId);
	}

}
