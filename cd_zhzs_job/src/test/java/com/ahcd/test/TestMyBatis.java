package com.ahcd.test;

import java.io.File;

import javax.annotation.Resource;  
  
import org.apache.log4j.Logger;  
import org.junit.Test;  
import org.junit.runner.RunWith;  
import org.springframework.test.context.ContextConfiguration;  
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ahcd.common.Constant;
import com.ahcd.common.FileUtil;
import com.ahcd.common.JobConstant;
import com.ahcd.pojo.SysReportUser;
import com.ahcd.service.IUserService;
  
@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
  
public class TestMyBatis {  
    private static Logger logger = Logger.getLogger(TestMyBatis.class);  
    @Resource  
    private IUserService userService = null;  
  
//  @Before  
//  public void before() {  
//      ac = new ClassPathXmlApplicationContext("applicationContext.xml");  
//      userService = (IUserService) ac.getBean("userService");  
//  }  
  
    @Test  
    public void test1() {  
    	SysReportUser user = userService.selectByLoginCode("admin");
         System.out.println(user.getUserName());  
         logger.info("ֵ姓名"+user.getUserName()); 
    }
    
    @Test
    public void checkFilePath(){
    	String filePath = JobConstant.getFileSavePath("aaa1", "excel/22.xls");
    	System.out.println(filePath);
    }
}