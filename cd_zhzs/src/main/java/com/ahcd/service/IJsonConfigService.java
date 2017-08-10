package com.ahcd.service;

import java.util.List;

import com.ahcd.common.OpreateResult;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.pojo.ExcelTemplate;
import com.ahcd.pojo.Page;

public interface IJsonConfigService {
	public  Page<DatasourceBean> getDatasourcePage(Page<DatasourceBean> page, DatasourceBean queryBean);
	
	public  List<String> getAllDatasourceId();
	
	public  OpreateResult saveDatasource(DatasourceBean db);
	
	public  OpreateResult updateDatasource(DatasourceBean db);
	
	public  DatasourceBean getDatasourceById(String id) ;
	
	public  OpreateResult deleteDatasourceById(String id);
	
	public  Page<ExcelTemplate> getExcelTemplatePage(Page<ExcelTemplate> page, ExcelTemplate queryBean);
	
	public  OpreateResult saveTemplateConifg(ExcelTemplate excelTemplate);

	public boolean checkExistByTableName(String tableName);

	public OpreateResult deleteTemplateConifgByName(String name);

	public List<DatasourceBean> getAllDatasource();

	public ExcelTemplate getExcelTemplateByName(String name);

	public OpreateResult updateTemplateConifg(ExcelTemplate excelTemplate);
}
