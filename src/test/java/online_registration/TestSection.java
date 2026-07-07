package online_registration;

import java.sql.Connection;

public class TestSection {

	public static void main(String[] args) {

		System.out.println("Initiating Section Test...");
		String user = DBConfig.getDatabaseUser();
		String password = DBConfig.getDatabasePassword();

		try (Connection conn = DatabaseManager.connect("localhost", "3306", "registrationdb", user, password)) {

		if (conn == null) {
		System.err.println("Validation Error: Could not establish connection. Aborting section test.");
		return;
		}

		System.out.println("Connection verified. Testing Section Manager...");

		// --- TEST 1: ADDING A SECTION ---
		System.out.println("\n--- Testing addSection() ---");

		// IMPORTANT: This relies on your Foreign Key constraint!
		// It assumes the course you added earlier (CMSC 495) was assigned course_id 1.
		int targetCourseId = 1;
		String schedule = "Summer 2026 - Online";
		String capacity = "30"; // Using a string to verify our numerical validation check

		boolean isAdded = SectionManager.addSection(conn, targetCourseId, schedule, capacity);

		if (isAdded) {
		System.out.println("Test 1 Complete: Section successfully linked to Course ID " + targetCourseId);
		}

		// --- TEST 2: CHECKING AVAILABILITY ---
		System.out.println("\n--- Testing checkAvailability() ---");

		// Assuming the section we just added was assigned section_id 1
		int testSectionId = 1;
		boolean isAvailable = SectionManager.checkAvailability(conn, testSectionId);

		if (isAvailable) {
		System.out.println("Test 2 Complete: Section " + testSectionId + " has open seats available.");
		} else {
		System.out.println("Test 2 Complete: Section " + testSectionId + " is full or does not exist.");
		}

		} catch (Exception e) {
		System.err.println("Critical System Error: The test encountered an unexpected issue.");
		e.printStackTrace();
		}
	}
}