package biz.gelicon.gta.server.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.Post;
import biz.gelicon.gta.server.repo.PersonRepository;

@Service
public class PersonService {
	@Inject
	private PersonRepository personRepository;
	
	public void save(Person person) {
		Post post = person.getPostDict();
		Person man = null;
		if(post!=null && post.getId()==Post.MANAGERID) 
		if(person.getId()!=null) {
			man = personRepository.findByTeam_IdAndPostDict_IdAndIdNot(
					person.getTeam().getId(),
					Post.MANAGERID,person.getId());
		} else {
			man = personRepository.findByTeam_IdAndPostDict_Id(
					person.getTeam().getId(),Post.MANAGERID);
		}
		if(man!=null)
			throw new RuntimeException("Team already has a manager. This is "+man.getNic());
		personRepository.save(person);
	}

}
