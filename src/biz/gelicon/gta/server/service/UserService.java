package biz.gelicon.gta.server.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.Post;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.dto.UserDTO;
import biz.gelicon.gta.server.repo.PersonRepository;
import biz.gelicon.gta.server.repo.PostRepository;
import biz.gelicon.gta.server.repo.TeamRepository;
import biz.gelicon.gta.server.repo.UserRepository;

@Service
public class UserService {
	@Inject
	private UserRepository userRepository;
	@Inject
	private PersonRepository personRepository;
	@Inject
	private PostRepository postRepository;
	@Inject
	private TeamRepository teamRepository;
	@Inject
	private EntityManagerFactory entityManagerFactory;
	
	static private User currentUser;

	
	static public User getCurrentUser() {
		return currentUser;
	}

	public Set<Person> getCurrentPersons(Team team) {
		return personRepository.findByUserAndTeam(currentUser,team);
	}

	public Person getCurrentPerson(Team team, String nic) {
		return personRepository.findByUserAndTeamAndNic(currentUser,team,nic);
	}
	
	static public void setCurrentUser(User user) {
		currentUser = user;
	}


	public List<User> findByNameLike(String substr) {
		return userRepository.findByNameLike(substr);
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	public User findByIdNotAndName(Integer id,String name) {
		return userRepository.findByIdNotAndName(id,name);
	}
	
	public boolean isCurrentPM(Team team) {
		Set<Person> persons = getCurrentPersons(team);
		Optional<Person> opt = persons.stream().filter(p->p.isManager()).findFirst();
		return opt.isPresent();
	}

	public User findUser(int id) {
		return userRepository.findOne(id);
	}
	
	public Post findPost(int id) {
		return postRepository.findOne(id);
	}

	public Team findTeam(int id) {
		return teamRepository.findOne(id);
	}
	
	public List<Team> getMemebersOfTeam(User u) {
		return userRepository.findMemebers(u.getId());
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public void mergeUser(User user) {
		EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(entityManagerFactory);
		em.merge(user);
//		em.flush();
	}


	
}
