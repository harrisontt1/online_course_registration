package online_registration.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import com.example.registration.online_registration.EnrollmentManager;

public class TestEnrollment {

	@Test
	public void testStudentEnrollmentFlow() {
		System.out.println("=== Initiating Enrollment Test ===");

		// 1. Instantiate the Manager
		EnrollmentManager enrollmentManager = new EnrollmentManager();

		// 2. Define Test Inputs
		String studentId = "1";
		String sectionId = "1";

		System.out.println("Test Scenario: Enrolling Student ID [" + studentId + "] into Section [" + sectionId + "]");

		// 3. Execute the enrollment
		boolean isEnrolled = enrollmentManager.enrollStudent(studentId, sectionId);

		// 4. Verify results using modern JUnit assertions
		assertTrue(isEnrolled, "Test Failed: The enrollment operation returned false. Check database constraints.");
		System.out.println("Test Passed: System successfully processed the enrollment and updated capacity.");

		System.out.println("=== Test Cycle Complete ===");
	}
}