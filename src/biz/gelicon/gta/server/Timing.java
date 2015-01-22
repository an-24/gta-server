package biz.gelicon.gta.server;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import biz.gelicon.gta.server.data.Message;
import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.ScreenShot;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.utils.Pair;
import biz.gelicon.gta.server.utils.WebException;
import biz.gelicon.gta.server.wrappers.MessageWrapper;
import biz.gelicon.gta.server.wrappers.TeamWrapper;
import biz.gelicon.gta.server.wrappers.UserWrapper;

@Path("timing")
@Produces(MediaType.APPLICATION_JSON)
public class Timing {
	final private static Logger log = Logger.getLogger("gta");
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Integer push(MessageWrapper m) {
		String token = m.getLeft();
		User u = Sessions.checkSession(token);
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	Transaction t = session.beginTransaction();
    	try{
    		Message message = m.getRight();
    		message.setUser(u);
    		//message.setPerson(??);
    		Query q = session.createQuery("from Team t where t.name=:name");
    		q.setString("name", message.getTeamName());
    		message.setTeam((Team) q.iterate().next());
    		session.save(message);
    		t.commit();
    		log.info("save "+m+" succ.");
    		return message.getId();
    	} catch (Exception e) {
    		t.rollback();
    		throw new WebException(e.getMessage());
    	} finally {
    		session.close();
    	}
	}
	
	@POST
	@Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(
			@FormDataParam("token") String token,
			@FormDataParam("id") Integer id, 
			@FormDataParam("picture") InputStream stream, 
			@FormDataParam("picture") FormDataContentDisposition disposition) {
		Sessions.checkSession(token);
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	Transaction t = session.beginTransaction();
    	try{
    		ByteArrayOutputStream os = new ByteArrayOutputStream();
    		byte[] buff = new byte[2048];
    		for (int len; (len = stream.read(buff)) != -1;)
                os.write(buff, 0, len);
			os.flush();
			
    		Query q = session.createQuery("from Message m where m.id=:id");
    		q.setInteger("id", id);
    		Message m = (Message) q.iterate().next();
    		ScreenShot ss = new ScreenShot(m, os.toByteArray());
    		m.setScreenshot(ss);
    		session.save(ss);
    		t.commit();
    		log.info("upload picture for id="+id+" succ.");
    		return Response.ok().build();
    	} catch (Exception e) {
    		t.rollback();
    		throw new WebException(e.getMessage());
    	} finally {
    		session.flush();
    		session.close();
    	}
	}

	@POST
	@Path("checkteamlimit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean checkTeamLimit(TeamWrapper m) {

		String token = m.getLeft();
		Sessions.checkSession(token);
		
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	try{
    		Team team = (Team) session.get(Team.class,m.getRight());
    		if(team.getLimit()==null) return true;
    		Teams.calculateWorkTime(team);
    		int weekLimitInMin = team.getLimit()*60;
    		double worked = team.getWorkedOfWeek()==null?10:team.getWorkedOfWeek()*60+10;
    		return worked<weekLimitInMin;
    	} finally {
    		session.close();
    	}
	}
	
	@POST
	@Path("checkuserlimit")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean checkUserLimit(UserWrapper m) {

		String token = m.getLeft();
		User curr = Sessions.checkSession(token);
		
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	try{
    		Pair<Integer,Integer> userteam = m.getRight();
    		Integer userId = userteam.getRight()==null?curr.getId():userteam.getRight();
    		
    		Query q = session.createQuery("from Person p where p.user.id=:userId and p.team.id=:teamId");
    		q.setInteger("teamId", userteam.getLeft());
    		q.setInteger("userId", userId);
    		Person p = (Person) q.uniqueResult();
    		if(p.getLimit()==null) return true;
    		int weekLimitInMin = p.getLimit()*60;
    		Double v = Teams.calculateWeekWorkTime(p);
    		double worked = v==null?10:v*60+10;
    		return worked<weekLimitInMin;
    	} finally {
    		session.close();
    	}
	}
	
}
