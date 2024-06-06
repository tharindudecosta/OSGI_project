package com.osgi.suppliermanagementpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private ServiceRegistration serviceRegistration;

	public void start(BundleContext bundleContext) throws Exception {

		System.out.println("Chamara PC Store Supplier Publisher Service Started.");

		SupplierService supplierService = new SupplierServiceImpl();

		serviceRegistration = bundleContext.registerService(SupplierService.class.getName(), supplierService, null);

	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Chamara PC Store Supplier Publisher Service Stopped.");
		serviceRegistration.unregister();
	}

}
