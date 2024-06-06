package com.osgi.pcstoredb;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class DbConnectImpl implements DbConnect {

	private Connection dbConnection;
	private final String driverName;
	private String ConnectionString;
	private String dbUser;
	private String dbPassword;

	// setup db environment
	public DbConnectImpl() {

		this.driverName = "com.mysql.jdbc.Driver";
		this.ConnectionString = "jdbc:mysql://localhost:3306/pcstoredb";
		this.dbUser = "root";
		this.dbPassword = "Password";

	}

	@Override
	public Connection getDatabaseConnection() {

		try {

			Class.forName(driverName);
			dbConnection = (Connection) DriverManager.getConnection(ConnectionString, dbUser, dbPassword);

			System.out.println("Database Connection Eshtablished");

		} catch (Exception ex) {

			System.out.println("Database Connection Error : " + ex.getMessage());
		}

		return dbConnection;
	}

}
