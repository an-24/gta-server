package biz.gelicon.gta.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class GtaApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(PingSession.class);
        return classes;
    }
	
}
