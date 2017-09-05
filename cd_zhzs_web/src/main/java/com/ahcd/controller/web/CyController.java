package com.ahcd.controller.web;

import java.io.IOException;
//import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ahcd.common.Constant;
import com.ahcd.common.DbUntil;
import com.ahcd.common.StringUtil;
import com.ahcd.common.XmlUtils;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.HyQuertyBean;
import com.ahcd.pojo.Page;
import com.ahcd.pojo.QyBean;
//import com.ahcd.pojo.ShuiEBean;
//import com.ahcd.pojo.ShuiHuBean;
import com.ahcd.pojo.ZdConstant;
import com.ahcd.service.ConfigService;
import com.ahcd.service.CyService;
import com.ahcd.service.GbhyService;
import com.ahcd.service.HyQuertyService;
import com.ahcd.service.QyInfoService;
import com.ahcd.service.QyService;
import com.ahcd.service.ShuiEService;
import com.ahcd.service.ShuiHuService;
import com.ahcd.service.impl.ZdConstantServiceImpl;
import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * @author : fei yang
 * @version ：2016年11月26日 下午5:11:26
 */
@Controller
@RequestMapping("web/cy")
public class CyController {
	@Resource
	private GbhyService gbhyService;
	@Resource
	private HyQuertyService hyQuertyService;

	@Resource
	private ConfigService configService;

	@Resource
	private ShuiHuService shuiHuService;

	@Resource
	private ShuiEService shuiEService;

	@Resource
	private CyService cyService;

	@Resource
	private ZdConstantServiceImpl zdConstantService;

	@Resource
	private QyService qyService;

	public static List<String[]> getReturnQuerValues(String[] querValues) {
		List<String[]> reQuerValues = new ArrayList<String[]>();
		if (!StringUtil.isBlank(querValues)) {
			for (int i = 0; i < querValues.length; i++) {
				if (querValues[i].indexOf("^") >= 0) {
					String[] str = { querValues[i], querValues[i + 1] };
					reQuerValues.add(str);
					i++;
				} else if (querValues[i].equals(Constant.notChoseStr)) {
					reQuerValues.add(null);
				} else {
					String[] str = { querValues[i] };
					reQuerValues.add(str);
				}
			}
		}
		return reQuerValues;
	}

	@RequestMapping("/showCyList")
	public String showCyList(HttpServletRequest request, Model model,
			String functionid, Page<String[]> page, String[] querValues,
			Integer type) {
		page.setNumPerPage(10000);
		String filePath = configService.getXmlPath() + "/con" + functionid
				+ ".xml";
		// ConfigBean bean = configService.getBean(filePath);
		ConfigBean bean = XmlUtils.getBean(filePath, ConfigBean.class);
		Map<String, String[]> queryMap = new HashMap<String, String[]>();
		for (int i = 0; i < bean.getConditionBeans().size(); i++) {
			String[] value = { "" };
			if (!StringUtil.isBlank(request.getParameterValues(bean
					.getConditionBeans().get(i).getConditionId()))) {
				value = request.getParameterValues(bean.getConditionBeans()
						.get(i).getConditionId());
			}
			queryMap.put(bean.getConditionBeans().get(i).getConditionId(),
					value);
		}
		Page<String[]> page2 = DbUntil.toQuery(bean, page, queryMap);
		model.addAttribute("bean", bean);
		model.addAttribute("pageList", page2);
		model.addAttribute("querValues", getReturnQuerValues(querValues));
		model.addAttribute("exportValue", querValues);
		List<ZdConstant> monthList = zdConstantService
				.getConstantListByType(Constant.WEB_SHOW_MONTH_TYPE);
		model.addAttribute("months", monthList);
		model.addAttribute("queryMap", queryMap);
		String jsonQueryMap = JSON.toJSON(queryMap).toString();
		model.addAttribute("jsonQueryMap", jsonQueryMap);
		HyQuertyBean hyQuertyBean = new HyQuertyBean();

		for (Map.Entry<String, String[]> entry : queryMap.entrySet()) {
			if (entry.getKey().equals("时间始")) {
				if (!StringUtil.isBlank(entry.getValue())) {
					hyQuertyBean.setStartDate(entry.getValue()[0]);
				}
			}
			if (entry.getKey().equals("时间止")) {
				if (!StringUtil.isBlank(entry.getValue())) {
					hyQuertyBean.setEndDate(entry.getValue()[0]);
				}
			}
		}
		model.addAttribute("hyQuertyBean", hyQuertyBean);
		return "web/cy/showCyList";
	}

