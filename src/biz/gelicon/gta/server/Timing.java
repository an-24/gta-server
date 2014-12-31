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
import biz.gelicon.gta.server.data.ScreenShot;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.utils.MessageWrapper;
import biz.gelicon.gta.server.utils.WebException;

@Path("timing")
@Produces(MediaType.APPLICATION_JSON)
public class Timing {
	final private static Logger log = Logger.getLogger("gta");
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Integer push(MessageWrapper m) {
		String token = m.getLeft();
		User u = Sessions.findSession(token);
		if(u==null) 
			throw new WebException("Session not found");
		
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	Transaction t = session.beginTransaction();
    	try{
    		Message message = m.getRight();
    		message.setUser(u);
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
		User u = Sessions.findSession(token);
		if(u==null) 
			throw new WebException("Session not found");
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

}
