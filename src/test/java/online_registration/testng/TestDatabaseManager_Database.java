package online_registration.testng;

import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;
import java.sql.Connection;
import online_registration.DatabaseManager;
import online_registration.DBConfig;

public class TestDatabaseManager_Database {

	@Test
	public void testDatabaseManagerUtilityConnection() {
		String user = DBConfig.getDatabaseUser();
		String password = DBConfig.getDatabasePassword();

		try (Connection conn = DatabaseManager.connect("localhost", "3306", "registrationdb", user, password)) {
		assertNotNull(conn, "DatabaseManager utility failed to yield a valid active connection instance mapping.");
		} catch (Exception e) {
		System.err.println("Utility validation failed to auto-close resource context block: " + e.getMessage());
		}
	}
}