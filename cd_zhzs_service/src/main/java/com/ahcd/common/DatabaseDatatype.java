package com.ahcd.common;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DatabaseDatatype {
	private static Map<String,String> MAP = null ;
	private static Map<String,String> FROM_TO_MAP = null ;
	private static String TYPE = "" ;
	private static String FROM_DB = "" ;
	private static String TO_DB = "" ;
	
	/**
	 * 功能说明 :根据数据类型、数据库获得对应的数据类型
	 * @param datatype
	 * @param database
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getDataType(String datatype,String database){
		DatabaseDatatype type = new DatabaseDatatype();
		Class<?> clazz = type.getClass();	
		try {
			if(datatype == null || database == null){
				return null ;
			}
			if(MAP == null || !TYPE.equals(database.toLowerCase())){
				TYPE = database.toLowerCase() ;
				Method method = clazz.getMethod(TYPE);
				MAP = (Map<String, String>) method.invoke(type);
			}
			return MAP.get(datatype.toUpperCase());			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	/**
	 * 功能说明 :根据fromDB数据库的数据类型得到toDB数据库对应的数据类型
	 * @param datatype
	 * @param fromDB
	 * @param toDB
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized String getDataType(String datatype,String fromDB,String toDB){
		DatabaseDatatype type = new DatabaseDatatype();
		Class<?> clazz = type.getClass();	
		try {
			if(datatype == null || fromDB == null || toDB == null){
				return null ;
			}
			if(FROM_TO_MAP == null || !FROM_DB.equals(fromDB.toLowerCase()) || !TO_DB.equals(toDB.toLowerCase())){
				FROM_DB = fromDB.toLowerCase() ;
				TO_DB = toDB.toLowerCase() ;			
				Method method = clazz.getMethod("from"+capitalized(FROM_DB)+"To"+capitalized(TO_DB));
				FROM_TO_MAP = (Map<String, String>) method.invoke(type);
			}
			return FROM_TO_MAP.get(datatype.toUpperCase());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	/** oracle
	 * */
    public Map<String,String> oracle(){
    	
		Map<String,String> oracle = new HashMap<String, String>();
		//数字类型
		oracle.put("SMALLINT", "SMALLINT");
		oracle.put("INTEGER", "INTEGER");
		oracle.put("REAL", "REAL");
		oracle.put("FLOAT", "FLOAT");
		oracle.put("BINARY_FLOAT", "BINARY_FLOAT");
		oracle.put("BINARY_DOUBLE", "BINARY_DOUBLE");
		oracle.put("NUMERIC", "NUMERIC");
		oracle.put("DECIMAL", "DECIMAL");
		oracle.put("NUMBER", "NUMBER");
		//字符串类型
		oracle.put("CHAR", "CHAR");
		oracle.put("NCHAR", "NCHAR");
		oracle.put("VARCHAR", "VARCHAR");
		oracle.put("VARCHAR2", "VARCHAR2");
		oracle.put("NVARCHAR2", "NVARCHAR2");
		//日期类型
		oracle.put("DATE", "DATE");
		oracle.put("TIMESTAMP", "TIMESTAMP");
		//LOB类型
		oracle.put("BLOB", "BLOB");
		oracle.put("CLOB", "CLOB");
		return oracle;
    }
     
    /** sqlserver
	 * */
	public Map<String,String> sqlserver(){
		
		Map<String,String> sqlserver = new HashMap<String, String>();
		//整数数据类型
		sqlserver.put("TINYINT", "TINYINT");
		sqlserver.put("SMALLINT", "SMALLINT");
		sqlserver.put("INTEGER", "INTEGER");
		sqlserver.put("NUMBER", "VARCHAR ");
		sqlserver.put("BIGINT", "BIGINT");
		//浮点数据类型
		sqlserver.put("REAL", "REAL");
		sqlserver.put("FLOAT", "FLOAT");
		sqlserver.put("DECIMAL", "DECIMAL");
		sqlserver.put("NUMERIC", "NUMERIC");
		//字符数据类型	
		sqlserver.put("CHAR", "CHAR");
		sqlserver.put("VARCHAR", "VARCHAR");
		sqlserver.put("VARCHAR2", "VARCHAR");
		sqlserver.put("NCHAR", "NCHAR");
		sqlserver.put("NVARCHAR", "NVARCHAR");
		//日期和时间数据类型
		sqlserver.put("DATE", "DATE");
		sqlserver.put("TIME", "TIME");
		sqlserver.put("SMALLDATETIME", "SMALLDATETIME");
		sqlserver.put("DATETIME", "DATETIME");
		sqlserver.put("DATETIME2", "DATETIME2");
		sqlserver.put("DATETIMEOFFSET", "DATETIMEOFFSET");
		//文本和图形数据类型
		sqlserver.put("TEXT", "TEXT");
		sqlserver.put("NTEXT", "NTEXT");
		sqlserver.put("IMAGE", "IMAGE");
		//货币数据类型	
		sqlserver.put("MONEY", "MONEY");
		sqlserver.put("SMALLMONEY", "SMALLMONEY");
		//位数据类型
		sqlserver.put("BIT", "BIT");
		//二进制数据类型
		sqlserver.put("BINARY", "BINARY");
		sqlserver.put("VARBINARY", "VARBINARY");
		//时间戳
		sqlserver.put("TIMESTAMP", "TIMESTAMP");		
		return sqlserver;
	}
	
    /**	mysql
	 * */
	public Map<String,String> mysql(){
		
		Map<String,String> mysql = new HashMap<String, String>();
		//数值类型
		mysql.put("TINYINT","TINYINT");
		mysql.put("SMALLINT","SMALLINT");
		mysql.put("MEDIUMINT","MEDIUMINT");
		mysql.put("INTEGER","INTEGER");
		mysql.put("BIGINT","BIGINT");
		mysql.put("TINYINT UNSIGNED","TINYINT UNSIGNED");
		mysql.put("SMALLINT UNSIGNED","SMALLINT UNSIGNED");
		mysql.put("MEDIUMINT UNSIGNED","MEDIUMINT UNSIGNED");
		mysql.put("INTEGER UNSIGNED","INTEGER UNSIGNED");
		mysql.put("BIGINT UNSIGNED","BIGINT UNSIGNED");
		mysql.put("FLOAT","FLOAT");
		mysql.put("DOUBLE","DOUBLE");
		mysql.put("DECIMAL","DECIMAL");
		mysql.put("REAL","REAL");
		mysql.put("NUMERIC","NUMERIC");
		mysql.put("NUMBER", "int ");
		//字符串类型
		mysql.put("CHAR","CHAR");
		mysql.put("VARCHAR","VARCHAR");
		mysql.put("VARCHAR2","VARCHAR");
		mysql.put("NCHAR", "NCHAR");
		mysql.put("NVARCHAR", "NVARCHAR");
		mysql.put("TINYBLOB","TINYBLOB");
		mysql.put("TINYTEXT","TINYTEXT");
		mysql.put("BLOB","BLOB");
		mysql.put("TEXT","TEXT");
		mysql.put("MEDIUMBLOB","MEDIUMBLOB");
		mysql.put("MEDIUMTEXT","MEDIUMTEXT");
		mysql.put("LOGNGBLOB","LOGNGBLOB");
		mysql.put("LONGTEXT","LONGTEXT");
		mysql.put("BINARY","BINARY");
		mysql.put("VARBINARY","VARBINARY");
		//日期和时间类型
		mysql.put("YEAR","YEAR");
		mysql.put("TIME","TIME");
		mysql.put("DATE","DATE");
		mysql.put("TIMESTAMP","TIMESTAMP");
		mysql.put("DATETIME","DATETIME");
		//位数据类型
		mysql.put("BIT","BIT");		
		return mysql;
	}
	
	/**From mysql To sqlserver
	 * */
	public Map<String,String> fromMysqlToSqlserver(){
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("TINYINT", "TINYINT");
		map.put("SMALLINT", "SMALLINT");
		map.put("MEDIUMINT", "INTEGER");
		map.put("INTEGER", "INTEGER");
		map.put("BIGINT", "BIGINT");
		map.put("TINYINT UNSIGNED", "SMALLINT");
		map.put("SMALLINT UNSIGNED", "MEDIUMINT");
		map.put("MEDIUMINT UNSIGNED", "INTEGER");
		map.put("INTEGER UNSIGNED", "BIGINT");
		//map.put("BIGINT UNSIGNED", "");
		map.put("FLOAT", "FLOAT");
		map.put("DOUBLE", "FLOAT");
		map.put("DECIMAL", "DECIMAL");
		map.put("REAL", "REAL");
		map.put("NUMERIC", "NUMERIC");
		map.put("CHAR", "CHAR");
		map.put("VARCHAR", "VARCHAR");
		map.put("NCHAR", "NCHAR");
		map.put("NVARCHAR", "NVARCHAR");
		map.put("TINYBLOB", "VARBINARY");
		map.put("TINYTEXT", "VARCHAR");
		map.put("BLOB", "VARBINARY");
		map.put("TEXT", "TEXT");
		map.put("MEDIUMBLOB", "VARBINARY");
		map.put("MEDIUMTEXT", "NVARCHAR");
		map.put("LOGNGBLOB", "IMAGE");
		map.put("LONGTEXT", "NTEXT");
		map.put("BINARY", "BINARY");
		map.put("VARBINARY", "VARBINARY");
		map.put("YEAR", "INTEGER");
		map.put("TIME", "TIME");
		map.put("DATE", "DATE");
		map.put("TIMESTAMP", "TIMESTAMP");
		map.put("DATETIME", "DATETIME");
		map.put("BIT", "BIT");		
		return map ;
	}
	
	/** From sqlserver To mysql
	 * */
	public Map<String,String> fromSqlserverToMysql(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("TINYINT", "TINYINT");
		map.put("SMALLINT", "SMALLINT");
		map.put("INTEGER", "INTEGER");
		map.put("BIGINT", "BIGINT");
		map.put("REAL", "REAL");
		map.put("FLOAT", "FLOAT");
		map.put("DECIMAL", "DECIMAL");
		map.put("NUMERIC", "NUMERIC");
		map.put("CHAR", "CHAR");
		map.put("VARCHAR", "VARCHAR");
		map.put("NCHAR", "NCHAR");
		map.put("NVARCHAR", "NVARCHAR");
		map.put("DATE", "DATE");
		map.put("TIME", "TIME");
		map.put("SMALLDATETIME", "DATETIME");
		map.put("DATETIME", "DATETIME");
		map.put("TEXT", "TEXT");
		map.put("NTEXT", "LONGTEXT");
		map.put("IMAGE", "LOGNGBLOB");
		map.put("MONEY", "DECIMAL(19,4)");
		map.put("SMALLMONEY", "DECIMAL(10,4)");
		map.put("BIT", "BIT");
		map.put("BINARY", "BINARY");
		map.put("VARBINARY", "VARBINARY");
		map.put("TIMESTAMP", "TIMESTAMP");		
		return map ;
	}
	
	/**From sqlserver To oracle
	 * */
	public Map<String,String> fromSqlserverToOracle(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("TINYINT", "NUMBER(3,0)");
		map.put("SMALLINT", "SMALLINT");
		map.put("INTEGER", "INTEGER");
		map.put("BIGINT", "NUMBER(19,0)");
		map.put("REAL", "BINARY_FLOAT");
		map.put("FLOAT", "BINARY_DOUBLE");
		map.put("DECIMAL", "DECIMAL");
		map.put("NUMERIC", "NUMERIC");
		map.put("CHAR", "CHAR");
		map.put("VARCHAR", "VARCHAR2");
		map.put("NCHAR", "NCHAR");
		map.put("NVARCHAR", "NVARCHAR2");
		map.put("DATE", "DATE");
		//map.put("TIME", "");
		map.put("SMALLDATETIME", "DATE");
		map.put("DATETIME", "DATE");
		map.put("DATETIMEOFFSET", "DATE");
		map.put("TEXT", "CLOB");
		map.put("NTEXT", "NCLOB");
		map.put("IMAGE", "BLOB");
		map.put("MONEY", "NUMBER(19,4)");
		map.put("SMALLMONEY", "NUMBER(10,4)");
		map.put("BIT", "NUMBER(2,0)");
		map.put("BINARY", "RAW(50)");
		map.put("VARBINARY", "RAW(50)");
		map.put("TIMESTAMP", "RAW(8)");	
		return map ;
	} 
	
	 /**From oracle To sqlserver
     * */
    public Map<String,String> fromOracleToSqlserver(){
    	
    	Map<String,String> map = new HashMap<String, String>();
    	map.put("SMALLINT", "SMALLINT");
    	map.put("INTEGER", "INTEGER");
    	map.put("REAL", "VARBINARY");
    	map.put("FLOAT", "FLOAT");
    	map.put("BINARY_FLOAT", "REAL");
    	map.put("BINARY_DOUBLE", "FLOAT");
    	map.put("NUMERIC", "NUMERIC");
    	map.put("DECIMAL", "DECIMAL");
    	map.put("NUMBER", "DECIMAL");
    	map.put("CHAR", "CHAR");
    	map.put("NCHAR", "NCHAR");
    	map.put("VARCHAR", "VARCHAR");
    	map.put("VARCHAR2", "NVARCHAR");
    	map.put("NVARCHAR2", "NVARCHAR");
    	map.put("DATE", "DATE");
    	map.put("TIMESTAMP", "DATETIME");
    	map.put("BLOB", "IMAGE");
    	map.put("CLOB", "NTEXT");
    	map.put("LONG", "NTEXT");  	
    	return map;
    }
	
	/** FROM oracle TO mysql
	 * */
    public Map<String,String> fromOracleToMysql(){
    	
    	Map<String,String> map = new HashMap<String, String>();
    	map.put("SMALLINT", "SMALLINT");
    	map.put("INTEGER", "INTEGER");
    	map.put("REAL", "FLOAT");
    	map.put("FLOAT", "FLOAT");
    	map.put("BINARY_FLOAT", "FLOAT");
    	map.put("BINARY_DOUBLE", "DOUBLE");
    	map.put("NUMERIC", "NUMERIC");
    	map.put("DECIMAL", "DECIMAL");
    	map.put("NUMBER", "DECIMAL");
    	map.put("CHAR", "CHAR");
    	map.put("NCHAR", "NCHAR");
    	map.put("VARCHAR", "VARCHAR");
    	map.put("VARCHAR2", "VARCHAR");
    	map.put("DATE", "DATE");
    	map.put("TIMESTAMP", "TIMESTAMP");
    	map.put("BLOB", "LOGNGBLOB");
    	map.put("CLOB", "LONGTEXT");	
    	return map ;
    }
    
    /**From mysql To oracle
	 * */
	public Map<String,String> fromMysqlToOracle(){
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("TINYINT", "NUMBER(3,0)");
		map.put("SMALLINT", "SMALLINT");
		map.put("MEDIUMINT", "NUMBER(7,0)");
		map.put("INTEGER", "INTEGER");
		map.put("BIGINT", "NUMBER(19,0)");
		map.put("TINYINT UNSIGNED", "NUMBER(10,0)");
		map.put("SMALLINT UNSIGNED", "NUMBER(5,0)");
		map.put("MEDIUMINT UNSIGNED", "NUMBER(7,0)");
		map.put("INTEGER UNSIGNED", "NUMBER(10,0)");
		map.put("BIGINT UNSIGNED", "NUMBER(19,0)");
		map.put("FLOAT", "BINARY_FLOAT");
		map.put("DOUBLE", "BINARY_DOUBLE");
		map.put("DECIMAL", "DECIMAL");
		map.put("REAL", "FLOAT(24)");
		map.put("NUMERIC", "NUMERIC");
		map.put("CHAR", "CHAR");
		map.put("NCHAR", "NCHAR");
		map.put("VARCHAR", "VARCHAR2");
		map.put("TINYBLOB", "ROW");
		map.put("TINYTEXT", "VARCHAR2");
		map.put("BLOB", "ROW");
		map.put("TEXT", "VARCHAR2");
		map.put("MEDIUMBLOB", "BLOB");
		map.put("MEDIUMTEXT", "CLOB");
		map.put("LOGNGBLOB", "BLOB");
		map.put("LONGTEXT", "LONGTEXT");
		map.put("BINARY", "BLOB");
		map.put("VARBINARY", "BLOB");
		map.put("YEAR", "NUMBER");
		//map.put("TIME", "");
		map.put("DATE", "DATE");
		map.put("TIMESTAMP", "TIMESTAMP");	
		map.put("DATETIME", "DATE");
		map.put("BIT", "RAW");	
		map.put("SET", "VARCHAR2");
		return map ;
	}
	
	/**将传入的字符串首字母大写
	 * */
	private static String capitalized(String string) {
		if(string == null){
			return "" ;
		}
	    return string.substring(0, 1).toUpperCase() + string.substring(1);  
	}  
}