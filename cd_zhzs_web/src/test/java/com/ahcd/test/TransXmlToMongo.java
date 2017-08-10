package com.ahcd.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;  

import org.apache.log4j.Logger;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.ahcd.pojo.ConfigBean;
import com.ahcd.pojo.DatasourceBean;
import com.ahcd.service.ConfigService;
import com.ahzd.framework.common.XmlTest;
import com.ahzd.service.ConfigBeanService;
import com.ahzd.service.DataSourceMongoService;
import com.alibaba.fastjson.JSONObject;
  
@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class TransXmlToMongo {  
    private static Logger logger = Logger.getLogger(TransXmlToMongo.class);  
    @Resource  
    private ConfigService configService = null;  
    @Resource  
    private ConfigBeanService configBeanService = null;  
    @Resource  
    private DataSourceMongoService dataSourceMongoService = null;  
    
  
//  @Before  
//  public void before() {  
//      ac = new ClassPathXmlApplicationContext("applicationContext.xml");  
//      userService = (IUserService) ac.getBean("userService");  
//  }  
    
    
    /**
	 * 
	 * 功能说明 : 获取xml路径
	 * 
	 * @author : fei yang
	 * @version ：2016年10月21日 下午3:09:42
	 * @return
	 */
    public static String getFilePath() {
		String filPath ="D:/zhzs_resource/datasource";
		File file = new File(filPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return filPath;
	}
    public List<DatasourceBean> getAllList() throws FileNotFoundException {
		String filePath = getFilePath();
		File file = new File(filePath);
		String[] filelist = file.list();
		List<DatasourceBean> list = new ArrayList<DatasourceBean>();
		if (filelist != null) {
			for (int i = 0; i < filelist.length; i++) {
				String fileName = filePath + "\\" + filelist[i];
				//DatasourceBean bean = XmlUtils.getBean(fileName, DatasourceBean.class);
				InputStream is = new FileInputStream(new File(fileName));
				String text = XmlTest.inputStreamToString(is, "UTF-8");
				DatasourceBean bean = JSONObject.parseObject(text, DatasourceBean.class);
				if (bean != null) {
					list.add(bean);
				}
			}
		} else {
			System.out.println("文件夹不不存在");
		}
		return list;
	}
    @Test  
    public void test1() {  
    	 List<ConfigBean>  xmlList = configService.getAllList();
    	 if(xmlList!=null && xmlList.size() >  0) {
    		 for (ConfigBean cb:xmlList) {
    			 	System.out.println(cb.getName());
    			 	configBeanService.save(cb);
    			 	
    		 }
    	 }
    }
   // @Test  
    public void test2() throws FileNotFoundException {  
    	 List<DatasourceBean>  fileList = getAllList();
    	 if(fileList!=null && fileList.size() >  0) {
    		 for (DatasourceBean cb:fileList) {
    			 	System.out.println(cb.getName());
    			 	dataSourceMongoService.save(cb);
    			 	
    		 }
    	 }
    }
}