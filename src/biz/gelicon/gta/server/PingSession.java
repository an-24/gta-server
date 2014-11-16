package biz.gelicon.gta.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PingSession {
	
	@GET
	@Path("{token}")
	public boolean pingGET(@PathParam("token") String token) {
		return ping(token);
	}

	@POST
	public boolean pingPOST(String token) {
		return ping(token);
	}
	
	private boolean ping(String token) {
		return Sessions.findSession(token) != null;
	}

}
