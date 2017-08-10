package com.ahcd.xml;

public final class XMLException extends Exception
{
  public XMLException()
  {
    super("XML �����쳣");
  }

  public XMLException(String strMsg) {
    super(strMsg);
  }

  public XMLException(Throwable cause) {
    super(cause);
  }
}