	@RequestMapping("/getChileCyList")
	public void getChileCyList(HttpServletResponse response, String type,
			String name, String querValues) {
		try {
			Map<String, String[]> newMap = JSON.parseObject(querValues,
					new TypeReference<Map<String, String[]>>() {
					});
			String startDate = "";
			String endDate = "";
			for (Map.Entry<String, String[]> entry : newMap.entrySet()) {
				if (entry.getKey().equals("时间始")) {
					if (!StringUtil.isBlank(entry.getValue())) {
						startDate = entry.getValue()[0];
					}
				}
				if (entry.getKey().equals("时间止")) {
					if (!StringUtil.isBlank(entry.getValue())) {
						endDate = entry.getValue()[0];
					}
				}
			}
			List<String[]> list = cyService.getList(Integer.valueOf(type),
					name, startDate, endDate);
			String jsonStr = JSON.toJSON(list).toString();
			response.setContentType("text/text;charset=GBK");
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/getShuihuList")
	public String getShuihuList(HttpServletRequest request, Model model,
			Page page, HyQuertyBean bean, String[] querValues) {
		if (!StringUtil.isBlank(querValues)) {
			String startDate = querValues[0];
			String endDate = querValues[1];
			bean.setStartDate(startDate);
			bean.setEndDate(endDate);
		}
		page = cyService.getShuiHuList(page, bean);
		model.addAttribute("pageList", page);
		model.addAttribute("bean", bean);
		return "web/cy/shuihuList";
	}

	@RequestMapping("/getQyDetil")
	public String getQyDetil(HttpServletRequest request, Model model,
			QyBean bean, String startDate, String endDate,String qymc,String year) {
		// 通过type在码表中获取对应的年份
		String type = "web_show_year";
		List<ZdConstant> yearList = zdConstantService.getConstantListByType(type);
		bean.setQymc(qymc);
		if(StringUtil.isBlank(startDate) && yearList!=null && yearList.size()>0){
			startDate= yearList.get(0).getValue();
		}
		if(StringUtil.isBlank(endDate) && yearList!=null && yearList.size()>0){
			endDate= yearList.get(yearList.size()-1).getValue();
		}
		List<Object> list = qyService.getQyJbxx(bean, startDate, endDate);
		List<Object> list0 = QyInfoService.getQyJbxx(bean);
		Map newMap = new HashMap();
		if(list0!=null && list0.size() > 0){
			Map map = (Map) list0.get(0);
			String is_canvass_buisiness = (String) map.get("is_canvass_buisiness");
			String is_above_scale = (String) map.get("IS_ABOVE_SCALE");
			newMap.put("is_canvass_buisiness", is_canvass_buisiness);
			newMap.put("is_above_scale", is_above_scale);
		}
		QyBean qyBean = (QyBean) list.get(0);
		List<String[]> seList = (List<String[]>) list.get(1);
		List<String[]> xmList = (List<String[]>) list.get(2);
		List<String[]> sjList = (List<String[]>) list.get(3);
		
		model.addAttribute("yearList", yearList);
		model.addAttribute("qyBean", qyBean);
		model.addAttribute("bean", bean);
		model.addAttribute("qyxx", newMap);
		model.addAttribute("seList", seList);
		model.addAttribute("xmList", xmList);
		model.addAttribute("sjList", sjList);
		model.addAttribute("year", year);
		return "web/cy/qyDetil";
	}

	@RequestMapping("/shuieList")
	public String shuieList(HttpServletRequest request, Model model, Page page,
			HyQuertyBean bean, String[] querValues) {
		if (!StringUtil.isBlank(querValues)) {
			String startDate = querValues[0];
			String endDate = querValues[1];
			bean.setStartDate(startDate);
			bean.setEndDate(endDate);
		}
		page = cyService.getShuiEList(page, bean);
		model.addAttribute("pageList", page);
		model.addAttribute("bean", bean);
		return "web/cy/shuieList";
	}

	@RequestMapping("/getDataByYear")
	public String getDataByYear(HttpServletRequest request, Model model,
			String startDate, String endDate, String year, String qymc) {
		QyBean bean = new QyBean();
		bean.setQymc(qymc);
		List<Object> list = qyService.getQyJbxx(bean, startDate, endDate);

		List<Object> list0 = QyInfoService.getQyJbxx(bean);
		Map newMap = new HashMap();
		if(list0!=null && list0.size() > 0){
			Map map = (Map) list0.get(0);
			String is_canvass_buisiness = (String) map.get("is_canvass_buisiness");
			String is_above_scale = (String) map.get("IS_ABOVE_SCALE");
			newMap.put("is_canvass_buisiness", is_canvass_buisiness);
			newMap.put("is_above_scale", is_above_scale);
		}
		List<Object> listXX = null;
		List<String[]> seList = null;
		List<String[]> xmList = null;
		List<String[]> sjList = null;
		if (!StringUtil.isBlank(year)) {
			listXX = qyService.getQyJbxxByYear(bean, year);
			if(listXX!=null && listXX.size()>=3){
				seList = (List<String[]>) listXX.get(1);
				xmList = (List<String[]>) listXX.get(2);
				sjList = (List<String[]>) listXX.get(3);
			}
		} else {
			if(list!=null && list.size()>=3){
				seList = (List<String[]>) list.get(1);
				xmList = (List<String[]>) list.get(2);
				sjList = (List<String[]>) list.get(3);
			}
		}
		if(list!=null && list.size() >0){
			QyBean qyBean = (QyBean) list.get(0);
			model.addAttribute("qyBean", qyBean);
		}
		// 通过type在码表中获取对应的年份
		String type = "web_show_year";
		List yearList = zdConstantService.selectDicByType(type);
		model.addAttribute("bean", bean);
		model.addAttribute("qyxx", newMap);
		model.addAttribute("seList", seList);
		model.addAttribute("xmList", xmList);
		model.addAttribute("sjList", sjList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("year", year);
		return "web/cy/qyDetil";
	}
	/*
	 * public String getDataByYear(HttpServletRequest request,Model model,
	 * String startDate,String endDate,String year,String qymc) { QyBean bean
	 * =new QyBean(); bean.setQymc(qymc); List<Object>
	 * list=qyService.getQyJbxx(bean, startDate, endDate); List<Object> listXX =
	 * qyService.getQyJbxxByYear(bean, year); List<Object> list0
	 * =QyInfoService.getQyJbxx(bean); Map map = (Map) list0.get(0); Map newMap
	 * = new HashMap(); String is_canvass_buisiness= (String)
	 * map.get("is_canvass_buisiness"); String is_above_scale = (String)
	 * map.get("IS_ABOVE_SCALE"); newMap.put("is_canvass_buisiness",
	 * is_canvass_buisiness); newMap.put("is_above_scale", is_above_scale);
	 * QyBean qyBean=(QyBean) list.get(0); List<String[]> seList=
	 * (List<String[]>) listXX.get(1); List<String[]> xmList= (List<String[]>)
	 * list.get(2); List<String[]> sjList= (List<String[]>) list.get(3);
	 * model.addAttribute("qyBean", qyBean); model.addAttribute("qyxx", newMap);
	 * model.addAttribute("seList", seList); model.addAttribute("xmList",
	 * xmList); model.addAttribute("sjList", sjList);
	 * 
	 * return "web/cy/qyDetil"; }
	 */
}
