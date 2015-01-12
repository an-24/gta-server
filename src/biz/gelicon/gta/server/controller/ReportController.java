package biz.gelicon.gta.server.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
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
import biz.gelicon.gta.server.controller.MainController.UserInput;
import biz.gelicon.gta.server.utils.DateUtils;
import biz.gelicon.gta.server.utils.HttpServletResponseStub;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.FontFactoryImp;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Controller
@RequestMapping("/inner/report")
public class ReportController {
	
	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private ServletContext servletContext;
	@Inject
	private InternalResourceViewResolver viewResolver;

	
    @RequestMapping(value = "/r1", method=RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly=true)
    public void getR1(HttpServletResponse response, HttpServletRequest request,
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
			XMLWorkerHelper gen = XMLWorkerHelper.getInstance();
			gen.parseXHtml(writer, document,
					getR1InputStream(response,request,teamId,dtStart,dtEnd),
					getCSS(), Charset.forName("UTF-8"));
			document.close();
			buff.flush();
			buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		};
    }
    
	private InputStream getR1InputStream(HttpServletResponse response, HttpServletRequest request,
			Integer teamId, Date dtStart, Date dtEnd) throws Exception {
		View view = viewResolver.resolveViewName("reports/r1", GtaSystem.getLocale());
    	ModelAndView mv = new ModelAndView(view);
    	mv.getModelMap().addAttribute("teamId", teamId);
    	mv.getModelMap().addAttribute("dtStart", dtStart);
    	mv.getModelMap().addAttribute("dtEnd", dtEnd);
    	
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
	
	class ByteArrayServletResponse extends HttpServletResponseStub  {
		private ServletOutputStreamImpl stream;
		private PrintWriter writer;
		private ServletOutputStreamImpl writerStream;

		public ByteArrayServletResponse() {
		}
		
		@Override
		public ServletOutputStream getOutputStream() throws java.io.IOException {
			if(stream==null) {
				
	            if (writer != null)
	                throw new IllegalStateException(
	                        "getWriter() has already been called for this response.");
				
				stream = new ServletOutputStreamImpl();
			}
			return stream;
		}
		
		@Override
		public PrintWriter getWriter() throws IOException {
	        if (writer == null) {

	            if (stream != null)
	                throw new IllegalStateException(
	                        "getOutputStream() has already been called for this response.");

	            writerStream =  new ServletOutputStreamImpl();
	            writer = new PrintWriter(new OutputStreamWriter(writerStream, "UTF-8"));
	            
	        }
	        return writer;
		}
		
		
	    public byte[] toByteArray() {
	        return stream!=null?stream.toByteArray():writerStream.toByteArray();
	    }

	    
	}
	class ServletOutputStreamImpl extends ServletOutputStream {

	    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

	    ServletOutputStreamImpl() {
	    }

	    public byte[] toByteArray() {
	        return out.toByteArray();
	    }

		@Override
	    public void write(int b) throws IOException {
	        out.write(b);
	    }

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener l) {
		}

	}
	
}
