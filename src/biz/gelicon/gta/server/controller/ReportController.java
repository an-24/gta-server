package biz.gelicon.gta.server.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.data.Message;
import biz.gelicon.gta.server.data.WeeklySignature;
import biz.gelicon.gta.server.repo.TeamRepository;
import biz.gelicon.gta.server.repo.WeeklySignatureRepository;
import biz.gelicon.gta.server.reports.WorkedOut;
import biz.gelicon.gta.server.service.ReportService;
import biz.gelicon.gta.server.utils.Base64;
import biz.gelicon.gta.server.utils.ByteArrayServletResponse;
import biz.gelicon.gta.server.utils.DateUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Controller
@RequestMapping("/inner/report")
public class ReportController {
	
	@Inject
	private InternalResourceViewResolver viewResolver;
	@Inject
	private TeamRepository teamRepository;
	@Inject
	private ReportService reportService;
	@Inject
	private WeeklySignatureRepository weeklySignatureRepository;
	@Inject
	private SignController signController;

	
    @RequestMapping(value = "/r1", method=RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly=true)
    public void getR1(HttpServletResponse response, HttpServletRequest request,
    		@RequestParam(required=true)  Integer teamId,
    		@RequestParam(required=true)  String dateStart,
    		@RequestParam(required=true)  String dateEnd,
    		@RequestParam(required=false) Integer base64encode,
    		@RequestParam(required=false) Integer signed) {
    	
    	try {
    		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			Date dtStart = formatter.parse(dateStart);
			Date dtEnd = DateUtils.getEndOfDay(formatter.parse(dateEnd));
			Document document = new Document(PageSize.A4);
			
			ByteArrayServletResponse enc64 = null;
			if(base64encode != null && base64encode.intValue()==1)
				enc64 = new ByteArrayServletResponse();
			
			// проверяем, есть ли подаисанный отчет
			WeeklySignature signature = weeklySignatureRepository.findByTeamAndDtDay(teamRepository.findOne(teamId),formatter.parse(dateEnd));
			if(signature!=null) {
				BufferedOutputStream buff = new BufferedOutputStream(enc64!=null?enc64.getOutputStream() :response.getOutputStream());
				buff.write(signature.getData());
				buff.flush();
				buff.close();
			} else {
				BufferedOutputStream buff = new BufferedOutputStream(enc64!=null?enc64.getOutputStream() :response.getOutputStream());
				
				PdfWriter writer = PdfWriter.getInstance(document,buff);
				document.open();
				
				boolean bsigned = signed!=null && signed.intValue()==1;
				InputStream html = getR1InputStream(request,teamId,dtStart,dtEnd,bsigned);
				
				XMLWorkerHelper gen = XMLWorkerHelper.getInstance();
				gen.parseXHtml(writer, document,html,
						getCSS(), Charset.forName("UTF-8"));
				
				if(bsigned) {
					Image img = Image.getInstance(signController.getStampPublicKey());
					img.setAbsolutePosition(document.getPageSize().getWidth()-250f, 30f);
					document.add(img);
				}
				
				document.close();
				buff.flush();
				buff.close();
			}
			
			if(enc64!=null) {
				response.getWriter().print(Base64.encode(enc64.toByteArray()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		};
    }
    
	private InputStream getR1InputStream(HttpServletRequest request,
			Integer teamId, Date dtStart, Date dtEnd, boolean signed) throws Exception {
		View view = viewResolver.resolveViewName("reports/r1", GtaSystem.getLocale());
    	ModelAndView mv = new ModelAndView(view);
    	mv.getModelMap().addAttribute("teamName", teamRepository.findOne(teamId).getName());
    	mv.getModelMap().addAttribute("dtStart", dtStart);
    	mv.getModelMap().addAttribute("dtEnd", dtEnd);
    	mv.getModelMap().addAttribute("signed",signed);
    	
    	List<WorkedOut> data = reportService.getWorkedOutData(teamId, dtStart, dtEnd);
    	mv.getModelMap().addAttribute("data", data);
    	
    	Map<String, List<WorkedOut>> mapPost = data.stream().collect(Collectors.groupingBy(WorkedOut::getPost));
    	ArrayList<WorkedOut> dataByPosition = new ArrayList<WorkedOut>();
    	mapPost.forEach((key,list)->{
    		WorkedOut wo = new WorkedOut();
    		wo.setPost(key);
    		wo.setHours(list.stream().collect(Collectors.summingDouble(WorkedOut::getHours)));
    		wo.setActivityScore(list.stream().collect(Collectors.summingDouble(WorkedOut::getActivityScore)));
    		wo.setActivityPercent(Message.getActivityPercent(wo.getHours(), wo.getActivityScore()));
    		dataByPosition.add(wo);
    	});
    	mv.getModelMap().addAttribute("dataByPosition", dataByPosition);
    	
    	ByteArrayServletResponse dest = new ByteArrayServletResponse();
    	mv.getView().render(mv.getModelMap(), request, dest);
    	
    	return new ByteArrayInputStream(dest.toByteArray());
		
	}
	
	private InputStream getCSS() throws UnsupportedEncodingException {
		String css =
				"body {"
				+ "font-family:Tahoma;"
				+ "color:black;"
				+ "}";
		return new ByteArrayInputStream(css.getBytes("UTF-8"));
	}
	
}
