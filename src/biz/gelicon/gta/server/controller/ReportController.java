package biz.gelicon.gta.server.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import biz.gelicon.gta.server.utils.DateUtils;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/inner/report")
public class ReportController {
	
	@Autowired
	private ApplicationContext appContext;

	
    @RequestMapping(value = "/r1", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public void getScreenshot(Model ui, HttpServletResponse response,
    		@RequestParam(required=true)  Integer teamId,
    		@RequestParam(required=true)  String dateStart,
    		@RequestParam(required=true)  String dateEnd) {
    	
    	try {
    		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			Date dtStart = formatter.parse(dateStart);
			Date dtEnd = DateUtils.getEndOfDay(formatter.parse(dateEnd));
/*			
			IReportEngine engine = BirtEngine.getInstance();
			IReportRunnable report = engine.openReportDesign(ClassLoader.getSystemResourceAsStream("biz/gelicon/gta/server/reports/r1.rptdesign"));
			IRunAndRenderTask task = engine.createRunAndRenderTask(report);
			
			IGetParameterDefinitionTask params = engine.createGetParameterDefinitionTask(report);
			HashMap<String,Object> allParams = params.getDefaultValues();
			allParams.put("teamId", teamId);
			allParams.put("dateBegin", dtStart);
			allParams.put("dateEnd", dtEnd);
			params.setParameterValues(allParams);
			
			task.getAppContext().put("spring", appContext);
			
			PDFRenderOption options = new PDFRenderOption();
			options.setOutputFormat("pdf");
			options.setOutputStream(response.getOutputStream());
			task.setRenderOption(options);
			
			task.run();
			task.close();
*/			
		} catch (Exception e) {
			throw new SpringException(e.getMessage());
		};
    	
/*    	
    	final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
    	return new ResponseEntity<byte[]>(ss.getScreenshot(), headers, HttpStatus.OK);
*/    	
    }
	
}
