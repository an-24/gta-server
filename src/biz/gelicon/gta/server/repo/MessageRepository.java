package biz.gelicon.gta.server.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import biz.gelicon.gta.server.data.Message;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	public List<Message> findByTeamAndDtBeginBetween(Team team,Date begin,Date finish);
	public List<Message> findByTeamAndUserAndDtBeginBetween(Team team,User user,Date begin,Date finish);
}
