package biz.gelicon.gta.server.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import biz.gelicon.gta.server.dto.TeamDTO;
import biz.gelicon.gta.server.repo.TeamRepository;

@Controller
@RequestMapping("/inner/diary")
public class WorkDiaryController {
	@Inject
	private TeamRepository teamRepository;

    @RequestMapping(method=RequestMethod.GET)
    @Transactional
    public String select(Model ui) {
    	List<TeamDTO> list = teamRepository.findAll().stream().map(t->{
    		TeamDTO dto = new TeamDTO(t);
    		dto.setWorkerCount(t.getPersons().size());
    		return dto;
    	}).collect(Collectors.toList());
    	ui.addAttribute("teams", list);
        return "inner/diary/select";
    }

    @RequestMapping(value = "/{id}", method=RequestMethod.GET, produces = "application/json")
    public String main(Model ui, @PathVariable Integer id) {
        return "inner/diary/calendar";
    }
    
}
