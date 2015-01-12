package biz.gelicon.gta.server.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import biz.gelicon.gta.server.utils.DateUtils;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/inner/report")
public class ReportController {
	
	@Autowired
	private ApplicationContext appContext;

	
    @RequestMapping(value = "/r1", method=RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly=true)
    public void getR1(HttpServletResponse response,
    		@RequestParam(required=true)  Integer teamId,
    		@RequestParam(required=true)  String dateStart,
    		@RequestParam(required=true)  String dateEnd) {
    	
    	try {
    		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
			Date dtStart = formatter.parse(dateStart);
			Date dtEnd = DateUtils.getEndOfDay(formatter.parse(dateEnd));
			
			Document document = new Document();
			BufferedOutputStream buff = new BufferedOutputStream(response.getOutputStream());
			PdfWriter writer = PdfWriter.getInstance(document,buff);
			document.open();
			//BaseFont f = BaseFont.createFont(BaseFont.HELVETICA,BaseFont.CP1252, BaseFont.EMBEDDED);
			//writer.getDirectContent().setFontAndSize(f,18f);
			XMLWorkerHelper gen = XMLWorkerHelper.getInstance();
			gen.parseXHtml(writer, document,
					getR1InputStream(teamId,dtStart,dtEnd),
					getCSS());
			document.close();
			buff.flush();
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
			//throw new SpringException(e.getMessage());
		};
    }


	private InputStream getR1InputStream(Integer teamId, Date dtStart, Date dtEnd) throws UnsupportedEncodingException {
		StringBuffer html = new StringBuffer();
		html.append("<html>")
			.append("<head>")
			.append("</head>")
			.append("<body>")
			.append("<h1>").append("Worked out on period.  Выработка").append("</h1>")
			.append("</body></html>");
		return new ByteArrayInputStream(html.toString().getBytes("UTF-8"));
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
