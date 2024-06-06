package com.osgi.customermanagementpublisher;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.osgi.pcstoredb.DbConnect;
import com.osgi.pcstoredb.DbConnectImpl;

public class CustomerServiceImpl implements CustomerService {

	private Connection connection = null;
	private Statement statement;
	private ResultSet resultSet;
	private DbConnect dbContext;
	private static PreparedStatement preparedStatement = null;
	Scanner sc = new Scanner(System.in);

	public CustomerServiceImpl() {
		super();
		this.dbContext = new DbConnectImpl();
		this.connection = dbContext.getDatabaseConnection();
	}

	@Override
	public void addNewCustomer() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

		CustomerModel customerModel = new CustomerModel();

		System.out.println("\n\n");
		System.out.println(
				"=========================================================================================================");
		System.out.println(
				"================================	Chamara PC Store Customer SignUp	=================================");
		System.out.println(
				"=========================================================================================================");
		System.out.println("\n\n");

		System.out.println("Enter Your First Name ::");
		customerModel.setFirstName(sc.nextLine().trim());

		System.out.println("Enter Your Last Name ::");
		customerModel.setLastName(sc.nextLine().trim());

		System.out.println("Enter Your Email ::");
		customerModel.setEmail(sc.nextLine().trim());

		System.out.println("Enter Your Phone Number ::");
		customerModel.setPhone(sc.nextLine().trim());

		System.out.println("Enter Your Password ::");
		customerModel.setPassword(sc.nextLine().trim());

