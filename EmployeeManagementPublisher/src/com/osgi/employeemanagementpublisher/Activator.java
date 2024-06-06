package com.osgi.employeemanagementpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private ServiceRegistration serviceRegistration;

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Chamara PC Store Employee Publisher Service Started.");

		EmployeeService employeeService = new EmployeeServiceImpl();

		serviceRegistration = bundleContext.registerService(EmployeeService.class.getName(), employeeService, null);

	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Chamara PC Store Employee Publisher Service Stopped.");

		serviceRegistration.unregister();

	}

}
