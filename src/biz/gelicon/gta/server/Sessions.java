package biz.gelicon.gta.server;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.utils.WebException;

public class Sessions {
	private static Map<String,User> sessions = new HashMap<>();
	private static SessionFactory sessionFactory =  null;
	
	public static User findSession(String token) {
		return sessions.get(token);
	}

	public static User checkSession(String token) {
		User u = Sessions.findSession(token);
		if(u==null) 
			throw new WebException("Session not found");
		return u;
	}
	
	public static String newSession(User user) {
		String token = newToken();
		sessions.put(token,user);
		//sessions.put("currentUser", user);
		return token;
	}
	
	public static void updateUser(String token, User user) {
		sessions.put(token,user);
	}
	
	public static Object getSessionAttr(String name) {
		return sessions.get(name);
	}
	
	public static SessionFactory getHibernateSession() {
		if(sessionFactory==null) {
	    	Configuration cfg = new Configuration().configure();
	    	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build(); 
	    	sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}

	private static String newToken() {
		byte[] code = new byte[16];
		new SecureRandom().nextBytes(code);
		return new BigInteger(1,code).toString(16);
	}
	

}
