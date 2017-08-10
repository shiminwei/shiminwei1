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
public class AdminInterceptor implements HandlerInterceptor{

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
		//获取Session
		HttpSession session = request.getSession();
		SysReportUser user = (SysReportUser)session.getAttribute(Constant.SESSION_USER);
		
		if(user != null && user.getUserId().intValue()==1 && user.getUserCode().equals("admin")){
			return true;
		}
		String path = request.getContextPath();
	    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/unauthorized.jsp";
		//不符合条件的，跳转到登录界面
		response.sendRedirect(basePath);
		return false;
	}

}
