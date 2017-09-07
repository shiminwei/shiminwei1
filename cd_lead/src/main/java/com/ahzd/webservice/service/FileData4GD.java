package com.ahzd.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.ahzd.webservice.pojo.Resume;

@WebService
@javax.xml.ws.soap.MTOM  
public interface FileData4GD {
//	String getFileData(@WebParam(name="text")String FileText);
	@WebMethod 
	String getFileData(String FileText);
	@WebMethod  
    public void uploadResume(@WebParam(name = "resume") Resume resume);
}
