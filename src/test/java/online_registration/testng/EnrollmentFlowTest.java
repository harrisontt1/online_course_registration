package online_registration.testng;

import online_registration.RegistrationSystem;
import online_registration.Student;
import online_registration.Course;
import online_registration.Section;
import online_registration.RegistrationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EnrollmentFlowTest {

	private RegistrationSystem system;
	private Student validStudent;
	private Course targetCourse;
	private Section openSection;

	@BeforeMethod
	public void setUp() {
		system = new RegistrationSystem();

		// Updated parameters: dummyId, firstName, lastName, isTestContext
		validStudent = new Student("STU888", "Mandyck", "Chessie", true);
		targetCourse = new Course("CMSC495", "Capstone Project");

		// Updated parameters: courseId, schedule, capacity, termId
		openSection = new Section(495, "Thursday 7PM", 25, 1);

		system.addStudent(validStudent);
		system.addSection(openSection);
	}

	@Test
	public void testCompleteEndToEndEnrollmentFlow() {
		try {
		boolean loginPassed = system.authenticateStudent(validStudent.getStudentID());
		Assert.assertTrue(loginPassed, "Step 1 Failed: Valid student login credential rejected.");

		boolean sectionHasSeats = openSection.checkAvailability();
		Assert.assertTrue(sectionHasSeats, "Step 2/3 Failed: Targeted class section list returned unexpected status.");

		RegistrationResult outcome = system.register(validStudent, openSection);

		Assert.assertNotNull(outcome, "System transaction response cannot be null.");
		Assert.assertTrue(outcome.isSuccess(), "Step 5a Failed: System validation rejected a valid enrollment transaction.");
		Assert.assertTrue(outcome.getMessage().toLowerCase().contains("complete"),
				"Transaction response string text didn't output confirmation text.");

		} catch (NullPointerException | IllegalArgumentException e) {
		Assert.fail("Enrollment workflow broke unexpectedly due to a processing exception: " + e.getMessage());
		}
	}
}