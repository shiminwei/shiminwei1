package com.ahzd.webservice.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface JSONData4GS {
//	String getJSONData(@WebParam(name="text")String JSONText);
	String getJSONData(String JSONText);
}
