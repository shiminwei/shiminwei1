package com.ahcd.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ahcd.log.Logger;

public class XMLDocument
{
  private XMLNode root = null;

  protected Document doc = null;

  public XMLDocument()
  {
  }

  public XMLDocument(Reader read) {
    try {
      loadFromReader(read);
    } catch (Exception e) {
      Logger.error(e.toString());
    }
  }

  public XMLDocument(InputStream input) {
    try {
      loadFromInputStream(input);
    } catch (Exception e) {
      Logger.error(e.toString());
    }
  }

  public XMLDocument(String strFileName) {
    try {
      loadFromFile(strFileName);
    } catch (Exception e) {
      Logger.error(e.toString());
    }
  }

  public void loadFromReader(Reader read) throws XMLException
  {
    SAXReader sBuilder = new SAXReader(false);
    try {
      this.doc = sBuilder.read(read);
      read.close();
    }
    catch (Exception ioE) {
      throw new XMLException(ioE);
    }
  }

  public void loadFromInputStream(InputStream input)
  {
    SAXReader sBuilder = new SAXReader(false);
    try {
      this.doc = sBuilder.read(input);
      input.close();
    }
    catch (Exception ioe) {
      Logger.error(ioe.toString());
    }
  }

  public void loadFromFile(String strFileName) throws XMLException {
    try {
      FileInputStream fin = new FileInputStream(strFileName);
      loadFromInputStream(fin);
      this.root = null;
    }
    catch (Exception e) {
      throw new XMLException(e);
    }
  }

  public void loadFromString(String str) throws XMLException
  {
    try {
      this.doc = DocumentHelper.parseText(str);
      this.root = null;
    }
    catch (DocumentException de) {
      Logger.error(de.toString());
      throw new XMLException(de);
    }
  }

  public void loadFromFile(File fileName) throws XMLException {
	    try {
	      FileInputStream fin = new FileInputStream(fileName);
	      this.loadFromInputStream(fin);
	      this.root = null;
	    }
	    catch (Exception e) {
	         Logger.error(e.toString());
	    }
}
  
  public void create(String rootName) throws XMLException
  {
    String strXML = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>";
    String strRoot = rootName;
    if (strRoot.equals("")) {
      strRoot = "Root";
    }
    strXML = strXML + "<" + strRoot + "/>";
    try {
      this.doc = DocumentHelper.parseText(strXML);
    }
    catch (DocumentException dE) {
      throw new XMLException(dE);
    }
  }

  private void saveToWriter(Writer outter, String encoding) throws XMLException {
    try {
      OutputFormat format = OutputFormat.createCompactFormat();
      format.setEncoding(encoding);
      XMLWriter outputter = new XMLWriter(outter, format);
      outputter.write(this.doc);
      outter.close();
    }
    catch (Exception ioE) {
      throw new XMLException(ioE);
    }
  }

  public String toXML() {
    if (this.doc != null) {
      return this.doc.asXML();
    }

    return "�ĵ���ʽ����!";
  }

  public String asXML()
  {
    if (this.doc != null) {
      return this.doc.asXML();
    }
    return "�ĵ���ʽ����!";
  }

  public void saveToFile(String strFileName, String encoding) throws XMLException
  {
    File fTemp = new File(strFileName);
    if (!fTemp.exists()) {
      try {
        fTemp.createNewFile();
      }
      catch (IOException ioE) {
        throw new XMLException(ioE);
      }
    }
    OutputStreamWriter fWriter = null;
    try {
      FileOutputStream fOutStream = new FileOutputStream(strFileName);
      fWriter = new OutputStreamWriter(fOutStream, encoding);
    }
    catch (FileNotFoundException fnfE) {
      throw new XMLException(fnfE);
    }
    catch (IOException ioE) {
      throw new XMLException(ioE);
    }
    try {
      saveToWriter(fWriter, encoding);
    }
    catch (XMLException xmlE) {
      throw xmlE;
    }
  }

  public void saveToFile(String strFileName) throws XMLException {
    saveToFile(strFileName, "gb2312");
  }

  public XMLNode getRoot() {
    if (this.root == null) {
      this.root = new XMLNode(this, this.doc.getRootElement());
    }
    return this.root;
  }

  public String toString() {
    return this.doc.asXML();
  }

  public boolean equals(Object parm1) {
    return toString().equals(parm1.toString());
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public XMLNode getNode(String nodeName) {
    Element rootElement = this.doc.getRootElement();
    for (Iterator it = rootElement.elementIterator(); it.hasNext(); ) {
      Element e = (Element)it.next();
      if (nodeName.equals(e.getName())) {
        return new XMLNode(this, e);
      }
    }
    return null;
  }

  public XMLNode[] selectNodes(String xpath) {
    List lst = this.doc.selectNodes(xpath);
    int nCount = lst.size();
    XMLNode[] rets = new XMLNode[nCount];
    for (int i = 0; i < nCount; i++) {
      Element el = (Element)lst.get(i);
      rets[i] = new XMLNode(this, el);
    }
    return rets;
  }

  public XMLNode selectSingleNode(String xpath) {
    Element ele = (Element)this.doc.selectSingleNode(xpath);
    return new XMLNode(this, ele);
  }
}