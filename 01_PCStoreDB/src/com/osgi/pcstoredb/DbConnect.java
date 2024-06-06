package com.osgi.pcstoredb;

import java.sql.Connection;

public interface DbConnect {

	// interface for connection
	public Connection getDatabaseConnection();

}
