package com.osgi.customermanagementpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private ServiceRegistration serviceRegistration;

	public void start(BundleContext bundleContext) throws Exception {

		System.out.println("Chamara PC Store Customer Publisher Service Started.");

		CustomerService customerService = new CustomerServiceImpl();

		serviceRegistration = bundleContext.registerService(CustomerService.class.getName(), customerService, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {

		System.out.println("Chamara PC Store Customer Publisher Service Stopped.");

		serviceRegistration.unregister();
	}

}
