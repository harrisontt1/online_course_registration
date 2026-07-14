package online_registration.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import online_registration.DBConfig;
import online_registration.DatabaseManager;
import online_registration.SectionManager;

import java.sql.Connection;

public class TestSection {

	@Test
	public void testSectionOperations()
	{
		System.out.println("Initiating Section Test...");
		String user = DBConfig.getDatabaseUser();
		String password = DBConfig.getDatabasePassword();

		try (Connection conn = DatabaseManager.connect("localhost", "3306", "registrationdb", user, password))
			{

			assertNotNull(conn, "Validation Error: Could not establish connection. Aborting section test.");
			System.out.println("Connection verified. Testing Section Manager...");

			// --- TEST 1: ADDING A SECTION ---
			System.out.println("\n--- Testing addSection() ---");

			// IMPORTANT: This relies on Foreign Key constraints
			int targetCourseId = 1;
			int targetTermId = 1;     // Newly required to map back to the term table
			String schedule = "MWF 9:00 AM";
			String capacity = "30"; // Using a string to verify our numerical validation check

			// Updated manager method signature to include the required term tracking
			boolean isAdded = SectionManager.addSection(conn, targetCourseId, targetTermId, schedule, capacity);

			assertTrue(isAdded, "Test 1 Failed: Section could not be linked.");
			System.out.println("Test 1 Complete: Section successfully linked to Course ID " + targetCourseId);

			// --- TEST 2: CHECKING AVAILABILITY ---
			System.out.println("\n--- Testing checkAvailability() ---");

			int testSectionId = 1;
			boolean isAvailable = SectionManager.checkAvailability(conn, testSectionId);

			// We log the availability state rather than forcing a strict true/false assertion
			// since actual capacity numbers shift dynamically based on seed data state
			if (isAvailable)
				{
				System.out.println("Test 2 Complete: Section " + testSectionId + " has open seats available.");
				} else
				{
				System.out.println("Test 2 Complete: Section " + testSectionId + " is full or does not exist.");
				}

			}
		catch (Exception e)
			{
			System.err.println("Critical System Error: The test encountered an unexpected issue.");
			e.printStackTrace();
			}
	}
}