		try {

			String query = "insert into customer(firstName,lastName,email,phone,password,isActive) values(?,?,?,?,?,'1') ";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, customerModel.getFirstName());
			preparedStatement.setString(2, customerModel.getLastName());
			preparedStatement.setString(3, customerModel.getEmail());
			preparedStatement.setString(4, customerModel.getPhone());
			preparedStatement.setString(5, customerModel.getPassword());

			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {

				System.out.println("Customer Signup Has Been Successful");

			} else {

				System.out.println("An error orccured. Please try again.");

			}

		} catch (Exception ex) {

			System.out.println("Customer Signup error : " + ex.getMessage());
		}

	}

	// view all products
	@Override
	public void viewAllProducts() {

		try {

			String query = "SELECT productId, productName, brand, unitPrice FROM products WHERE isActive =1";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\n\n");
			System.out.println(
					"=======================================================================================================");
			System.out.println(
					"================================	Chamara PC Store catalog	=======================================");
			System.out.println(
					"=======================================================================================================");
			System.out.println("\n\n");

			System.out.println(String.format("%20s %20s %20s %20s\n", "ProdcutId", "Prodcut Name", "Brand", "Price"));

			System.out.println(
					"--------------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {

				System.out.printf("%20d %20s %20s %20s\n", resultSet.getInt("productId"),
						resultSet.getString("productName"), resultSet.getString("brand"),
						resultSet.getString("unitPrice"));
				System.out.println(
						"--------------------------------------------------------------------------------------------------------");
			}

		} catch (Exception ex) {

			System.out.println("Error Occured when retrieving catalog :" + ex.getMessage());

		}
	}

	@Override
	public void orderProduct() {
		ProductOrderModel order = new ProductOrderModel();
		Scanner orderSc = new Scanner(System.in);

		Integer productId;

		System.out
				.println("*****************************	Place Your Order Here	***********************************");

		System.out.println("Enter Your Delivery Address ::");
		order.setDeliveryAddress(orderSc.nextLine());
		System.out.println("Please Enter product ID: ");
		productId = (orderSc.nextInt());
		
		System.out.println("\n\n Processing order.......\n\n");

		try {

			String query = "SELECT productId,productName,brand,unitPrice FROM products WHERE productId = '" + productId
					+ "' && isActive = 1";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("======================	Your Order is ready	==========================");

			if (resultSet.isBeforeFirst()) {

				while (resultSet.next()) {

					System.out.println("Product ID :" + resultSet.getInt("productId"));
					System.out.println("Product Name :" + resultSet.getString("productName"));
					System.out.println("Brand :" + resultSet.getString("brand"));
					System.out.println("Price :" + resultSet.getString("unitPrice"));

					order.setPrice(resultSet.getString("unitPrice"));
					order.setProductName(resultSet.getString("productName"));
					order.setProductId(resultSet.getInt("productId"));

				}
				setOrder(order);

			} else {
				System.out.println("Please select a product that is active.");
			}

			System.out.println("==================================================================\n\n");
		} catch (Exception ex) {

			System.out.println("Error Occured when ordering product : " + ex.getMessage());

		}

	}

	public void setOrder(ProductOrderModel order) {

		String query = "insert into productOrders(productId,productName,deliveryaddress,price,isActive)values(?,?,?,?,'1')";
		try {

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, order.getProductId());
			preparedStatement.setString(2, order.getProductName());
			preparedStatement.setString(3, order.getDeliveryaddress());
			preparedStatement.setString(4, order.getPrice());

			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {

				System.out.println("Your order has been placed successful. Thank you!! ");

			} else {

				System.out.println("Error has been occured please try again");

			}

		} catch (Exception ex) {

			System.out.println("Error Occured when ordering product : " + ex.getMessage());

		}

	}

	@Override
	public void viewAllOrders() {
		try {

			String query = "SELECT orderId, productId,productName ,deliveryAddress, price FROM productOrders";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\n\n");
			System.out.println(
					"=======================================================================================================");
			System.out
					.println("================================	All orders	=======================================");
			System.out.println(
					"=======================================================================================================");
			System.out.println("\n\n");

			System.out.println(String.format("%30s %30s %30s %30s %30s\n", "Order Id", "product Id", "Product Name",
					"Address", "Price list"));

			System.out.println(
					"----------------------------------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {

				System.out.printf("%30d %30d %30s %30s %30s \n", resultSet.getInt("orderId"), resultSet.getInt("productId"),
						resultSet.getString("productName"), resultSet.getString("deliveryAddress"),
						resultSet.getString("price"));
				System.out.println(
						"=================================================================================================================================");
			}

		} catch (Exception ex) {

			System.out.println("Error Occured when retrieving catalog :" + ex.getMessage());

		}
	}

	@Override
	public void removeCustomer() {

		Integer id;

		System.out.print("\nPlease enter Customer id : ");
		id = (sc.nextInt());

		try {

			String query = "UPDATE customer SET isActive = 0 WHERE customerId = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id);

			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {

				System.out.println("Delete Customer has been successful..");

			} else {

				System.out.println("Please enter valid customer Id.");

			}

		} catch (Exception ex) {

			System.out.println("Customer Delete error : " + ex.getMessage());

		}

	}

	@Override
	public void getOrderReport() {
		try {

			String query = "SELECT orderId, productId,productName ,deliveryAddress, price FROM productOrders";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			File directory = new File("C:\\Users\\Tharindu De Costa\\Desktop\\OSGiReports");

			directory.mkdirs();

			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
			
			File file = new File(directory, "OrderProductReport-"+dateFormat.format(date)+".txt");
			FileWriter fileWriter = new FileWriter(file);

			fileWriter.write(String.format(
					"====================================================== Order Report =============================================================================================================\n\n"));
			fileWriter.write(

					String.format("%30s %30s %30s %30s %30s\n", "Order Id", "product Id", "Product Name", "Address",
							"Price list"));

			fileWriter.write(String.format(
					"=================================================================================================================================================================================\n\n"));

			while (resultSet.next()) {

				fileWriter.write(

						String.format(

								"%30d %30d %30s %30s %30s \n", resultSet.getInt("orderId"), resultSet.getInt("productId"),
								resultSet.getString("productName"), resultSet.getString("deliveryAddress"),
								resultSet.getString("price")

						));

				fileWriter.write(String.format(
						"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));
			}

			fileWriter.flush();
			fileWriter.close();

			System.out.println("Report genaration has been successful");

		} catch (Exception ex) {

			System.out.println("Order book report error :" + ex.getMessage());

		}
	}

}
