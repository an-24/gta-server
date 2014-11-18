package biz.gelicon.gta.server;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import biz.gelicon.gta.server.data.User;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Login {
	final private static Logger log = Logger.getLogger("gta");

	@GET
	@Path("{name}/{password}")
	public String loginGET(@PathParam("name") String userName, @PathParam("password") String password) throws NoSuchAlgorithmException {
		return login(userName, password);
	}
	
	@POST
	public String loginPOST(List<String> params) throws NoSuchAlgorithmException {
		return login(params.get(0),params.get(1));
	}
	
	private String login(String userName, String password) throws NoSuchAlgorithmException {
		User user = null;
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	try{
    		Query q = session.createQuery("from User u where u.name=:name");
    		q.setString("name", userName);
    		user = (User) q.uniqueResult();
    		if(user==null) return "";
    		log.info("User "+userName+" is found");
    		//System.out.println(hashPassword(u,password));
    		if(!user.getPassword().equals(hashPassword(user,password)))  return "";
    		log.info("User "+userName+" login succ. Token ");
    	} finally {
    		session.close();
    	}
		return Sessions.newSession(user);
	}
	
	public static String hashPassword(User user, String pswd) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA1");
		//salt
		pswd = user.getId()+"#"+user.getName()+pswd;
		// generate
		digest.update(pswd.getBytes(), 0, pswd.length());
		pswd = new BigInteger(1, digest.digest()).toString(16);
		return pswd;
	}

}
