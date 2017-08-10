package com.ahcd.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ahcd.common.Constant;
import com.ahcd.pojo.SysReportUser;
/**
 * 登录认证的拦截器
 */
public class LoginInterceptor implements HandlerInterceptor{

	/**
	 * Handler执行完成之后调用这个方法
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exc)
			throws Exception {
		
	}

	/**
	 * Handler执行之后，ModelAndView返回之前调用这个方法
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
	}

	/**
	 * Handler执行之前调用这个方法
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		//获取请求的URL
		String url = request.getRequestURI();
		//URL:login是公开的，其它的URL都进行拦截控制
		if(url.indexOf("login")>=0 || url.indexOf("toLogin")>=0){
			return true;
		}
		//获取Session
		HttpSession session = request.getSession();
		SysReportUser user = (SysReportUser)session.getAttribute(Constant.SESSION_USER);
		
		if(user != null){
			return true;
		}
		String path = request.getContextPath();
	    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/toLogin";
		//不符合条件的，跳转到登录界面
		response.sendRedirect(basePath);
		return false;
	}

}
