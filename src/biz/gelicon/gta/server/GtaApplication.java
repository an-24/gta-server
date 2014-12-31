package biz.gelicon.gta.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

@ApplicationPath("/gta-server")
public class GtaApplication extends javax.ws.rs.core.Application {
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(Login.class);
        set.add(Teams.class);
        set.add(PingSession.class);
        set.add(MultiPartFeature.class);
        set.add(Timing.class);
        return set;
    }
}
