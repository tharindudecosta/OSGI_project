package com.osgi.employeemanagementsubscriber;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.osgi.employeemanagementpublisher.EmployeeService;

public class Activator implements BundleActivator {

	ServiceReference serviceReference;

	public void start(BundleContext bundleContext) throws Exception {

		System.out.println("Start Employee Subscriber Service");

		serviceReference = bundleContext.getServiceReference(EmployeeService.class.getName());

		EmployeeService employeeService = (EmployeeService) bundleContext.getService(serviceReference);
		EmployeeDashboard(employeeService);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Stop Employee subscriber Service");
		bundleContext.ungetService(serviceReference);
	}

	public void EmployeeDashboard(EmployeeService employeeService) {

		Scanner sc = new Scanner(System.in);

		int EmployeeDashboardChoice;
		String choice = "y";

		System.out.println("\n\n");
		System.out.println("==============Chamara PC store Employee Dashboard===============");
		System.out.println("1 => Employee Registration");
		System.out.println("2 => Get All Employee Details");
		System.out.println("3 => Get Employee By Id");
		System.out.println("4 => Delete Employee");
		System.out.println("5 => Get Employee Report");

		System.out.println("Please Select Your Option");

		EmployeeDashboardChoice = Integer.parseInt(sc.nextLine().trim());

		switch (EmployeeDashboardChoice) {
		case 1:

			employeeService.addNewEmployee();
			EmployeeDashboard(employeeService);
			break;

		case 2:
			employeeService.getAllEmployeeDetails();
			EmployeeDashboard(employeeService);
			break;

		case 3:
			employeeService.getEmployeeById();
			EmployeeDashboard(employeeService);
			break;

		case 4:
			employeeService.deleteEmployee();
			EmployeeDashboard(employeeService);
			break;

		case 5:
			employeeService.getEmployeeReport();
			EmployeeDashboard(employeeService);
			break;

		default:
			System.out.println("\n\nInvalid Option Selected. Please Try again.");
			EmployeeDashboard(employeeService);
			break;
		}

	}
}
