package online_registration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

	/**
	 * Establishes a secure connection to the MySQL database.
	 * Includes pre-validation to ensure numerical inputs are formatted correctly.
	 */
	public static Connection connect(String host, String port, String dbName, String user, String password) {

		// Gracefully handle potential errors: Check for non-numerical or special characters in the port input
		if (port == null || !port.matches("\\d+")) {
		System.err.println("Validation Error: The port provided ('" + port + "') contains non-numerical characters. Please use a valid number like '3306'.");
		return null;
		}

		// Construct the connection URL
		String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

		try {
		// Attempt the connection using the validated URL and secure credentials
		return DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
		// Gracefully catch the error instead of crashing the application
		System.err.println("Database Connection Failed: Could not connect to the '" + dbName + "' database.");
		System.err.println("Technical Details: " + e.getMessage());
		return null;
		}
	}
}