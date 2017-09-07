package com.ahzd.webservice.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataHandler;
import javax.jws.WebService;

import com.ahzd.webservice.pojo.Resume;
import com.ahzd.webservice.service.FileData4GD;

//@WebService(endpointInterface = "com.ahzd.webservice.service.impl.FileData4GSImpl",serviceName="FileData4GS")
@WebService
public class FileData4GSImpl implements FileData4GD {

	@Override
	public String getFileData(String FileText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void uploadResume(Resume resume) {
		// TODO Auto-generated method stub
		System.out.println("1");  
        DataHandler handler = resume.getResume();   
        try {   
            System.out.println("2");  
        InputStream is = handler.getInputStream();   
        OutputStream os = new FileOutputStream(new File("G:\\"   
        + resume.getCandidateName() +"."+   
        resume.getResumeFileType()));   
        byte[] b = new byte[100000];   
        int bytesRead = 0;   
        while ((bytesRead = is.read(b)) != -1) {   
        os.write(b, 0, bytesRead);   
        }   
        System.out.println("3");  
        os.flush();   
        os.close();   
        is.close();   
        } catch (IOException e){  
            e.printStackTrace();          
        }  
	}

}
