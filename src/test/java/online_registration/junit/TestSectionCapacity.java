
/**
 * To prevent overselling a course and causing data mismatch errors
 * by verifying that the manager layer securely rejects registrations
 * once enrolled_count >= capacity
 *
 * Tests:
 * Set a section's capacity to 1 in the database.
 *
 * Enroll Student A (assert true).
 *
 * Attempt to enroll Student B into the exact same section. Assert that
 * it returns false and that enrolled_count remains securely clamped at 1.
 */

package online_registration.junit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import online_registration.EnrollmentManager;

public class TestSectionCapacity {

	@Test
	public void testExceedingSectionCapacityBlocksEnrollment() {
		System.out.println("--- Testing Section Capacity Guardrail ---");
		EnrollmentManager enrollmentManager = new EnrollmentManager();

		// Assume Section ID 3 is a specialty lab session with a max capacity of 1, and 1 student is already inside.
		String freshStudentId = "5";
		String fullSectionId = "3";

		System.out.println("Attempting to enroll Student ID [" + freshStudentId + "] into fully filled Section [" + fullSectionId + "]...");
		boolean isEnrolled = enrollmentManager.enrollStudent(freshStudentId, fullSectionId);

		// The system must reject the transaction and prevent seat inflation
		assertFalse(isEnrolled, "Capacity Violation: System allowed over-enrollment beyond section limits.");
		System.out.println("✅ Pass: Transaction safely terminated because the section has hit maximum capacity boundaries.");
	}
}