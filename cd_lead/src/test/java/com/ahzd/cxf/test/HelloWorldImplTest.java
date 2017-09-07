
package com.ahzd.cxf.test;

//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
import org.junit.Test;

//import com.ahzd.webservice.service.XMLData4GS;

import static org.junit.Assert.assertEquals;

//import java.net.MalformedURLException;
//import java.net.URL;
//import java.rmi.RemoteException;
//
//import javax.xml.namespace.QName;
//import javax.xml.rpc.ServiceException;
//import javax.xml.ws.Service;

public class HelloWorldImplTest {

    @Test
    public void testSayHi() {
        HelloWorldImpl helloWorldImpl = new HelloWorldImpl();
        String response = helloWorldImpl.sayHi("Sam");
        assertEquals("HelloWorldImpl not properly saying hi", "Hello Sam", response);
    }
    
    @Test
    public void testXML() {
//    	URL wsdlUrl = null;
//		try {
//			wsdlUrl = new URL("http://127.0.0.1:8080/cd_lead/XMLData4GS?wsdl");
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        Service s = Service.create(wsdlUrl, new QName("http://127.0.0.1:8080/cd_lead/","XMLData4GS"));
//        XMLData4GS hs = s.getPort(new QName("http://127.0.0.1:8080/cd_lead/","XMLData4GS"), XMLData4GS.class);
//        String ret = hs.getXMLData("zhangsan");
//        System.out.println(ret);
    }
    
    @Test
    public void testxml1(){
//    	org.apache.axis.client.Service sv = new org.apache.axis.client.Service();  
//    	org.apache.axis.client.Call call = null;
//		try {
//			call = (org.apache.axis.client.Call) sv.createCall();
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//         try {
//			call.setTargetEndpointAddress(new URL("http://127.0.0.1:8080/cd_lead/XMLData4GS"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//         call.setOperationName(new QName("getUsers")); 
//         call.addParameter("id", org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);  
//         call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
//         String result = null;
//		try {
//			result = (String) call.invoke(new Object[]{"1001"});
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
//         System.out.println(result);
//         Document doc = null;
//		try {
//			doc = DocumentHelper.parseText(result);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//         Element root = doc.getRootElement();
//         System.out.println("id="+root.element("UsersID").getText()+"    name="+root.element("UsersName").getText()+"     sex="+root.element("UsersSex").getText());  
           
     }  
 }  
