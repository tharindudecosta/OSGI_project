package com.osgi.suppliermanagementsubscriber;

import com.osgi.suppliermanagementpublisher.SupplierService;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	ServiceReference serviceReference;

	public void start(BundleContext bundleContext) throws Exception {

		System.out.println("Start Supplier Subscriber Service");

		serviceReference = bundleContext.getServiceReference(SupplierService.class.getName());

		SupplierService supplierService = (SupplierService) bundleContext.getService(serviceReference);
		renderSupplierDashboard(supplierService);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stop Supplier Subscriber Service");
		bundleContext.ungetService(serviceReference);
	}

	private void renderSupplierDashboard(SupplierService supplierService) {
		Scanner sc = new Scanner(System.in);

		int supplierChoice;
		String choice = "y";

		System.out.println("\n\n");
		System.out.println("==============Supplier Dashboard===============");
		System.out.println("\n");
		System.out.println("1 => Supplier Registration");
		System.out.println("2 => Get All Supplier Details");
		System.out.println("3 => Get Supplier by Id");
		System.out.println("4 => Delete Supplier");
		System.out.println("5 => Get Supplier Report \n");

		System.out.println("6 => Place Stock Order");
		System.out.println("7 => Get Stock All Orders");
		System.out.println("8 => Get Stock Order by Order Id ");
		System.out.println("9 => Delete Stock Order ");
		System.out.println("10 => Get Stock order Report");

		System.out.println("Please Select Your Option");

		supplierChoice = Integer.parseInt(sc.nextLine().trim());

		switch (supplierChoice) {

		case 1:
			supplierService.addNewSupplier();
			renderSupplierDashboard(supplierService);
			break;

		case 2:
			supplierService.getAllSupplierDetails();
			renderSupplierDashboard(supplierService);
			break;

		case 3:

			supplierService.getSupplierById();
			renderSupplierDashboard(supplierService);

			break;

		case 4:
			supplierService.deleteSupplier();
			renderSupplierDashboard(supplierService);

			break;
		case 5:

			supplierService.getsupplierReport();
			renderSupplierDashboard(supplierService);
			break;

		case 6:
			supplierService.stockOrder();
			while (choice.equals("y")) {
				System.out.printf("\nDo You Want To Add Another Stock Order(y/n) ");
				choice = sc.nextLine().trim().toLowerCase();
				if (choice.equals("y")) {
					supplierService.stockOrder();
				}
			}
			renderSupplierDashboard(supplierService);
			break;

		case 7:

			supplierService.getAllStockOrders();
			renderSupplierDashboard(supplierService);
			break;

		case 8:

			supplierService.getStockOrderById();
			renderSupplierDashboard(supplierService);

			break;

		case 9:

			supplierService.deleteStockOrder();
			renderSupplierDashboard(supplierService);

			break;

		case 10:

			supplierService.getstockOrderReport();
			renderSupplierDashboard(supplierService);
			break;
		default:

			System.out.println("\n\nInvalid Option Selected. Please Try again.");
			renderSupplierDashboard(supplierService);
			break;
		}
	}
}