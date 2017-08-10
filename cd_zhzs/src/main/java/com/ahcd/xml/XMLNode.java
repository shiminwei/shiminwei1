package com.ahcd.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Element;

import com.ahcd.log.Logger;

public class XMLNode
{
	
  private Element srcElement;
  private XMLDocument doc;

  Element getSourceElement()
  {
    return this.srcElement;
  }

  XMLDocument getdoc() {
    return this.doc;
  }

  public XMLNode(XMLDocument doc, Element ele) {
    this.doc = doc;
    this.srcElement = ele;
  }

  public XMLDocument getDocument() {
    return this.doc;
  }

  public Object getSource() {
    return this;
  }

  public int getChildNodesCount() {
    return this.srcElement.elements().size();
  }

  public XMLNode getParent() {
    Element elParent = this.srcElement.getParent();
    if (elParent != null) {
      return new XMLNode(this.doc, elParent);
    }

    return null;
  }

  public XMLNode getChildNode(int nIndex)
  {
    if ((nIndex >= 0) && (nIndex < getChildNodesCount())) {
      List lst = this.srcElement.elements();
      Element elChild = (Element)lst.get(nIndex);
      return new XMLNode(this.doc, elChild);
    }

    return null;
  }

  public XMLNode getChildNode(String nodeName)
  {
    Element elChild = this.srcElement.element(nodeName);
    if (elChild != null) {
      return new XMLNode(this.doc, elChild);
    }

    return null;
  }

  public XMLNode[] getAllChildNode()
  {
    ArrayList list = new ArrayList();
    for (Iterator it = this.srcElement.elementIterator(); it.hasNext(); ) {
      Element elChild = (Element)it.next();
      list.add(new XMLNode(this.doc, elChild));
    }
    return (XMLNode[])list.toArray(new XMLNode[list.size()]);
  }

  public XMLNode[] getAllChildNode(String nodeName) {
    ArrayList list = new ArrayList();
    for (Iterator it = this.srcElement.elementIterator(); it.hasNext(); ) {
      Element elChild = (Element)it.next();
      if (nodeName.equals(elChild.getName())) {
        list.add(new XMLNode(this.doc, elChild));
      }
    }
    return (XMLNode[])list.toArray(new XMLNode[list.size()]);
  }

  public XMLNode createChildNode(String nodeName) {
    Element elNew = this.srcElement.addElement(nodeName);
    return new XMLNode(this.doc, elNew);
  }

  public XMLNode addChildNode(XMLNode newNode) {
    XMLNode xmlNode = (XMLNode)newNode.getSource();
    Element elNew = xmlNode.getSourceElement();
    this.srcElement.add(elNew);
    return new XMLNode(this.doc, elNew);
  }

  public void copy(XMLNode source) {
    String[] attrNames = source.getAttributeNames();
    removeAttributes();

    for (int i = 0; i < attrNames.length; i++) {
      setAttribute(attrNames[i], source.getAttributeValue(attrNames[i]));
    }
    int nCount = source.getChildNodesCount();
    if (nCount == 0) {
      String nodeValue = source.getNodeValue();
      if (!nodeValue.equals(""))
        setCDATAText(nodeValue);
    }
    else
    {
      for (int i = 0; i < nCount; i++) {
        XMLNode subSource = source.getChildNode(i);
        XMLNode subTarget = createChildNode(subSource.getNodeName());
        subTarget.copy(subSource);
      }
    }
  }

  public void removeChildren()
  {
    this.srcElement.clearContent();
  }

  public void removeAttributes() {
    String[] attrNames = getAttributeNames();
    for (int i = 0; i < attrNames.length; i++)
      removeAttribute(attrNames[i]);
  }

  public void removeChildNode(String nodeName)
  {
    this.srcElement.remove(this.srcElement.element(nodeName));
  }

  public void removeChildNode(XMLNode node) {
    XMLNode doc = (XMLNode)node.getSource();
    Element elNode = doc.getSourceElement();
    this.srcElement.remove(elNode);
  }

  public String getNodeName() {
    return this.srcElement.getName();
  }

  public String getNodeValue() {
    return this.srcElement.getTextTrim();
  }

  public void setText(String nodeValue) {
    this.srcElement.clearContent();
    this.srcElement.setText(nodeValue);
  }

  public void setCDATAText(String cdataValue) {
    this.srcElement.clearContent();
    this.srcElement.addCDATA(cdataValue);
  }

  public int getAttributesCount() {
    return this.srcElement.attributeCount();
  }

  public String[] getAttributeNames() {
    List lst = this.srcElement.attributes();
    String[] Ret = new String[lst.size()];
    Iterator item = lst.iterator();
    int i = 0;
    while (item.hasNext()) {
      Attribute attr = (Attribute)item.next();
      Ret[i] = attr.getName();
      i++;
    }
    return Ret;
  }

  public String getAttributeValue(String attrName) {
    Attribute attr = this.srcElement.attribute(attrName);
    if (attr != null) {
      String retValue = attr.getValue();
      if (retValue == null) {
        return "";
      }

      return retValue;
    }

    return "";
  }

  public void setAttribute(String attrName, String attrValue)
  {
    addAttribute(attrName, attrValue);
  }

  public void addAttribute(String attrName, String attrValue) {
    try {
      this.srcElement.addAttribute(attrName, attrValue);
    } catch (Exception e) {
      Logger.error(e.toString());
    }
  }

  public void removeAttribute(String attrName) {
    try {
      this.srcElement.remove(this.srcElement.attribute(attrName));
    }
    catch (Exception localException) {
    }
  }

  public XMLNode[] selectNodes(String xpath) {
    List lst = this.srcElement.selectNodes(xpath);
    int nCount = lst.size();
    XMLNode[] rets = new XMLNode[nCount];
    for (int i = 0; i < nCount; i++) {
      Element el = (Element)lst.get(i);
      rets[i] = new XMLNode(this.doc, el);
    }
    return rets;
  }
}