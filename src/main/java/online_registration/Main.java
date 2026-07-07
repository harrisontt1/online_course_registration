package online_registration; // Ensure this matches your actual package name

import java.sql.Connection;

public class Main {
	public static void main(String[] args) {

		// Securely fetch credentials from the .env file
		String user = DBConfig.getDatabaseUser();
		String password = DBConfig.getDatabasePassword();

		System.out.println("Initiating database connection...");

		// Connect using the robust DatabaseManager
		try (Connection conn = DatabaseManager.connect("localhost", "3306", "registrationdb", user, password)) {

		if (conn != null) {
		System.out.println("✅ SUCCESS: Secure connection to MySQL established!");
		System.out.println("👤 Authenticated as user: " + user);
		System.out.println("📁 Connected to database: registrationdb\n");

		// --- TEST 1: ADDING A COURSE ---
		System.out.println("--- Testing Course Catalog Insertion ---");

		String courseTitle = "CMSC 495";
		String courseDescription = "Computer Science Capstone";
		String courseCredits = "3"; // Passed as a string to trigger our graceful validation checks

		boolean isCourseAdded = CourseManager.addCourse(conn, courseTitle, courseDescription, courseCredits);

		if (isCourseAdded) {
		System.out.println("Test Complete: Course pipeline is fully functional.");
		} else {
		System.out.println("Test Complete: Course insertion encountered a validation block.");
		}
		}

		} catch (Exception e) {
		// Graceful error handling for ultimate system stability
		System.err.println("❌ Critical System Error: The application encountered an unexpected issue.");
		e.printStackTrace();
		}
	}
}