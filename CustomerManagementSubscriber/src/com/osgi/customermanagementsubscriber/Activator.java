package com.osgi.customermanagementsubscriber;

import com.osgi.customermanagementpublisher.CustomerService;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	ServiceReference serviceReference;

	public void start(BundleContext bundleContext) throws Exception {

		System.out.println("Start Customer Subscriber Service");
		serviceReference = bundleContext.getServiceReference(CustomerService.class.getName());

		CustomerService customerService = (CustomerService) bundleContext.getService(serviceReference);
		CustomerDashboard(customerService);

	}

	public void stop(BundleContext bundleContext) throws Exception {

		System.out.println("Stop Customer subscriber Service");
		bundleContext.ungetService(serviceReference);

	}

	public void CustomerDashboard(CustomerService customerService) {

		Scanner sc = new Scanner(System.in);
		String selection = null;

		int customerDashboardChoice;
		String choice = "y";

		System.out.println("\n\n");
		System.out.println("==========================	User Dashboard	=======================");
		System.out.println("1 =>Customer Registration");
		System.out.println("2 =>View PC store catalog");
		System.out.println("3 =>Order Product");
		System.out.println("4 =>All Product Orders");
		System.out.println("5 =>Delete Customer");
		System.out.println("6 =>Generate Product order Report");

		System.out.println("Please Select Your Option");

		customerDashboardChoice = Integer.parseInt(sc.nextLine().trim());

		switch (customerDashboardChoice) {
		case 1:

			customerService.addNewCustomer();
			CustomerDashboard(customerService);

			break;
		case 2:
			customerService.viewAllProducts();
			CustomerDashboard(customerService);
			break;
		case 3:

			customerService.orderProduct();

			while (choice.equals("y")) {

				System.out.println("\nDo you want to buy another PC product:(y/n) ");
				choice = sc.nextLine().trim().toLowerCase();

				if (choice.equals("y")) {

					customerService.orderProduct();
				}
			}
			CustomerDashboard(customerService);

			break;
		case 4:
			customerService.viewAllOrders();
			CustomerDashboard(customerService);

			break;

		case 5:
			customerService.removeCustomer();
			CustomerDashboard(customerService);

			break;

		case 6:
			customerService.getOrderReport();
			CustomerDashboard(customerService);

			break;

		default:
			System.out.println("\n\nInvalid Option Selected. Please Try again.");
			CustomerDashboard(customerService);

			break;
		}

	}

}
