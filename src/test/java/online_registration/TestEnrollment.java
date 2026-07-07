package online_registration;

/**
 * Test class for validating the EnrollmentManager business logic.
 * This test assumes 'config.properties' is correctly placed in src/main/resources/.
 */
public class TestEnrollment {

	public static void main(String[] args) {
		System.out.println("=== Initiating Enrollment Test ===");

		// 1. Instantiate the Manager
		// EnrollmentManager handles its own connection and config loading internally.
		EnrollmentManager enrollmentManager = new EnrollmentManager();

		// 2. Define Test Inputs
		String studentId = "1";
		String sectionId = "1";

		System.out.println("Test Scenario: Enrolling Student ID [" + studentId + "] into Section [" + sectionId + "]");

		// 3. Execute the enrollment
		boolean isEnrolled = enrollmentManager.enrollStudent(studentId, sectionId);

		// 4. Verify results
		if (isEnrolled) {
		System.out.println("✅ Test Passed: System successfully processed the enrollment and updated capacity.");
		} else {
		System.err.println("❌ Test Failed: The enrollment operation returned false. Check logs above for specific database or validation errors.");
		}

		System.out.println("=== Test Cycle Complete ===");
	}
}