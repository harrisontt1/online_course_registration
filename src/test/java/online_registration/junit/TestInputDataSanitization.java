/**
 * To verify that parsing layers gracefully handle dirty, malformed,
 * or malicious character parameters without crashing the thread execution.
 *
 * Tests:
 * Pass non-numerical characters or symbol injections (like "300#!" or "abc")
 * into methods expecting numerical parameters (like capacity, course credits,
 * or IDs) to confirm try-catch blocks handle them smoothly without crashing
 * the system thread.
 */

package online_registration.junit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;
import com.example.registration.online_registration.EnrollmentManager;

public class TestInputDataSanitization {

	@Test
	public void testMalformedInputParametersHandledGracefully() {
		System.out.println("--- Testing Input Sanitization Resilience ---");
		EnrollmentManager enrollmentManager = new EnrollmentManager();

		// Inject intentional malicious or corrupted non-numerical strings into the manager endpoints
		String maliciousStudentId = "999; DROP TABLE student; --";
		String corruptedSectionId = "Section_One_#4!9";

		System.out.println("Injecting non-numerical special characters into enrollment manager...");

		boolean isProcessed = false;
		try {
		isProcessed = enrollmentManager.enrollStudent(maliciousStudentId, corruptedSectionId);

		// The method should gracefully catch the parsing issue internally and safely return false
		assertFalse(isProcessed, "Sanitization Error: System tried to run database processes using unsanitized string input.");
		System.out.println("✅ Pass: Internal handlers caught non-numerical inputs gracefully without system failures.");

		} catch (Exception e) {
		org.junit.jupiter.api.Assertions.fail("Critical Crash: The application threw an unhandled exception instead of recovering gracefully.", e);
		}
	}
}