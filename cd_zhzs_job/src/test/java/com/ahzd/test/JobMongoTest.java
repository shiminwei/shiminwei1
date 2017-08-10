package com.ahzd.test;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;  

import org.apache.log4j.Logger;  
import org.junit.Test;  
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ahcd.common.JobConstant;
import com.ahcd.common.StepTypeEnum;
import com.ahzd.pojo.DataJob;
import com.ahzd.pojo.DataJobStepBase;
import com.ahzd.pojo.DataJobStepExToDb;
import com.ahzd.pojo.DataJobStepSqlScripts;
import com.ahzd.pojo.DataJobStepTxtToDb;
import com.ahzd.service.DataJobMongoService;
import com.ahzd.service.DataJobStepTxtToDbService;
  
@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class JobMongoTest {  
    @Resource  
    private DataJobMongoService dataJobMongoService = null;  
    private static DataJobStepTxtToDbService dataJobStepTxtToDbService;
    
    @Test  
    public void testAdd() {
    	DataJob dj=new DataJob();
    	dj.setName("test2");
    	List<DataJobStepBase> stepList=new ArrayList<DataJobStepBase>();
    	
    	for(int i=0;i<4;i++){
    		DataJobStepSqlScripts djs=new DataJobStepSqlScripts();
    		int m=i+1;
    		djs.setType(StepTypeEnum.SQL_SCRIPT.getIndex());
    		djs.setSortNum(m);
    		djs.setName("name"+m);
    		djs.setSql("select * from A1");
    		Map<String,String> paramMap= new HashMap<String, String>();
    		paramMap.put("@{year}@", "2017");
    		djs.setParamMap(paramMap);
    		stepList.add(djs);
    	}
    	for(int i=4;i<8;i++){
    		DataJobStepExToDb djs=new DataJobStepExToDb();
    		int m=i+1;
    		djs.setType(StepTypeEnum.EX_TO_DB2003.getIndex());
    		djs.setSortNum(m);
    		djs.setName("name"+m);
    		djs.setFilePath("d://");
    		Map<String,String> paramMap= new HashMap<String, String>();
    		paramMap.put("name", "名称");
    		djs.setParamMap(paramMap);
    		stepList.add(djs);
    	}
    	dj.setSteps(stepList);
    	dataJobMongoService.save(dj);
    }
    
  @Test  
    public void testEditSort() {
    	DataJob dj=new DataJob();
    	dj.setName("test");
    	dataJobMongoService.editStepSort(dj, "name1", 10);
    }
  
  
  @Test  
  public void testEditSort2() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
  	DataJobStepTxtToDb dataJobStepTxtToDb = new DataJobStepTxtToDb(); 
	dataJobStepTxtToDb.setFilePath("D:/3.txt");
//	dataJobStepTxtToDb.setBeginRowIndex(1);
//	dataJobStepTxtToDb.setEndRowIndex(9);
	dataJobStepTxtToDb.setSeparator("\t");
	dataJobStepTxtToDb.setDatasourceId("1");
	dataJobStepTxtToDb.setTableName("A1");
	Map<String, String> txtMap = new HashMap<String, String>();
	txtMap.put("F1", "b");
	txtMap.put("F2", "a");
	txtMap.put("F3", "c");
	txtMap.put("F4", "d");
	txtMap.put("F5", "e");
	dataJobStepTxtToDb.setParamMap(txtMap);
	dataJobStepTxtToDb.setBeginRowIndex(1);
	dataJobStepTxtToDb.setEndRowIndex(20);
	dataJobStepTxtToDbService.txtToDb(dataJobStepTxtToDb);
  }
  
  
  @Test
  public void testStepType(){
	 Map<Integer,String>  map = JobConstant.getAllStepTypes();
 	 for (Map.Entry<Integer,String> entry : map.entrySet()) {
 		System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
 	 }
  }
  
  @Test  
  public void testRead() {
	  DataJob  dataJob = dataJobMongoService.findById("58edb97600191d1658a531dc");
	  if(dataJob!=null){
		  List<? extends DataJobStepBase> list= dataJob.getSteps();
		  
		  for(DataJobStepBase step:list){
			  System.out.println(step.getStepId()+"**************"+step.getName());
			  if(step.getType() == StepTypeEnum.SQL_SCRIPT.getIndex()){
				  DataJobStepSqlScripts newStep=(DataJobStepSqlScripts) step;
				  System.out.println(newStep.getSql());
			  }else if(step.getType() == StepTypeEnum.EX_TO_DB2003.getIndex()){
				  DataJobStepExToDb newStep=(DataJobStepExToDb) step;
				  System.out.println(newStep.getFilePath());
			  }
		  }
	  }
  }
}