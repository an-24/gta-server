package biz.gelicon.gta.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public List<User> findByNameLike(String substr);
	public User findByName(String name);
	
	@Query("select t from Team t,Person p where p.team=t and p.user.id=?1")
	public List<Team> findMemebers(Integer userId);
	public User findByIdNotAndName(Integer id, String name);


}
