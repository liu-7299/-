package com.fh.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.admin.po.User;
import com.fh.admin.servlet.DistributedSession;
import com.fh.admin.util.KeyUtil;
import com.fh.admin.util.RedisUtil;
import com.fh.admin.util.SystemCount;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor{

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		// 执行完毕，返回前拦截
	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		// 在处理过程中，执行拦截
	}
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		// 在拦截点执行前拦截，如果返回true则不执行拦截点后的操作（拦截成功）
		// 返回false则不执行拦截
		//User user = (User)request.getSession().getAttribute(SystemCount.LOGIN_USER);
		String sessionId = DistributedSession.getSessionId(request, response);
		String users = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
		User user = JSONObject.parseObject(users, User.class);
		if(user == null){
            response.sendRedirect("/");
            return false;
        }
        return true;
		/*if(null != user){
			// 登录成功不拦截
			return true;
		}else{
			// 拦截后进入登录页面
			//获取session
			//((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse().sendRedirect(request.getContextPath());
			response.sendRedirect("/");
			//request.getContextPath()
			//request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			//request.getRequestDispatcher("loginForm").forward(request, response);
			return false;
		}*/
	}

}
