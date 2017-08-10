package com.ahcd.common;

public enum  StepTypeEnum {
	//SQL_SCRIPT("SQL脚本",1),EX_TO_DB("EXCEL导入数据库",2),TXT_TO_DB("TXT导入数据库",3),DB_TO_EX97("数据库导出EXCEL97",4),DB_TO_EX2007("数据库导出EXCEL2007",5),FILE_TO_FTP("发送文件到FTP",6),FTP_TO_LOCAL("从FTP下载文件",7),DB_TO_XML("数据库导出XML",8),XML_TO_DB("XML导入数据库",9);
	SQL_SCRIPT("SQL脚本",1),EX_TO_DB2003("EXCEL2003导入数据库",2),EX_TO_DB2007("EXCEL2007导入数据库",3),DB_TO_EX2003("数据库导出EXCEL2003",4),DB_TO_EX2007("数据库导出EXCEL2007",5),FILE_TO_FTP("发送文件到FTP",6),FTP_TO_LOCAL("从FTP下载文件",7),DB_TO_XML("数据库导出XML",8),XML_TO_DB("XML导入数据库",9),TXT_TO_DB("TXT导入数据库",10),DB_TO_TXT("数据库导出TXT",11);
	private String name ;
    private int index ;
     
    private StepTypeEnum( String name , int index ){
        this.name = name ;
        this.index = index ;
    }
     
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    
}
