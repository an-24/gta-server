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
		String path = request.getPathInfo();
		boolean inner = path.indexOf("/inner/")>=0;
		// внутренний вызов
		if(inner) {
			if(UserService.getCurrentUser()==null 
					&& !path.endsWith("login")
					&& !path.endsWith("home")) {
				response.sendRedirect("login");
				return false;
			}
			return super.preHandle(request, response, handler);
		} else {
			// определение текущего пользователя
			String token = NetUtils.getTokenFromCookie(request);
			if(token!=null)
				UserService.setCurrentUser(Sessions.findSession(token));else
				// сброс пользователя
				UserService.setCurrentUser(null);

			return super.preHandle(request, response, handler);
		}
	}

}
