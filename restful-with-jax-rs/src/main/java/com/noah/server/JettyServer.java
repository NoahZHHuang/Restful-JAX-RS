package com.noah.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.servlet.ServletContainer;

import com.noah.server.filter.RequestFilterBasicAuth;
import com.noah.server.filter.RequestFilterHigerPriority;
import com.noah.server.filter.RequestFilterLowerPriority;
import com.noah.server.filter.ResponseFilterCSRF;

public class JettyServer {
	
	private static Server server;
	
	public static void start() throws Exception{
		
		ResourceConfig configUnsecure = new ResourceConfig();
		configUnsecure.packages("com.noah.server.service.unsecure");
		configUnsecure.register(RequestFilterLowerPriority.class);
		configUnsecure.register(RequestFilterHigerPriority.class);
		configUnsecure.register(ResponseFilterCSRF.class);
		
		ResourceConfig configSecure = new ResourceConfig();
		configSecure.packages("com.noah.server.service.secure");
		configSecure.register(RequestFilterLowerPriority.class);
		configSecure.register(RequestFilterHigerPriority.class);
		configSecure.register(ResponseFilterCSRF.class);
		configSecure.register(RequestFilterBasicAuth.class);
		configSecure.register(RolesAllowedDynamicFeature.class);
		
		ServletHolder servletHolderUnsecure = new ServletHolder(new ServletContainer(configUnsecure));
		ServletHolder servletHolderSecure = new ServletHolder(new ServletContainer(configSecure));
		
		server = new Server(8080);
		ServletContextHandler contextHandler = new ServletContextHandler(server,"/");
		contextHandler.addServlet(servletHolderUnsecure, "/unsecure/book/*");
		contextHandler.addServlet(servletHolderSecure, "/secure/book/*");
		
		server.start();
		
		System.out.println("Jetty Server Stat Up Successfully !!!");
	}
	
	public static void stop() throws Exception{
		if(server!=null){
			server.stop();
		}
	}

}
