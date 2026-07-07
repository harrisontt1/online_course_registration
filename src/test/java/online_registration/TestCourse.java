package online_registration;

import java.sql.Connection;

public class TestCourse {

	public static void main(String[] args) {

		System.out.println("Initiating Course Test...");
		String user = DBConfig.getDatabaseUser();
		String password = DBConfig.getDatabasePassword();

		// Establish connection with graceful error handling
		try (Connection conn = DatabaseManager.connect("localhost", "3306", "registrationdb", user, password)) {

		if (conn == null) {
		System.err.println("Validation Error: Could not establish connection. Aborting course test.");
		return;
		}

		System.out.println("Connection verified. Attempting to add CMSC 495...");

		// --- THE COURSE TEST ---
		String courseTitle = "CMSC 495";
		String courseDescription = "Computer Science Capstone";
		String courseCredits = "3"; // Using a string to test the internal numerical validation

		boolean isCourseAdded = CourseManager.addCourse(conn, courseTitle, courseDescription, courseCredits);

		if (isCourseAdded) {
		System.out.println("Test Complete: The Course pipeline is fully functional.");
		} else {
		System.out.println("Test Complete: Course insertion encountered a validation block.");
		}

		} catch (Exception e) {
		// Gracefully catch any unexpected runtime issues to prevent hard crashes
		System.err.println("Critical System Error: The test encountered an unexpected issue.");
		e.printStackTrace();
		}
	}
}