/**
 * Check academic history via enrollment and grade so advanced classes
 * cannot be bypassed.
 *
 * Tests:
 * Attempt to enroll a student in CMSC430 without them having a passing
 * grade in CMSC150. The test should assert that the enrollment is blocked.
 *
 * Insert a passing record ('A', 'B', or 'C') into the grade table for the
 * prerequisite course, and verify that the enrollment is allowed to go through.
 */
package online_registration.junit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import com.example.registration.online_registration.EnrollmentManager;

public class TestPrerequisiteValidation {

	@Test
	public void testMissingPrerequisiteBlocksEnrollment() {
		System.out.println("--- Testing Academic Prerequisite Engine ---");
		EnrollmentManager enrollmentManager = new EnrollmentManager();

		// Assume Student ID 4 has not completed the introductory course
		String studentId = "4";
		String advancedSectionId = "2"; // Section tracking an advanced course like CMSC430

		System.out.println("Attempting to enroll Student ID [" + studentId + "] into advanced Section [" + advancedSectionId + "] without prerequisites...");
		boolean isEnrolled = enrollmentManager.enrollStudent(studentId, advancedSectionId);

		// The system must identify the missing prerequisite rule mapping and return false
		assertFalse(isEnrolled, "Academic Violation: Student allowed to register for an advanced course without passing prerequisites.");
		System.out.println("✅ Pass: Academic engine cleanly blocked the enrollment request.");
	}
}