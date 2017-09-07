package com.ahzd.webservice.service.impl;

import javax.jws.WebService;

import com.ahzd.webservice.service.XMLData4GS;

@WebService
public class XMLData4GSImpl implements XMLData4GS {

	@Override
	public String getXMLData(String XMLText) {
		// TODO Auto-generated method stub
		System.out.println(XMLText);
		return "1";
	}

	@Override
	public String getXMLsData(String XMLText) {
		// TODO Auto-generated method stub
		System.out.println(XMLText);
		return "1";
	}

}
