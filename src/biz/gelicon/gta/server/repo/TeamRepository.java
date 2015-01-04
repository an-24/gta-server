package biz.gelicon.gta.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;

public interface TeamRepository extends JpaRepository<Team, Integer> {

	public Team findByName(String name);
	public List<Team> findByNameLike(String name);
	public Team findByIdNotAndName(Integer id, String name);
	@Query("select distinct t from Team t, Person p where p.team.id=t.id AND t.active=true AND p.user.id=?1")
	public List<Team> findByUser_Id(Integer userId);

}
