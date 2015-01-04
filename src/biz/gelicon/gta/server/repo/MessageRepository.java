package biz.gelicon.gta.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import biz.gelicon.gta.server.data.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
