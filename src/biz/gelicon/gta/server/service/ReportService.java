package biz.gelicon.gta.server.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Service;

import biz.gelicon.gta.server.reports.WorkedOut;

@Service
public class ReportService {

	@Inject
	private EntityManagerFactory entityManagerFactory;
	@Autowired
	private ApplicationContext appContext;
	
	
	public List<WorkedOut> getWorkedOutData(int teamId, Date dateBegin, Date dateEnd) {
		EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
		Query query = em.createQuery("select p.nic,p.post,p.postDict.id,0,0,0 "
								   + "from Person p, Team t where t.id=:teamId and p.team.id=t.id ",WorkedOut.class);
		query.setParameter("teamId", teamId);
		
		//List<WorkedOut> results = query.getResultList();
		//List<WorkedOut> r = new ArrayList<>();
		
		return query.getResultList();
	}

	public ApplicationContext getSpringContext() {
		return appContext;
	}
	
}
