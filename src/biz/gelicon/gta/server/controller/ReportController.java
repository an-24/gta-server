package biz.gelicon.gta.server.controller;

import java.io.BufferedOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.IGetParameterDefinitionTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import biz.gelicon.gta.server.data.ScreenShot;
import biz.gelicon.gta.server.reports.BirtEngine;
import biz.gelicon.gta.server.utils.DateUtils;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/inner/report")
public class ReportController {

	
    @RequestMapping(value = "/r1", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public void getScreenshot(Model ui, HttpServletResponse response,
    		@RequestParam(required=true)  Integer teamid,
    		@RequestParam(required=true)  String dateStart,
    		@RequestParam(required=true)  String dateEnd) {
    	
    	try {
    		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			Date dtStart = formatter.parse(dateStart);
			Date dtEnd = DateUtils.getEndOfDay(formatter.parse(dateEnd));
			
			IReportEngine engine = BirtEngine.getInstance();
			IReportRunnable report = engine.openReportDesign(ClassLoader.getSystemResourceAsStream("biz/gelicon/gta/server/reports/r1.rptdesign"));
			IRunAndRenderTask task = engine.createRunAndRenderTask(report);
			
			IGetParameterDefinitionTask params = engine.createGetParameterDefinitionTask(report);
			HashMap<String,Object> allParams = params.getDefaultValues();
			allParams.put("teamId", teamid);
			allParams.put("dateBegin", dtStart);
			allParams.put("dateEnd", dtEnd);
			params.setParameterValues(allParams);
			
			PDFRenderOption options = new PDFRenderOption();
			options.setOutputFormat("pdf");
			options.setOutputStream(response.getOutputStream());
			task.setRenderOption(options);
			
			task.run();
			task.close();
			
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
