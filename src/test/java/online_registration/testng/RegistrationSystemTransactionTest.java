package online_registration.testng;

import com.example.registration.online_registration.RegistrationSystem;
import com.example.registration.online_registration.Student;
import com.example.registration.online_registration.Section;
import com.example.registration.online_registration.RegistrationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationSystemTransactionTest {

	private RegistrationSystem registrationSystem;
	private Student sampleStudent;
	private Section openSection;

	@BeforeMethod
	public void setUp() {
		registrationSystem = new RegistrationSystem();

		// Updated parameters: dummyId, firstName, lastName, isTestContext
		sampleStudent = new Student("STU123", "John", "Doe", true);

		// Updated parameters: courseId, schedule, capacity, termId
		openSection = new Section(495, "Mon 7PM", 30, 1);

		registrationSystem.addStudent(sampleStudent);
		registrationSystem.addSection(openSection);
	}

	@Test
	public void testSuccessfulRegistrationTransaction() {
		RegistrationResult result = registrationSystem.register(sampleStudent, openSection);

		Assert.assertNotNull(result);
		Assert.assertTrue(result.isSuccess(), "Transaction should complete successfully when rules are met.");
		Assert.assertTrue(result.getMessage().contains("Registration complete"),
				"Expected confirmation message output.");
	}
}