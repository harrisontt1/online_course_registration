package online_registration.testng;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivityTest {

	private String dbUrl = "jdbc:mysql://localhost:3306/registrationdb?useSSL=false&serverTimezone=UTC";
	private String dbUser = "root";
	private String dbPass = "password";

	@BeforeMethod
	public void setUp() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");

		String envUrl = System.getenv("DB_URL");
		if (envUrl != null && !envUrl.trim().isEmpty()) {
		dbUrl = envUrl;
		}
		} catch (ClassNotFoundException e) {
		System.err.println("MySQL JDBC Driver missing from classpath! Ensure it is added to build.gradle.");
		} catch (Exception e) {
		System.err.println("Graceful fallback: Could not parse environment variables. " + e.getMessage());
		}
	}

	@Test
	public void testDatabaseConnectionSuccess() {
		Connection connection = null;
		try {
		connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);

		Assert.assertNotNull(connection, "Database connection instance should not be null.");
		Assert.assertFalse(connection.isClosed(), "Database connection should be actively open.");

		} catch (SQLException e) {
		Assert.fail("MySQL connection failed! Check if MySQL Server is running locally. Error: " + e.getMessage());
		} finally {
		if (connection != null) {
		try {
		connection.close();
		} catch (SQLException e) {
		System.err.println("Failed to close connection gracefully: " + e.getMessage());
		}
		}
		}
	}

	@Test
	public void testInvalidCredentialsGracefulFailure() {
		Connection invalidConnection = null;
		try {
		invalidConnection = DriverManager.getConnection(dbUrl, dbUser, "INVALID_PASSWORD_!!!");
		Assert.fail("Connection should have thrown an Access Denied SQLException.");
		} catch (SQLException e) {
		Assert.assertTrue(e.getMessage().toLowerCase().contains("denied") || e.getSQLState() != null,
				"Expected SQL access denial validation state.");
		} finally {
		if (invalidConnection != null) {
		try {
		invalidConnection.close();
		} catch (SQLException ignored) {}
		}
		}
	}
}