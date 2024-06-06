package com.osgi.suppliermanagementpublisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

import com.osgi.pcstoredb.DbConnect;
import com.osgi.pcstoredb.DbConnectImpl;
import com.osgi.suppliermanagementpublisher.SupplierModel;

public class SupplierServiceImpl implements SupplierService {

	private Connection connection = null;
	private Statement statement;
	private ResultSet resultSet;
	private DbConnect dbContext;
	private static PreparedStatement preparedStatement = null;

	Scanner sc = new Scanner(System.in);

	public SupplierServiceImpl() {
		super();
		this.dbContext = new DbConnectImpl();
		this.connection = dbContext.getDatabaseConnection();
	}

	@Override
	public void addNewSupplier() {

		Scanner sc = new Scanner(System.in);

		SupplierModel supplierModel = new SupplierModel();

		System.out.println("\n\n");
		System.out.println(
				"=========================================================================================================");
		System.out.println(
				"===============================	Chamara PC Store Supplier register 	==================================");
		System.out.println(
				"=========================================================================================================");
		System.out.println("\n\n");

		System.out.println("Enter First Name ::");
		supplierModel.setFirstName(sc.nextLine().trim());

		System.out.println("Enter Last Name ::");
		supplierModel.setLastName(sc.nextLine().trim());

		System.out.println("Enter Email ::");
		supplierModel.setEmail(sc.nextLine().trim());

		System.out.println("Enter NIC ::");
		supplierModel.setNic(sc.nextLine().trim());

		System.out.println("Enter Mobile Number ::");
		supplierModel.setPhone(sc.nextLine().trim());

		System.out.println("Enter Company Name ::");
		supplierModel.setCompanyName(sc.nextLine().trim());

		try {

			String query = "INSERT INTO supplier(firstName,lastName,email,nic,phone,companyName,isActive) VALUES(?, ?, ?, ?, ?, ?, '1')";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, supplierModel.getFirstName());
			preparedStatement.setString(2, supplierModel.getLastName());
			preparedStatement.setString(3, supplierModel.getEmail());
			preparedStatement.setString(4, supplierModel.getNic());
			preparedStatement.setString(5, supplierModel.getPhone());
			preparedStatement.setString(6, supplierModel.getCompanyName());

			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {
				System.out.println("Supplier Registration is Successful");
			} else {
				System.out.println("Error has been orccured please try again..");
			}
		} catch (Exception ex) {

			System.out.println("supplierSaveError : " + ex.getMessage());
		}
	}

	@Override
	public void getAllSupplierDetails() {

		try {

			String query = "SELECT supplierId, firstName, lastName, email, nic, phone , companyName FROM supplier WHERE isActive =1 ";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\n\n");
			System.out.println(
					"=========================================================================================================");
			System.out.println(
					"===============================	Chamara PC Store Supplier Roster	==================================");
			System.out.println(
					"=========================================================================================================");
			System.out.println("\n\n");
			System.out.println(String.format("%20s %20s %20s %30s %20s %20s %20s\n", "SupplierId", "First Name",
					"Last Name", "Email", "NIC", "Mobile Number", "CompanyName"));

			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {

				System.out.printf("%20d %20s %20s %30s %20s %20s %20s\n", resultSet.getInt("supplierId"),
						resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"),
						resultSet.getString("nic"), resultSet.getString("phone"), resultSet.getString("companyName")

				);

				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}

		} catch (Exception ex) {

			System.out.println("getAllSupplierDetailsException:" + ex.getMessage());

		}

	}

	@Override
	public void deleteSupplier() {

		Integer supplierId;

		System.out.print("\nPlease enter Supplier supplierId : ");
		supplierId = (sc.nextInt());

		try {
			String query = "UPDATE supplier SET isActive = 0 WHERE supplierId = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, supplierId);
			int isSuccess = preparedStatement.executeUpdate();
			if (isSuccess > 0) {
				System.out.println("Delete supplier has been successfully..");
			} else {

				System.out.println("Cannot find supplier..");
			}
		} catch (Exception ex) {
			System.out.println("supplierDeleteException : " + ex.getMessage());
		}
	}

	@Override
	public void getSupplierById() {

		Integer supplierId;

		System.out.println("Enter Supplier Id : ");
		supplierId = (sc.nextInt());
		String query = "SELECT * FROM supplier WHERE supplierId = " + supplierId ;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				System.out.println("\nSuplier Id :	" + resultSet.getInt("supplierId"));
				System.out.println("First name :	" + resultSet.getString("firstName"));
				System.out.println("Last name :	" + resultSet.getString("lastName"));
				System.out.println("Email :		" + resultSet.getString("email"));
				System.out.println("Nic	:	" + resultSet.getString("nic"));
				System.out.println("Phone :		" + resultSet.getString("phone"));
				System.out.println("Company name :	" + resultSet.getString("companyName"));
				System.out.println("Is Active :	" + resultSet.getBoolean("isActive"));


			}
		} catch (Exception ex) {
			System.out.println("Error has been occured please try again");
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void stockOrder() {

		Scanner sc = new Scanner(System.in);

		StockOrderModel stockOrderModel = new StockOrderModel();

		System.out.println("\n\n");
		System.out.println(
				"=========================================================================================================");
		System.out.println(
				"========================================	Create Stock Order	==========================================");
		System.out.println(
				"=========================================================================================================");
		System.out.println("\n\n");

		System.out.println("Please Enter Product Name :");
		stockOrderModel.setProductName(sc.nextLine().trim());
		
		System.out.println("Please Enter Stock amount :");
		stockOrderModel.setNoOfStocks(sc.nextLine().trim());

		System.out.println("Please Enter Company Name :");
		stockOrderModel.setSupplierCompany(sc.nextLine().trim());

		try {

			String query = "INSERT INTO stockOrder(noOfStocks,productName,supplierCompany,isActive) VALUES(?, ?, ?, '1')";
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, stockOrderModel.getNoOfStocks());
			preparedStatement.setString(2, stockOrderModel.getProductName());
			preparedStatement.setString(3, stockOrderModel.getSupplierCompany());

			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {
				System.out.println("Stock Order placed successfully");
			} else {
				System.out.println("Error has been orccured please try again");
			}
		} catch (Exception ex) {
			System.out.println("stockOrder : " + ex.getMessage());
		}
	}

	@Override
	public void getAllStockOrders() {

		try {
			String query = "SELECT stockOrderId, noOfStocks , productName , supplierCompany  FROM stockOrder WHERE isActive = 1 ";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\n\n");
			System.out.println(
					"=========================================================================================================");
			System.out.println(
					"===========================================	All Stock Orders	======================================");
			System.out.println(
					"=========================================================================================================");
			System.out.println("\n\n");
			System.out.println(String.format("%20s %20s %20s %25s\n", "StockOrderId", "No Of Stocks", "Product Name",
					"Supplier Company"));

			System.out.println(
					"-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (resultSet.next()) {
				System.out.printf("%20d %20s %20s %25s\n", resultSet.getInt("stockOrderId"),
						resultSet.getString("noOfStocks"), resultSet.getString("ProductName"),
						resultSet.getString("supplierCompany"));
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			}

		} catch (Exception ex) {

			System.out.println("getAllStocksOrderDetailsException:" + ex.getMessage());
		}

	}

	@Override
	public void deleteStockOrder() {

		Integer stockOrderId;
		System.out.print("\nPlease enter Stock Order stockOrderId : ");
		stockOrderId = (sc.nextInt());
		try {
			String query = "UPDATE stockOrder SET isActive = 0 WHERE stockOrderId = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, stockOrderId);

			int isSuccess = preparedStatement.executeUpdate();
			if (isSuccess > 0) {
				System.out.println("Stock Order deleted Successfully");
			} else {
				System.out.println("Cannot Find Stock Order Details. Try Again...");
			}
		} catch (Exception ex) {

			System.out.println("stockOrderDeleteException : " + ex.getMessage());
		}
	}

	@Override
	public void getStockOrderById() {

		Integer stockOrderId;
		System.out.println("Enter StockOrder Id : ");
		stockOrderId = (sc.nextInt());

		String query = "SELECT * FROM stockOrder WHERE stockOrderId = '" + stockOrderId + "' && isActive = 1";
		try {

			System.out.println("\n\n");
			System.out.println(
					"=========================================================================================================");
			System.out.println(
					"=======================================	Stock Order details		======================================");
			System.out.println(
					"=========================================================================================================");
			System.out.println("\n\n");

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {

				System.out.println("Stock Order Id:" + resultSet.getInt("stockOrderId"));
				System.out.println("Number Of Stocks:" + resultSet.getString("noOfStocks"));
				System.out.println("Product Name:" + resultSet.getString("productName"));
				System.out.println("Product Supplier Company:" + resultSet.getString("supplierCompany"));

			}
		} catch (Exception ex) {
			System.out.println("Error has been occured please try again");
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void getsupplierReport() {

		try {

			String query = "SELECT supplierId,firstName,lastName,email,nic,phone,companyName,isActive FROM supplier";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			File directory = new File("C:\\Users\\Tharindu De Costa\\Desktop\\OSGiReports");

			directory.mkdirs();

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

			File file = new File(directory, "ProductSupplierReport-" + dateFormat.format(date) + ".txt");
			FileWriter fileWriter = new FileWriter(file);

			fileWriter.write(String.format(
					"====================================================== Supplier Report =============================================================================================================\n\n"));
			fileWriter.write(

					String.format("%20s %20s %20s %20s %20s %20s %20s %20s\n", "Supplier Id", "First Name", "Last Name", "Email",
							"Nic", "Phone", "Company Name", "Is Active"));

			fileWriter.write(String.format(
					"=================================================================================================================================================================================\n\n"));

			while (resultSet.next()) {

				fileWriter.write(

						String.format(

								"%20s %20s %20s %20s %20s %20s %20s %20s\n", resultSet.getInt("supplierId"),
								resultSet.getString("firstName"), resultSet.getString("lastName"),
								resultSet.getString("email"), resultSet.getString("nic"), resultSet.getString("phone"),
								resultSet.getString("companyName"), resultSet.getBoolean("isActive")

						));

				fileWriter.write(String.format(
						"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));
			}

			fileWriter.flush();
			fileWriter.close();

			System.out.println("Supplier Report has been successfully Generated");

		} catch (Exception ex) {

			System.out.println("Get Supplier Report Error :" + ex.getMessage());
		}

	}

	@Override
	public void getstockOrderReport() {

		try {

			String query = "SELECT stockOrderId,noOfStocks,productName,supplierCompany,isActive FROM stockOrder";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			File directory = new File("C:\\Users\\Tharindu De Costa\\Desktop\\OSGiReports");

			directory.mkdirs();

			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

			File file = new File(directory, "StockOrderReport-" + dateFormat.format(date) + ".txt");
			FileWriter fileWriter = new FileWriter(file);

			fileWriter.write(String.format(
					"====================================================== Stock Order Report =============================================================================================================\n\n"));
			fileWriter.write(

					String.format("%20s %20s %20s %20s %20s \n", "stockOrderId", "noOfStocks", "productName",
							"supplierCompany", "isActive"));

			fileWriter.write(String.format(
					"=======================================================================================================================================================================================\n\n"));

			while (resultSet.next()) {

				fileWriter.write(

						String.format(

								"%20s %20s %20s %20s %20s\n", resultSet.getInt("stockOrderId"),
								resultSet.getString("noOfStocks"), resultSet.getString("productName"),
								resultSet.getString("supplierCompany"), resultSet.getBoolean("isActive")

						));

				fileWriter.write(String.format(
						"---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));
			}

			fileWriter.flush();
			fileWriter.close();

			System.out.println("Stock Report has been successfully Generated");

		} catch (Exception ex) {

			System.out.println("Get Supplier Report Error :" + ex.getMessage());
		}

	}

}