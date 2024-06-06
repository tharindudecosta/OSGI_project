package com.osgi.employeemanagementpublisher;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

import com.osgi.pcstoredb.DbConnect;
import com.osgi.pcstoredb.DbConnectImpl;

public class EmployeeServiceImpl implements EmployeeService {

	private Connection connection = null;
	private Statement statement;
	private ResultSet resultSet;
	private DbConnect dbContext;
	private static PreparedStatement preparedStatement = null;

	Scanner sc = new Scanner(System.in);

	public EmployeeServiceImpl() {
		super();
		this.dbContext = new DbConnectImpl();
		this.connection = dbContext.getDatabaseConnection();
	}

	@Override
	public void addNewEmployee() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);

		EmployeeModel employeeModel = new EmployeeModel();

		System.out.println("\n\n");
		System.out.println(
				"=========================================================================================================");
		System.out.println(
				"==============================	Chamara PC Store Employee Register Console	===============================");
		System.out.println(
				"=========================================================================================================");
		System.out.println("\n\n");

		System.out.println("Enter Employee First Name ::");
		employeeModel.setFirstName(sc.nextLine().trim());

		System.out.println("Enter Employee Last Name ::");
		employeeModel.setLastName(sc.nextLine().trim());

		System.out.println("Enter Employee Email ::");
		employeeModel.setEmail(sc.nextLine().trim());

		System.out.println("Enter Phone Number ::");
		employeeModel.setPhone(sc.nextLine().trim());

		try {

			String query = "insert into employee(firstName,lastName,email,phone,isActive) values(?,?,?,?,'1') ";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, employeeModel.getFirstName());
			preparedStatement.setString(2, employeeModel.getLastName());
			preparedStatement.setString(3, employeeModel.getEmail());
			preparedStatement.setString(4, employeeModel.getPhone());

			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {

				System.out.println("Employee Registration Has Been Successful");

			} else {

				System.out.println("Error has been orccured please try again");

			}

		} catch (Exception ex) {

			System.out.println("Employee SignUp error : " + ex.getMessage());
		}

	}

	@Override
	public void deleteEmployee() {

		Integer employeeId;

		System.out.print("\nPlease enter Employee id :");
		employeeId = (sc.nextInt());

		try {

			String query = "UPDATE employee SET isActive = 0 WHERE employeeId = ?";

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, employeeId);
			int isSuccess = preparedStatement.executeUpdate();

			if (isSuccess > 0) {

				System.out.println("Employee Has Been Deleted Successfully...");

			} else {

				System.out.println("Cannot Find The Employee. Try Again..");

			}

		} catch (Exception ex) {

			System.out.println("Employee Delete Error : " + ex.getMessage());

		}

	}

	@Override
	public void getAllEmployeeDetails() {

		try {

			String query = "SELECT employeeId, firstName, lastName, email, phone FROM employee WHERE isActive = 1";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\n\n");
			System.out.println(
					"=========================================================================================================");
			System.out.println(
					"===============================	Chamara PC Store Employee Roster	=================================");
			System.out.println(
					"=========================================================================================================");
			System.out.println("\n\n");
			System.out.println(String.format("%20s %20s %20s %20s %20s\n", "EmployeeId", "First Name", "Last Name",
					"Email", "Phone Number"));

			System.out.println(
					"--------------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {

				System.out.printf("%20d %20s %20s %20s %20s\n", resultSet.getInt("employeeId"),
						resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"),
						resultSet.getString("phone")

				);

				System.out.println(
						"--------------------------------------------------------------------------------------------------------");
			}

		} catch (Exception ex) {

			System.out.println("Get All Employee Details Error :" + ex.getMessage());

		}

	}

	@Override
	public void getEmployeeById() {

		Integer employeeId;

		System.out.println("Enter Employee Id : ");
		employeeId = (sc.nextInt());

		String query = "SELECT * FROM employee WHERE employeeId = " + employeeId ;

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println("\n\n");
			System.out.println(
					"=========================================================================================================");
			System.out.println(
					"================================	Chamara PC Store Employee Details	==================================");
			System.out.println(
					"=========================================================================================================");
			System.out.println("\n\n");
			while (resultSet.next()) {

				System.out.println("EmployeeId:" + resultSet.getInt("employeeId"));
				System.out.println("First Name:" + resultSet.getString("firstName"));
				System.out.println("Last Name:" + resultSet.getString("lastName"));
				System.out.println("Email:" + resultSet.getString("email"));
				System.out.println("Phone Number:" + resultSet.getString("phone"));
				System.out.println("Is Active:" + resultSet.getBoolean("isActive"));


			}

			System.out.println("==============================================================================");

		} catch (Exception ex) {

			System.out.println("Error has been orccured please try again");
			System.out.println(ex.getMessage());

		}

	}

	@Override
	public void getEmployeeReport() {

		try {

			String query = "SELECT employeeId,firstName ,lastName, email,phone,isActive FROM employee";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			File directory = new File("C:\\Users\\Tharindu De Costa\\Desktop\\OSGiReports");

			directory.mkdirs();

			
			Date date = new Date() ;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
			
			File file = new File(directory, "EmployeeReport-"+dateFormat.format(date)+".txt");
			FileWriter fileWriter = new FileWriter(file);

			fileWriter.write(String.format(
					"====================================================== Chamara PC Store Employee Report ===============================================================================================\n\n"));
			fileWriter.write(

					String.format("%30s %30s %30s %30s %30s %30s\n", "Employee Id", "First Name", "Last Name", "Email",
							"Phone","isActive"));

			fileWriter.write(String.format(
					"------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));

			while (resultSet.next()) {

				fileWriter.write(

						String.format(

								"%30d %30s %30s %30s %30s %30s\n", resultSet.getInt("employeeId"), resultSet.getString("firstName"),
								resultSet.getString("lastName"), resultSet.getString("email"),
								resultSet.getString("phone"),resultSet.getBoolean("isActive")

						));

				fileWriter.write(String.format(
						"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n"));
			}

			fileWriter.flush();
			fileWriter.close();

			System.out.println("Report generated successfully");

		} catch (Exception ex) {

			System.out.println("Get Employee Report Error :" + ex.getMessage());

		}
	}

}
