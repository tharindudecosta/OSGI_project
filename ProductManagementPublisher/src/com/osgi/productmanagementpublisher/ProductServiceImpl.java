package com.osgi.productmanagementpublisher;

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

public class ProductServiceImpl implements ProductService {

	private Connection connection = null;
	private Statement statement;
	private ResultSet resultSet;
	private DbConnect dbContext;
	private static PreparedStatement preparedStatement = null;
	Scanner sc = new Scanner(System.in);

	public ProductServiceImpl() {
		super();
		this.dbContext = new DbConnectImpl();
		this.connection = dbContext.getDatabaseConnection();
	}

	@Override
	public void addNewProduct() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

		ProductModel productModel = new ProductModel();

		System.out.println("\n\n");
		System.out.println(
				"=========================================================================================================");
		System.out.println(
				"==============================	Chamara PC Store Product Register Console	===============================");
		System.out.println(
				"=========================================================================================================");
		System.out.println("\n\n");

		System.out.println("Enter Your Product Name ::");
		productModel.setProductName(sc.nextLine().trim());

		System.out.println("Enter Your Product Brand Name ::");
		productModel.setBrand(sc.nextLine().trim());

		System.out.println("Enter Your Unit Price ::");
		productModel.setUnitPrice(sc.nextLine().trim());

		System.out.println("Enter Your Quantity ::");
		productModel.setQuantity(sc.nextLine().trim());

		try {

			String query = "insert into products(productName,brand,unitPrice,quantity,isActive) values(?,?,?,?,'1') ";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, productModel.getProductName());
			preparedStatement.setString(2, productModel.getBrand());
			preparedStatement.setString(3, productModel.getUnitPrice());
			preparedStatement.setString(4, productModel.getQuantity());

			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {

				System.out.println("Product  Successfully Added");

			} else {

				System.out.println("Error has occured please try again");

			}

		} catch (Exception ex) {

			System.out.println("productSaveError : " + ex.getMessage());
		}

	}

	@Override
	public void deleteProduct() {

		Integer productId;

		System.out.print("\nPlease enter Product id : ");
		productId = (sc.nextInt());

		try {

			String query = "UPDATE products SET isActive = 0 WHERE productId = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, productId);
			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {

				System.out.println("Product Has Been Deleted Successfully...");

			} else {

				System.out.println("Cannot Find The Product. Try Again..");

			}

		} catch (Exception ex) {

			System.out.println("productDeleteException : " + ex.getMessage());

		}

	}

	@Override
	public void getAllProductDetails() {

		try {

			String query = "SELECT productId,productName,brand,unitPrice,quantity FROM products WHERE isActive = 1";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\n\n");
			System.out.println(
					"=========================================================================================================");
			System.out.println(
					"===============================	Chamara PC Store Product catalog	==================================");
			System.out.println(
					"=========================================================================================================");
			System.out.println("\n\n");

			System.out.println(String.format("%20s %20s %20s %20s %20s  \n", "ProductId", "ProductName",
					"Product Brand", " Unit Price", "Quantity"));

			System.out.println(
					"--------------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {

				System.out.printf("%20d %20s %20s %20s %20d\n", resultSet.getInt("productId"),
						resultSet.getString("productName"), resultSet.getString("brand"),
						resultSet.getString("unitPrice"), resultSet.getInt("quantity")

				);

				System.out.println(
						"--------------------------------------------------------------------------------------------------------");
			}

		} catch (Exception ex) {

			System.out.println("getAllProductDetailsException:" + ex.getMessage());

		}

	}

	@Override
	public void getProductById() {

		Integer productId;

		System.out.println("Enter Product Id : ");
		productId = (sc.nextInt());

		String query = " SELECT *  FROM products WHERE productId = " + productId;

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\n\n");
			System.out.println(
					"=========================================================================================================");
			System.out.println(
					"===============================	Chamara PC Store Product details	==================================");
			System.out.println(
					"=========================================================================================================");
			System.out.println("\n\n");

			while (resultSet.next()) {

				System.out.println("ProductId		: " + resultSet.getInt("productId"));
				System.out.println("Product Name		: " + resultSet.getString("productName"));
				System.out.println("Brand Name		: " + resultSet.getString("brand"));
				System.out.println("Unit Price		: " + resultSet.getString("unitPrice"));
				System.out.println("Quantity		: " + resultSet.getString("quantity"));
				System.out.println("Is Active		: " + resultSet.getBoolean("isActive"));

			}

			System.out.println("==============================================================================");

		} catch (Exception ex) {

			System.out.println("Error has been orccured please try again");
			System.out.println(ex.getMessage());

		}

	}

	@Override
	public void genarateProductReport() {
		try {

			String query = "SELECT productId,productName,brand,unitPrice,quantity,isActive FROM products";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			File directory = new File("C:\\Users\\Tharindu De Costa\\Desktop\\OSGiReports");

			directory.mkdirs();

			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
			
			
			File file = new File(directory, "StockProductReport-"+dateFormat.format(date)+".txt");
			FileWriter fileWriter = new FileWriter(file);

			fileWriter.write(String.format(
					"====================================================== Product Stock Report =============================================================================================================\n\n"));
			fileWriter.write(

					String.format("%30s %30s %30s %30s %30s %30s\n", "product Id", "productName", "brand", "unitPrice",
							"quantity", "isActive"));

			fileWriter.write(String.format(
					"=================================================================================================================================================================================\n\n"));

			while (resultSet.next()) {

				fileWriter.write(

						String.format(

								"%30s %30s %30s %30s %30s %30s\n", resultSet.getInt("productId"),
								resultSet.getString("productName"), resultSet.getString("brand"),
								resultSet.getString("unitPrice"), resultSet.getInt("quantity"),
								resultSet.getBoolean("isActive")));

				fileWriter.write(String.format(
						"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));
			}

			fileWriter.flush();
			fileWriter.close();

			System.out.println("Product Report has been successfully");

		} catch (Exception ex) {

			System.out.println("Get Product Report Error:" + ex.getMessage());
		}

	}

}