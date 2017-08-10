package com.ahcd.dbutil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ahcd.pojo.DatasourceBean;
import com.alibaba.druid.pool.DruidDataSource;
/**
 * JDBC封装类
 * @author DC
 *
 */
public class JDBCUtils{
    private static Map<String,DruidDataSource> dataSourceMap  = new HashMap<String, DruidDataSource>();
    //声明线程共享变量
    public static Map<String,Connection> dbConn = new HashMap<String,Connection>();
    static{
    	loadDatasource();
    }
        
    public static void loadDatasource(){
    	dataSourceMap.clear();
    	Map<String,DatasourceBean>  dsMap=ConfigFactory.getConfig().getAllDS();
    	for(Entry<String, DatasourceBean> key:dsMap.entrySet()){
    		System.out.println(key.getKey());
    		DatasourceBean db= key.getValue();
    		DruidDataSource dataSource=new DruidDataSource();
    		dataSource.setUrl(db.getUrl());
    		dataSource.setUsername(db.getUser());//用户名
    		dataSource.setPassword(db.getPwd());//密码
    		dataSource.setInitialSize(2);
    		dataSource.setMaxActive(20);
    		dataSource.setMinIdle(0);
    		dataSource.setMaxWait(60000);
    		dataSource.setValidationQuery("SELECT 1  FROM DUAL");
    		dataSource.setTestOnBorrow(false);
    		dataSource.setTestWhileIdle(true);
    		dataSource.setPoolPreparedStatements(false);
    		dataSourceMap.put(db.getId(), dataSource);
    	}
    }
    
    public static void reloadDatasource(){
    	ConfigFactory.getConfig().reloadConfig();
    	loadDatasource();
    }
    /**
     * 获取数据连接
     * @return
     */
    public static Connection getConnection(String id){
        Connection conn =null;
        try{
        	DruidDataSource dataSource=dataSourceMap.get(id);
            if(dbConn==null){
            	dbConn=new HashMap<String, Connection>();
                conn = dataSource.getConnection();
                dbConn.put(id, conn);
            }else{
            	conn=dbConn.get(id);
            	if(conn==null || conn.isClosed()){
            		conn = dataSource.getConnection();
                    dbConn.put(id, conn);
            	}
            }
            
            System.out.println(Thread.currentThread().getName()+"连接已经开启......");
        }catch(Exception e){
            System.out.println("连接获取失败");
            e.printStackTrace();
        }
        return conn;
    }
    /***获取当前线程上的连接开启事务*/
    public static void startTransaction(String id){
        if(dbConn==null){
        	dbConn=new HashMap<String, Connection>();
        }
        Connection conn=dbConn.get(id);//首先获取当前线程的连接
        if(conn==null){//如果连接为空
            conn=getConnection(id);//从连接池中获取连接
            dbConn.put(id, conn);//将此连接放在当前线程上
            System.out.println(Thread.currentThread().getName()+"空连接从dataSource获取连接");
        }else{
            System.out.println(Thread.currentThread().getName()+"从缓存中获取连接");
        }
        try{
            conn.setAutoCommit(false);//开启事务
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //提交事务
    public static void commit(String id){
        try{
            if(dbConn==null){
            	dbConn=new HashMap<String, Connection>();
            }
            Connection conn=dbConn.get(id);//从当前线程上获取连接if(conn!=null){//如果连接为空，则不做处理
            if(null!=conn){
                conn.commit();//提交事务
                System.out.println(Thread.currentThread().getName()+"事务已经提交......");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
 
    /***回滚事务*/
    public static void rollback(String id){
        try{
            if(dbConn==null){
            	dbConn=new HashMap<String, Connection>();
            }
            Connection conn=dbConn.get(id);//检查当前线程是否存在连接
            if(conn!=null){
                conn.rollback();//回滚事务
                dbConn.remove(id);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /***关闭连接*/
    public static void close(String id){
        try{
            if(dbConn==null){
            	dbConn=new HashMap<String, Connection>();
            }
            Connection conn=dbConn.get(id);
            if(conn!=null){
                conn.close();
                System.out.println(Thread.currentThread().getName()+"连接关闭");
            }
        }catch(SQLException e){
            throw new RuntimeException(e.getMessage(),e);
        }finally{
            try {
            	dbConn.remove(id);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    //简单使用方式
    public static void main(String[] args) throws SQLException {
        //开启事务1
        JDBCUtils.startTransaction("zhzs");
        System.out.println("执行事务操作111111111111111....");
        JDBCUtils.commit("zhzs");
        //开启事务2
        JDBCUtils.startTransaction("zhzs");
        System.out.println("执行事务操作222222222222....");
        JDBCUtils.commit("zhzs");
        JDBCUtils.close("zhzs");
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
 
                public void run() {
                    Connection conn2 = JDBCUtils.getConnection("zhzs");
                    for (int i = 0; i < 2; i++) {
                        JDBCUtils.startTransaction("zhzs");
                        System.out.println(conn2);
                        System.out.println(Thread.currentThread().getName()+"执行事务操作。。。。。。。。。。。。。");
                        JDBCUtils.commit("zhzs");
                    }
                    JDBCUtils.close("zhzs");
                }
            }).start();
        }
         
    }
}