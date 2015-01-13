package biz.gelicon.gta.server.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Service;

import biz.gelicon.gta.server.data.Message;
import biz.gelicon.gta.server.reports.WorkedOut;

@Service
public class ReportService {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	@Autowired
	private ApplicationContext appContext;
	
	
	public List<WorkedOut> getWorkedOutData(int teamId, Date dateBegin, Date dateEnd) {
		EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
		TypedQuery<WorkedOut> query = em.createQuery("select NEW "
				+ "biz.gelicon.gta.server.reports.WorkedOut(p.nic,p.post,p.postDict.id,24*sum(m.dtFinish-m.dtBegin),"
				+ "sum(m.key+m.mouse+0.01*m.mouseMove),0.0) "
				+ "from Person p, Team t, Message m where t.id=:teamId and p.team.id=t.id "
				+ "and m.team.id=t.id and m.user.id=p.user.id "
				+ "and (p.internal=0 or p.internal is null) "
				+ "and m.dtBegin between :dtBegin and :dtEnd "
				+ "group by p.nic,p.post,p.postDict",WorkedOut.class);
		query.setParameter("teamId", teamId);
		query.setParameter("dtBegin", dateBegin);
		query.setParameter("dtEnd", dateEnd);
		
		List<WorkedOut> results = query.getResultList();
		results.forEach(wo->{
			wo.setActivityPercent(Message.getActivityPercent(wo.getHours(), wo.getActivityScore()));
		});
		
		return results;
	}

	public ApplicationContext getSpringContext() {
		return appContext;
	}
	
}
