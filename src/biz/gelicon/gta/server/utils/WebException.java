package biz.gelicon.gta.server.utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class WebException extends WebApplicationException {
	private static final long serialVersionUID = -8843653103383124379L;

	public WebException(String message) {
		super(Response.status(Response.Status.BAD_REQUEST)
	             .entity(message).type(MediaType.TEXT_PLAIN).build());
	}

}
