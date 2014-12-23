package biz.gelicon.gta.server.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.repo.PersonRepository;
import biz.gelicon.gta.server.repo.UserRepository;

@Service
public class UserService {
	@Inject
	private UserRepository userRepository;
	@Inject
	private PersonRepository personRepository;
	
	static private User currentUser;

	
	static public User getCurrentUser() {
		return currentUser;
	}

	public List<Person> getCurrentPersons(Team team) {
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

	public boolean isCurrentPM(Team team) {
		List<Person> persons = getCurrentPersons(team);
		Optional<Person> opt = persons.stream().filter(p->p.isManager()).findFirst();
		return opt.isPresent();
	}


	
}
