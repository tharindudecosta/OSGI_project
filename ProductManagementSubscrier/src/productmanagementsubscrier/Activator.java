package productmanagementsubscrier;

import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.osgi.productmanagementpublisher.ProductService;

public class Activator implements BundleActivator {

	ServiceReference serviceReference;
	Scanner sc = new Scanner(System.in);

	public void start(BundleContext bundleContext) throws Exception {

		System.out.println("Start Product Management Subscriber Service");

		serviceReference = bundleContext.getServiceReference(ProductService.class.getName());

		ProductService productService = (ProductService) bundleContext.getService(serviceReference);

		ProductDashBoard(productService);
	}

	public void stop(BundleContext bundleContext) throws Exception {

		System.out.println("Stop Product Management Subscriber Service");

		bundleContext.ungetService(serviceReference);
	}

	public void ProductDashBoard(ProductService productService) {

		Scanner sc = new Scanner(System.in);

		int userChoice;

		System.out.println("\n\n");
		System.out.println("==============Product Management Dashboard===============");
		System.out.println("1 => Add New Product");
		System.out.println("2 => Get All Product Details");
		System.out.println("3 => Get Product Details By Id");
		System.out.println("4 => Delete Product");
		System.out.println("5 => Generate Product Stock Report");
		System.out.println("Please Select Your Option");

		userChoice = Integer.parseInt(sc.nextLine().trim());

		switch (userChoice) {

		case 1:
			productService.addNewProduct();
			ProductDashBoard(productService);
			break;

		case 2:
			productService.getAllProductDetails();
			ProductDashBoard(productService);
			break;

		case 3:
			productService.getProductById();
			ProductDashBoard(productService);
			break;

		case 4:
			productService.deleteProduct();
			;
			ProductDashBoard(productService);
			break;

		case 5:
			productService.genarateProductReport();
			ProductDashBoard(productService);
			break;
		default:
			System.out.println("\n\nInvalid Option Selected. Please Try again.");
			ProductDashBoard(productService);
			break;
		}

	}

}
