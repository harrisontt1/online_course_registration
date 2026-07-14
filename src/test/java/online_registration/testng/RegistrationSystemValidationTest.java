package online_registration.testng;

import online_registration.RegistrationSystem;
import online_registration.Student;
import online_registration.Section;
import online_registration.RegistrationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationSystemValidationTest {

	private RegistrationSystem registrationSystem;
	private Student sampleStudent;
	private Section fullSection;

	@BeforeMethod
	public void setUp() {
		registrationSystem = new RegistrationSystem();

		// Updated parameters: dummyId, firstName, lastName, isTestContext
		sampleStudent = new Student("STU123", "John", "Doe", true);

		// Updated parameters: courseId, schedule, capacity, termId
		fullSection = new Section(495, "Tue 7PM", 0, 1);

		registrationSystem.addStudent(sampleStudent);
		registrationSystem.addSection(fullSection);
	}

	@Test
	public void testRegistrationFailureDueToCapacity() {
		RegistrationResult result = registrationSystem.register(sampleStudent, fullSection);

		Assert.assertNotNull(result);
		Assert.assertFalse(result.isSuccess(), "Registration must fail when capacity is 0.");
		Assert.assertTrue(result.getMessage().contains("full"), "Expected capacity failure message.");
	}

	@Test
	public void testAuthenticationValidation() {
		boolean isValid = registrationSystem.authenticateStudent("STU123");
		boolean isInvalid = registrationSystem.authenticateStudent("FAKE999");

		Assert.assertTrue(isValid, "Registered student should validate successfully.");
		Assert.assertFalse(isInvalid, "Unregistered student ID should fail validation.");
	}
}