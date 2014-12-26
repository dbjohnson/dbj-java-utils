package com.BryanJohnson.Utils.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite3 {
	
	public static boolean driverSupported = true;

	static {
		// load the sqlite-JDBC driver using the current class loader
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			driverSupported = false;
			throw new RuntimeException("org.sqlite.JDBC driver not found.  Is the classpath set properly?");
		}
	}

	public static Connection connect(String pathToDB) {
		// create a database connection
		try {
			return DriverManager.getConnection(String.format("jdbc:sqlite:%s", pathToDB));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not load database!");
		}		
	}
}
