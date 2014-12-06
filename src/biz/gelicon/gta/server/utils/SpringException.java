package biz.gelicon.gta.server.utils;

public class SpringException extends RuntimeException{
	private static final long serialVersionUID = -1711782923110665694L;
	
	private String exceptionMsg;
 
	public SpringException(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	public String getExceptionMsg(){
		return this.exceptionMsg;
	}
	
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}