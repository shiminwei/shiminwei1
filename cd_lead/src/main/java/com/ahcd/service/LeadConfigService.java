package com.ahcd.service;

import java.io.File;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ahcd.common.Constant;
import com.ahcd.common.EmptyUtils;
import com.ahcd.common.FileUtils;
import com.ahcd.common.PropertiesUntil;
import com.ahcd.common.XmlUtils;
import com.ahcd.pojo.LeadConfigBean;
import com.ahcd.pojo.SysDepartmentLead;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.impl.SysDepartmentLeadServiceImpl;
import com.ahcd.service.impl.UserServiceImpl;

@Service
public class LeadConfigService {

	public static final String lead_config_fullPath = Constant.report_path_config + Constant.lead_config_fileName;

	@Resource
	private UserServiceImpl userService;

	@Resource
	private SysDepartmentLeadServiceImpl sysDepartmentLeadService;

	/**
	 * 
	 * 功能说明 : 创建默认配置ID
	 * 
	 * @author : fei yang
	 * @version ：2017年2月8日 上午10:46:28
	 */
	@PostConstruct
	public static void toSaveDefaultConfig() {
		FileUtils.mkdirs(Constant.report_path_config);
		File file = new File(lead_config_fullPath);
		if (file == null || !file.exists()) {
			LeadConfigBean bean = new LeadConfigBean();
			bean.setLeadId(new PropertiesUntil().getPropertyValue("lead_id"));
			if (EmptyUtils.isEmpty(bean.getLeadId())) {
				bean.setLeadId(Constant.getNowStr());
			}
			XmlUtils.saveOrUpdateXml(lead_config_fullPath, bean);
		}
	}

	/**
	 * 
	 * 功能说明 :获取当前前置机的配置对象
	 * 
	 * @author : fei yang
	 * @version ：2017年2月8日 上午10:49:02
	 * @return
	 */
	public LeadConfigBean getConfigBean() {
		LeadConfigBean bean = XmlUtils.getBean(lead_config_fullPath, LeadConfigBean.class);
		return saveOrUpdate(bean);
	}

	/**
	 * 
	 * 功能说明 :修改当前配置获取新的对象
	 * 
	 * @author : fei yang
	 * @version ：2017年2月8日 下午1:24:53
	 * @param bean
	 * @return
	 */
	public LeadConfigBean saveOrUpdate(LeadConfigBean bean) {
		SysDepartmentLead departmentLead = sysDepartmentLeadService.getSysDepartmentLeadByLeadId(bean.getLeadId());
		SysReportUser user = new SysReportUser();
		if (departmentLead != null && EmptyUtils.isNotEmpty(departmentLead.getUserId())) {
			user = userService.selectUserInfoByUserId(new BigDecimal(departmentLead.getUserId()));
		}
		bean.setUserName(user.getUserName());
		bean.setDepartmentName(user.getDepartmentName());
		if (!EmptyUtils.isEmpty(user.getUserId())) {
			bean.setUserId(user.getUserId().toString());
			bean.setIsMatch(1);
		} else {
			bean.setUserId("");
			bean.setIsMatch(0);
		}
		XmlUtils.saveOrUpdateXml(lead_config_fullPath, bean);
		FileUtils.createSubdirectory( bean.getDirPath(), Constant.needMkdirs);
		return bean;
	}

	public static void main(String[] args) {
		// LeadConfigBean bean= XmlUtils.getBean(lead_config_fullPath,
		// LeadConfigBean.class);
		// System.out.println(bean.getLeadId());
		LeadConfigBean bean = new LeadConfigBean();
		bean.setQuartzStr("asdas十大撒");
		bean.setLeadId("asdasd");
		System.out.println(XmlUtils.saveOrUpdateXml(lead_config_fullPath, bean));

	}

}
