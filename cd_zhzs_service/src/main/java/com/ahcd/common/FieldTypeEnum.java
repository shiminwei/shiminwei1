package com.ahcd.common;

public enum  FieldTypeEnum {
	VARCHAR("字符",1),INTEGER("整数",2),FLOAT("浮点",3),DATE("日期",4);
	private String name ;
    private int index ;
     
    private FieldTypeEnum( String name , int index ){
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
