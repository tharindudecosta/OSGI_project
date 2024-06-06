package com.osgi.productmanagementpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private ServiceRegistration serviceRegistration;

	public void start(BundleContext bundleContext) throws Exception {

		System.out.println("Chamara PC Store Product Management Service Started.");

		ProductService productService = new ProductServiceImpl();

		serviceRegistration = bundleContext.registerService(ProductService.class.getName(), productService, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {

		System.out.println("Chamara PC Store Product Publisher Service Stopped.");

		serviceRegistration.unregister();

	}

}
