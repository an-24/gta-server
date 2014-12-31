package biz.gelicon.gta.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import biz.gelicon.gta.server.data.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {

	public Team findByName(String name);
	public List<Team> findByNameLike(String name);
	public Team findByIdNotAndName(Integer id, String name);

}
