package com.ahcd.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public abstract class XmlUtils {

	/**
	 * 
	 * 功能说明 :更具XML内容获取JAVA对象
	 * 
	 * @author : fei yang
	 * @version ：2017年2月7日 下午4:24:24
	 * @param <E>
	 * @param filePath
	 * @param objClass
	 * @return
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getBean(String filePath, Class<E> eClass) {
		E bean = null;
		try {
			JAXBContext context = JAXBContext.newInstance(eClass);
			Unmarshaller u = context.createUnmarshaller();
			bean = (E) u.unmarshal(new File(filePath));
		} catch (Exception e) {
			return null;
		}
		return bean;
	}

	/**
	 * 
	   * 功能说明    :保存或者修改XML内容 
	   * @author   : fei yang 
	   * @version ：2017年2月8日 上午10:38:48 
	   * @param filePath
	   * @param eBean
	   * @return
	 */
	public static <E> boolean saveOrUpdateXml(String filePath, E eBean) {
		try {
			JAXBContext context = JAXBContext.newInstance(eBean.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "gb2312");
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setListener(new MarshallerListener());
			OutputStream os = new FileOutputStream(filePath);
			m.marshal(eBean, os);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
