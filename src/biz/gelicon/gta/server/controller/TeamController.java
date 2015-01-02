package biz.gelicon.gta.server.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.dto.PersonDTO;
import biz.gelicon.gta.server.dto.TeamDTO;
import biz.gelicon.gta.server.repo.TeamRepository;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/inner/team")
public class TeamController {
	
	@Inject
	private TeamRepository teamRepository;
	
    @RequestMapping(value = "/{id}", method=RequestMethod.GET, produces = "application/json")
    @Transactional(readOnly=true)
    @ResponseBody
    public TeamDTO getTeam(@PathVariable Integer id) {
    	Team team = teamRepository.findOne(id);
    	List<PersonDTO> persons = team.getPersons().stream()
    			.map(p->new PersonDTO(p,0)).collect(Collectors.toList());
    	TeamDTO dto = new TeamDTO(team);
    	dto.setPersons(persons);
    	dto.setManagerCurrentUser(team.isManagerCurrentUser());
    	return dto;
    }
	
	@RequestMapping(value = "/update", method=RequestMethod.POST, produces = "application/json")
 	@ResponseBody
 	@Transactional
	public String updateTeam(Model ui, Integer id,Integer  limit) {
		Team team = teamRepository.findOne(id);
		if(limit!=null) checkLimit(team,limit);
		team.setLimit(limit);
		teamRepository.save(team);
		String result = "Team successfully changed";
		return "{\"message\":\""+result+"\"}";
	}

	private void checkLimit(Team team, int limit) {
		int commonlimit = team.getPersons()
				.stream().mapToInt(t -> t.getLimit()!=null?t.getLimit():0).sum();
		if(commonlimit>limit)
			throw new SpringException("The total limit of members more than the limit set by"); 
	}

}
