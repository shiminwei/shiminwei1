package com.ahcd.test;


import org.junit.Test;  
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.github.drinkjava2.jdialects.Dialect;
  
@RunWith(SpringJUnit4ClassRunner.class)    
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})    
public class TestJDialects  {
	@Test
	public void page(){
//		Dialect d=guessDialect(dataSource);  //根据数据源判断方言类型,  
	       //Dialect d=guessDialect(connection);  //或根据连接来判断方言类型  
	       Dialect d=Dialect.SQLServer2005Dialect;     //或手工指定数据库方言类型 
	       String result=d.paginate(1, 20, "select name table_name from sysobjects where type = 'U'");  //创建分页SQL  
	       System.out.println(result);
	}
	
	@Test
	public void createSql(){
		 System.out.println(ddlSQL(Dialect.MySQL57InnoDBDialect));
	        System.out.println(ddlSQL(Dialect.SQLServer2012Dialect));
	        System.out.println(ddlSQL(Dialect.Oracle10gDialect));   
	}
	
	private static String ddlSQL(Dialect d) {
        return "create table " + d.check("BufferPool") + "("//
                + d.BIGINT("f1") //
                + ", " + d.BLOB("f3") //
                + ", " + d.BOOLEAN("f4") //
                + ", " + d.INTEGER("f5") //
                + ", " + d.VARCHAR("f6", 8000) //
                + ", " + d.NUMERIC("ACCESS_LOCK", 8,2) // 
                + ")" + d.engine(" DEFAULT CHARSET=utf8");
    }
}