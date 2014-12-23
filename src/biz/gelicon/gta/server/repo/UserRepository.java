package biz.gelicon.gta.server.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import biz.gelicon.gta.server.data.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public List<User> findByNameLike(String substr);

}
