<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.ahcd.common.Constant" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    pageContext.setAttribute("basePath",basePath); 
%>
<%
    pageContext.setAttribute("projectTitle",Constant.projectTitle); 
    pageContext.setAttribute("projectReportLoginUrl",Constant.projectReportLoginUrl); 
    pageContext.setAttribute("projectWebLoginUrl",Constant.projectWebLoginUrl); 
%>