package biz.gelicon.gta.server.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;

public interface PersonRepository extends JpaRepository<Person,Integer> {
	
	@Query("select p from Person p where p.user=?1 and p.team=?2")
	public Set<Person> findByUserAndTeam(User user,Team team);

	public Person findByUserAndTeamAndNic(User user, Team team,String nic);
	public Person findByTeam_IdAndNic(Integer id, String nic);
	public Person findByIdNotAndTeam_IdAndNic(Integer id, Integer teamId, String nic);
	public Person findByTeam_IdAndPostDict_IdAndIdNot(Integer teamId, int postId,Integer id);
	public Person findByTeam_IdAndPostDict_Id(Integer teamId, int postId);
	public List<Person> findByTeam_IdAndUser_Id(Integer teamId, Integer userId);
}
