package biz.gelicon.gta.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.NetUtils;

public class SystemInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// внутренний вызов
		if(request.getCookies()==null) {
			if(UserService.getCurrentUser()==null 
					&& !request.getPathInfo().endsWith("login")
					&& !request.getPathInfo().endsWith("home")) {
				response.sendRedirect("login");
				return false;
			}
			return super.preHandle(request, response, handler);
		} else {
			// определение текущего пользователя
			String token = NetUtils.getTokenFromCookie(request);
			if(token!=null)
				UserService.setCurrentUser(Sessions.findSession(token));
			return super.preHandle(request, response, handler);
		}
	}

}
