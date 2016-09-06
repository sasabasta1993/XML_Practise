package rs.ac.uns.ftn.xws.util;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

@ApplicationPath("/api")
public class XWSApplication extends Application {
	private static Logger log = Logger.getLogger(XWSApplication.class);
	
    @SuppressWarnings("rawtypes")
	@Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register root resource
		/*try {
			for (Class clazz : ClassUtils.getClasses()) {
			    classes.add(clazz);
			}
		} catch (ClassNotFoundException | IOException e) {
			log.error(e.getMessage(), e);
		}*/

        return classes;
    }    
   
}
