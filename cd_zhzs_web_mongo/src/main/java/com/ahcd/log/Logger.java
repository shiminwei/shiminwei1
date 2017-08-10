package com.ahcd.log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger
{
  private static final Log log = LogFactory.getLog(Logger.class);

  public static void info(String msg) {
    log.info(msg);
  }

  public static void error(String msg) {
    log.error(msg);
  }

  public static void main(String[] args)
    throws Exception
  {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    String now = df.format(cal.getTime());
    String history = "2007-01-01";
  }
}