package biz.gelicon.gta.server.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import biz.gelicon.gta.server.data.WeeklySignature;

public interface WeeklySignatureRepository extends JpaRepository<WeeklySignature, Integer> {
	
	public WeeklySignature findByDtDay(Date day);

}
