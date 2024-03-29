package biz.gelicon.gta.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.utils.DateUtils;
import biz.gelicon.gta.server.utils.WebException;

@Path("teams")
@Produces(MediaType.APPLICATION_JSON)
public class Teams {
	final private static Logger log = Logger.getLogger("gta");

	@GET
	@Path("{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Team> getTeamsGET(@PathParam("token") String token) {
		Sessions.checkSession(token);
		return getTeams(token);
	}

	@GET
	@Path("{token}/{teamId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Team getTeamGET(
			@PathParam("token") String token,
			@PathParam("teamId") int teamId) {

		Sessions.checkSession(token);
		
		List<Team> teams = getTeams(token);
		for (Team team : teams) {
			if(team.getId()==teamId) {
				Hibernate.initialize(team.getPersons());
				return team;
			}	
		}
		return null;
	}
	
	@POST
	public List<Team> getTeamsPOST(String token) {
		Sessions.checkSession(token);
		return getTeams(token);
		
	}
	
	public List<Team> getTeams(User u) {
		List<Team> list = new ArrayList<>();
		List<Object[]> agglist = new ArrayList<>();
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	try{
    		Query q = session.createQuery("select distinct t "
    				+ "from Team t, Person p "
    				+ "where p.team.id=t.id AND t.active=true AND p.user.id=:id");
    		q.setInteger("id", u.getId());
    		list = q.list();
    		// fetch
    		for (Team team : list) {
        		Hibernate.initialize(team.getPersons());
			}
    		// calculate work time for user
    		calculateWorkTime(session, list, u);
    		
    		log.info("getTeams "+Arrays.deepToString(list.toArray()));
    	} finally {
    		session.close();
    	}
		
		return list;
		
	}

	public static Double calculateWeekWorkTime(Person person) {
		Date date = new Date(); 
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	try{
    		//calculate WorkedOfWeek
    		Query q = session.createQuery("select 24*sum(m.dtFinish-m.dtBegin) "
    				+ "from Message m "
    				+ "where m.team.id=:teamId AND m.user.id=:userId AND "
    				+ "m.dtFinish between :start AND :finish ");
    		q.setInteger("teamId", person.getTeam().getId());
    		q.setInteger("userId", person.getUser().getId());
    		q.setDate("start", DateUtils.getStartOfWeek(date));
    		q.setDate("finish", DateUtils.getEndOfDay(DateUtils.getEndOfWeek(date)));
    		Object[] agglist = (Object[]) q.uniqueResult();
    		return (Double) agglist[0];
    	} finally {
    		session.close();
    	}
		
	}
	
	public static void calculateWorkTime(Team team) {
		SessionFactory dbSession = Sessions.getHibernateSession();
    	Session session = dbSession.openSession();
    	try{
    		calculateWorkTime(session, Arrays.asList(new Team[]{team}), null);
    	} finally {
    		session.close();
    	}
	}
	
