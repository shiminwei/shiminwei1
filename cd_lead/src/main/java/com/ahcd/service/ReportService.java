package com.ahcd.service;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.ahcd.common.Constant;
import com.ahcd.common.EmptyUtils;
import com.ahcd.common.FileUtils;
import com.ahcd.common.XmlUtils;
import com.ahcd.pojo.LeadConfigBean;

public class ReportService implements Job {

	@Resource
	private LeadConfigService leadConfigService;
	/**
	 * @author : fei yang
	 * @version ：2017年2月13日 上午11:24:27
	 */
	public static final String lead_config_fullPath = Constant.report_path_config + Constant.lead_config_fileName;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		LeadConfigBean configBean = XmlUtils.getBean(lead_config_fullPath, LeadConfigBean.class);
		String filePath = configBean.getDirPath();
		if (EmptyUtils.isNotEmpty(filePath)) {
			String[] needMkdirs = Constant.needMkdirs;
			for (int i = 0; i < needMkdirs.length; i++) {
				String path = filePath = "/" + needMkdirs[i];
				File file = new File(path);
				if (file.exists() && file.isDirectory()) {
					String[] fileNames = file.list();
					for (int j = 0; j < fileNames.length; j++) {
						String excelName = path + "/" + fileNames[j];
						if (FileUtils.vidateFileType(excelName, "xls,xlsx")) {
							File excelFile = new File(excelName);
						}
					}
				}
			}

		}

		// if (EmptyUtils.isNotEmpty(configBean.getIsStart()) &&
		// configBean.getIsStart() == 1) {// 已经设置启动了
		// System.out.println("任务当前状态====>启动");
		// if (EmptyUtils.isNotEmpty(configBean.getQuartzStr())) {
		//
		// }else {
		// System.out.println("时间格式未设置");
		// }
		// } else {
		// System.out.println("任务当前状态====>停止");
		// }
	}

	
	
	
	
	public static void main(String[] args) {
		ReportService service = new ReportService();

	}
}
