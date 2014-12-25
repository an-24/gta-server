package biz.gelicon.gta.server.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerController {

/*	
	public class ErrorInfo {
		private String message;

		public ErrorInfo(Exception ex) {
			this.message = ex.getMessage();
		}
	}
*/	
	@ExceptionHandler(Throwable.class)
	public ModelAndView handle(Throwable ex) {
        
       ModelAndView mav = new ModelAndView("exception");
       mav.addObject("name", ex.getClass().getName());
       mav.addObject("message", ex.getMessage());
       List<String> ls = new ArrayList<>();
       
       for(StackTraceElement th :ex.getStackTrace()) {
    	   ls.add("\""+th.toString()+"\"");
       }
       mav.addObject("stacktrace", Arrays.deepToString(ls.toArray()));

       return mav;
	}

}