	private static void calculateWorkTime(Session session, List<Team> list, User u) {
		List<Object[]> agglist;
		Query q;
		Date date = new Date(); 
		// если не по пользователю, то по первой команде в списке
		int teamId=0;
		if(u==null) {
			teamId = list.get(0).getId(); 
		}
		// calculate work time for user
		Map<Integer,Team> tmap = new HashMap<>();
		for (Team t : list) {
			tmap.put(t.getId(), t);
		}
		//calculate Limits
		/* deprecates
		q = session.createQuery("select t.id, p.limit "
				+ "from Team t, Person p "
				+ "where p.team.id=t.id AND t.active=true AND p.user.id=:id ");
		q.setInteger("id", u.getId());
		agglist = q.list();
		for (Object[] value : agglist) {
			Integer id = (Integer) value[0];
			Team team = tmap.get(id);
			if(team!=null) {
				Integer v = (Integer)value[1];
				//log.info("------- id="+id+" value="+v);
				team.setLimit(v);
			}
		}
		*/
		//calculate WorkedOfBeginProject
		q = session.createQuery("select t.id, 24*sum(m.dtFinish-m.dtBegin) "
				+ "from Message m, Team t, Person p "
				+ "where m.team.id=t.id AND m.user.id=p.user.id AND p.team.id=t.id AND t.active=true AND "
				+ (u!=null?"p.user.id=:id":"t.id=:id")+ " "
				+ "group by t.id");
		if(u!=null)	q.setInteger("id", u.getId());else q.setInteger("id", teamId); 
		agglist = q.list();
		for (Object[] value : agglist) {
			Integer id = (Integer) value[0];
			Team team = tmap.get(id);
			if(team!=null) {
				Double v = (Double)value[1];
				//log.info("------- id="+id+" value="+v);
				team.setWorkedOfBeginProject(v);
			}
		}
		//calculate WorkedOfMonth
		q = session.createQuery("select t.id, 24*sum(m.dtFinish-m.dtBegin) "
				+ "from Message m, Team t, Person p "
				+ "where m.team.id=t.id AND m.user.id=p.user.id AND p.team.id=t.id AND t.active=true AND "
				+ (u!=null?"p.user.id=:id":"t.id=:id")+" AND "
				+ "m.dtFinish between :start AND :finish "
				+ "group by t.id");
		if(u!=null)	q.setInteger("id", u.getId());else q.setInteger("id", teamId); 
		q.setDate("start", DateUtils.getStartOfMonth(date));
		q.setDate("finish", DateUtils.getEndOfDay(DateUtils.getEndOfMonth(date)));
		agglist = q.list();
		for (Object[] value : agglist) {
			Integer id = (Integer) value[0];
			Team team = tmap.get(id);
			if(team!=null) {
				Double v = (Double)value[1];
				//log.info("------- id="+id+" value="+v);
				team.setWorkedOfMonth(v);
			}
		}
		//calculate WorkedOfWeek
		q = session.createQuery("select t.id, 24*sum(m.dtFinish-m.dtBegin) "
				+ "from Message m, Team t, Person p "
				+ "where m.team.id=t.id AND m.user.id=p.user.id AND p.team.id=t.id AND t.active=true AND "
				+ (u!=null?"p.user.id=:id":"t.id=:id")+" AND "
				+ "m.dtFinish between :start AND :finish "
				+ "group by t.id");
		if(u!=null)	q.setInteger("id", u.getId());else q.setInteger("id", teamId); 
		q.setDate("start", DateUtils.getStartOfWeek(date));
		q.setDate("finish", DateUtils.getEndOfDay(DateUtils.getEndOfWeek(date)));
		agglist = q.list();
		for (Object[] value : agglist) {
			Integer id = (Integer) value[0];
			Team team = tmap.get(id);
			if(team!=null) {
				Double v = (Double)value[1];
				//log.info("------- id="+id+" value="+v);
				team.setWorkedOfWeek(v);
			}
		}
		//calculate WorkedOfDay
		q = session.createQuery("select t.id, 24*sum(m.dtFinish-m.dtBegin) "
				+ "from Message m, Team t, Person p "
				+ "where m.team.id=t.id AND m.user.id=p.user.id AND p.team.id=t.id AND t.active=true AND "
				+ (u!=null?"p.user.id=:id":"t.id=:id")+" AND "
				+ "m.dtFinish between :start AND :finish "
				+ "group by t.id");
		if(u!=null)	q.setInteger("id", u.getId());else q.setInteger("id", teamId); 
		q.setDate("start", DateUtils.getStartOfDay(date));
		q.setDate("finish", DateUtils.getEndOfDay(date));
		agglist = q.list();
		for (Object[] value : agglist) {
			Integer id = (Integer) value[0];
			Team team = tmap.get(id);
			if(team!=null) {
				Double v = (Double)value[1];
				//log.info("------- id="+id+" value="+v);
				team.setWorkedOfDay(v);
			}
		}
	}
	
	public List<Team> getTeams(String token) {
		List<Team> list = new ArrayList<>();
		User u = Sessions.findSession(token);
		if(u==null) return list;
		return getTeams(u);
	}

}
