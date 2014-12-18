package biz.gelicon.gta.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import biz.gelicon.gta.server.data.Person;

public interface PersonRepository extends JpaRepository<Person,Integer> {
}
