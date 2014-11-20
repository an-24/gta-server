package biz.gelicon.gta.server;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import biz.gelicon.gta.server.data.Message;

@Path("/timing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Timing {
	final private static Logger log = Logger.getLogger("gta");
	

	@POST
	public Integer push(String token, Message message) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
