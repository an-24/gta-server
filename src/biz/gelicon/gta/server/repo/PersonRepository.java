package biz.gelicon.gta.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;

public interface PersonRepository extends JpaRepository<Person,Integer> {
	
	@Query("select p from Person p where p.user=?1 and p.team=?2")
	public List<Person> findByUserAndTeam(User user,Team team);

	public Person findByUserAndTeamAndNic(User user, Team team,String nic);
	public Person findByTeam_IdAndNic(Integer id, String nic);
	public Person findByIdNotAndTeam_IdAndNic(Integer id, Integer teamId, String nic);
}
