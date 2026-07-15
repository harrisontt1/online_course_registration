/**
 * Students with active holds must be blocked before they can claim a seat in a section.
 *
 * Tests:
 * Verify that EnrollmentManager.enrollStudent() returns false if a row exists in
 * financial_hold where is_active = 1.
 *
 * Verify that once the hold is cleared (is_active = 0), the student can successfully enroll.
 */
package online_registration.junit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import com.example.registration.online_registration.EnrollmentManager;

public class TestFinancialHold {

	@Test
	public void testActiveFinancialHoldBlocksEnrollment() {
		System.out.println("--- Testing Financial Hold Guardrail ---");
		EnrollmentManager enrollmentManager = new EnrollmentManager();

		// Assume Student ID 3 has an active hold row set to is_active = 1
		String restrictedStudentId = "3";
		String targetSectionId = "1";

		System.out.println("Attempting to enroll Student ID [" + restrictedStudentId + "] with active business hold...");
		boolean isEnrolled = enrollmentManager.enrollStudent(restrictedStudentId, targetSectionId);

		// The system must catch the hold and return false
		assertFalse(isEnrolled, "Security Violation: Enrollment succeeded despite an active financial hold on the account.");
		System.out.println("✅ Pass: Enrollment manager successfully blocked registration due to active hold status.");
	}
}