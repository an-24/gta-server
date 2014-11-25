package biz.gelicon.gta.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;

@Path("teams")
@Produces(MediaType.APPLICATION_JSON)
public class Teams {
	final private static Logger log = Logger.getLogger("gta");

	@GET
	@Path("{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Team> getTeamsGET(@PathParam("token") String token) {
		return getTeams(token);
	}
	
	@POST
	public List<Team> getTeamsPOST(String token) {
		return getTeams(token);
		
	}
	
	public List<Team> getTeams(String token) {
		List<Team> list = new ArrayList<>();
		User u = Sessions.findSession(token);
		if(u==null) return list;
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	try{
    		Query q = session.createQuery("select distinct t from Team t, Person p where p.team.id=t.id AND t.active=true AND p.user.id=:id");
    		q.setInteger("id", u.getId());
    		list = q.list();
    		// fetch
    		for (Team team : list) {
        		Hibernate.initialize(team.getPersons());
			}
    		log.info("getTeams "+Arrays.deepToString(list.toArray()));
    	} finally {
    		session.close();
    	}
		
		return list;
		
	}
}
