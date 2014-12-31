package biz.gelicon.gta.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import biz.gelicon.gta.server.data.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {

}
