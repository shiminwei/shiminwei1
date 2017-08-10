package com.ahcd.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.Page;
import com.ahzd.service.DataSourceMongoService;


@Controller  
@RequestMapping("/admin/datasourceConfig")  
public class DatasourceConfigController {
	@Resource
	private DataSourceMongoService dataSourceMongoService;
	
	@RequestMapping("/list")  
    public String list(HttpServletRequest request,Model model,@ModelAttribute("datasourceBean")DatasourceBean datasourceBean,
    		@RequestParam(value ="pageNum", required = false) Integer pageNum ,@RequestParam(value ="numPerPage", required = false) Integer numPerPage ){ 
		Page<DatasourceBean> page=new Page<DatasourceBean>();
		if(numPerPage!=null){
			page.setNumPerPage(numPerPage);
		}
		if(pageNum!=null ){
			page.setPageNum(pageNum);
		}
		 //page=jsonConfigService.getDatasourcePage( page, datasourceBean);
		 page=dataSourceMongoService.getDataSourcePage(page, datasourceBean);// 从mongodb取数据    裴习柱  2017-04-05
		 request.setAttribute("pageList", page);
		 request.setAttribute("datasourceBean", datasourceBean);
		 return "admin/datasource/list";
	}
	
	@RequestMapping("/toAdd")  
    public String toAdd(HttpServletRequest request,Model model){ 
		request.setAttribute("operateMethod", "add");
		return "admin/datasource/edit";
	}
	
	@ResponseBody
	@RequestMapping("/add")  
    public OpreateResult add(HttpServletRequest request,Model model,@ModelAttribute("datasourceBean")DatasourceBean datasourceBean){ 
	//	OpreateResult opreateResult=jsonConfigService.saveDatasource(datasourceBean);
		OpreateResult opreateResult = dataSourceMongoService.save(datasourceBean); // 将新增的数据源保存至mongodb中      裴习柱   2017-04-05
		return opreateResult;
	}
	@RequestMapping("/toEdit")  
    public String toEdit(HttpServletRequest request,Model model,@RequestParam(value ="id", required = true) String id ){ 
	//	DatasourceBean datasourceBean=jsonConfigService.getDatasourceById(id);
		DatasourceBean datasourceBean=dataSourceMongoService.findById(id); // 根据id从mongodb中获取所选记录的信息     裴习柱  2017-04-05
		if(datasourceBean!=null){
			request.setAttribute("datasourceBean", datasourceBean);
		}
		request.setAttribute("operateMethod", "update");
		return "admin/datasource/edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")  
    public OpreateResult update(HttpServletRequest request,Model model,@ModelAttribute("datasourceBean")DatasourceBean datasourceBean){ 
	//	OpreateResult opreateResult=jsonConfigService.updateDatasource(datasourceBean);
		OpreateResult opreateResult = dataSourceMongoService.update(datasourceBean); // 将新增的数据源保存至mongodb中      裴习柱   2017-04-05
		return opreateResult;
	}
	
	@ResponseBody
	@RequestMapping("/delete")  
    public OpreateResult delete(HttpServletRequest request,Model model,@RequestParam(value ="id", required = true) String id ){ 
	//	OpreateResult op=jsonConfigService.deleteDatasourceById(id);
		OpreateResult op = dataSourceMongoService.deleteDataSourceConifgById(id); // 根据id将数据源从mongodb中删除     裴习柱   2017-04-05
		return op;
	}
}
