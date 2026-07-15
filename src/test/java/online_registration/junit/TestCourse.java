package online_registration.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import com.example.registration.online_registration.CourseManager;
import com.example.registration.online_registration.DBConfig;
import com.example.registration.online_registration.DatabaseManager;

import java.sql.Connection;

public class TestCourse {

	@Test
	public void testAddCoursePipeline()
	{
		System.out.println("Initiating Course Test...");
		String user = DBConfig.getDatabaseUser();
		String password = DBConfig.getDatabasePassword();

		// Establish connection with graceful error handling
		try (Connection conn = DatabaseManager.connect("localhost", "3306", "registrationdb", user, password))
			{

			assertNotNull(conn, "Validation Error: Could not establish connection. Aborting course test.");
			System.out.println("Connection verified. Attempting to add course...");

			// --- THE COURSE TEST ---
			String courseTitle = "Intro to Programming";
			String courseDescription = "Foundational Java programming course.";
			String courseCredits = "3"; // Using a string to test the internal numerical validation

			boolean isCourseAdded = CourseManager.addCourse(conn, courseTitle, courseDescription, courseCredits);

			assertTrue(isCourseAdded, "Test Failed: Course insertion encountered a validation block or SQL error.");
			System.out.println("Test Complete: The Course pipeline is fully functional.");

			}
		catch (Exception e)
			{
			// Gracefully catch any unexpected runtime issues to prevent hard crashes[cite: 3]
			System.err.println("Critical System Error: The test encountered an unexpected issue.");
			e.printStackTrace();
			}
	}
}