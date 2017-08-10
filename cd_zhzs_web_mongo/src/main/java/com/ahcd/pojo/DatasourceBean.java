package com.ahcd.pojo;

public class DatasourceBean {
//	  	type: SQLSERVER=1;  ORACLE=2;  DB2=3;    MYSQL=4; SYBASE_ASE=5; SYBASE_IQ=6;  
//	    method:  jndi=1;jdbc=2
//	    webs：     weblogic=1;tomcat=2
	private String charset;
	private String database;
	private String desc;
	private String host;
	private String id;
	private String jndi;
	private String method;
	private String name;
	private String port;
	private String pwd;
	private String sid;
	private String type;
	private String user;
	private String webs;
	private String aliasName;
	private String server;
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJndi() {
		return jndi;
	}
	public void setJndi(String jndi) {
		this.jndi = jndi;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getWebs() {
		return webs;
	}
	public void setWebs(String webs) {
		this.webs = webs;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getDriver() {
		if(type.equals("oracle")){
			return "oracle.jdbc.OracleDriver";
		}if(type.equals("mysql")){
			return "com.mysql.jdbc.Driver";
		}else{
			return "";
		}
	}
	public String getUrl() {
		String orcaleUrl = "jdbc:oracle:thin:@%s:%s:%s";
		String mysqlUrl = "jdbc:mysql://%s:%s/%s";
		if(type.equals("oracle")){
			return String.format(orcaleUrl,host, port, sid);
		}if(type.equals("mysql")){
			return String.format(mysqlUrl,host, port, sid);
		}else{
			return "";
		}
		
	}

}
