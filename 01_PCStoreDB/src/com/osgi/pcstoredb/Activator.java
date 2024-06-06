package com.osgi.pcstoredb;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration serviceRegistration;

	DbConnect connection = new DbConnectImpl();

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println(" Chamara PC Store Database Publisher Service Started ");
		DbConnect connection = new DbConnectImpl();
		serviceRegistration = bundleContext.registerService(DbConnect.class.getName(), connection, null);

	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println(" Chamara PC Store Database Publisher Service Stopped ");
		serviceRegistration.unregister();

	}

}
