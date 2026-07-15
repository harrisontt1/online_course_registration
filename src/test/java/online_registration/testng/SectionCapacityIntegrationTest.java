package online_registration.testng;

import com.example.registration.online_registration.RegistrationSystem;
import com.example.registration.online_registration.Student;
import com.example.registration.online_registration.Section;
import com.example.registration.online_registration.RegistrationResult;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SectionCapacityIntegrationTest {

	private RegistrationSystem system;
	private Section limitedSection;

	@BeforeMethod
	public void setUp() {
		system = new RegistrationSystem();

		// Updated parameters: courseId, schedule, capacity, termId
		limitedSection = new Section(101, "Online", 1, 1);
		system.addSection(limitedSection);
	}

	@Test
	public void testSectionFillsUpToCapacityAndBlocksSubsequentRequests() {
		// Updated parameters: dummyId, firstName, lastName, isTestContext
		Student firstStudent = new Student("STU001", "First", "Student", true);
		Student secondStudent = new Student("STU002", "Second", "Student", true);

		system.addStudent(firstStudent);
		system.addStudent(secondStudent);

		RegistrationResult responseOne = system.register(firstStudent, limitedSection);
		Assert.assertTrue(responseOne.isSuccess(), "First registration transaction failed to occupy open capacity seat.");

		Assert.assertFalse(limitedSection.checkAvailability(), "Section availability state should be false after taking the last seat.");

		RegistrationResult responseTwo = system.register(secondStudent, limitedSection);

		Assert.assertNotNull(responseTwo, "Capacity response wrapper should not be null.");
		Assert.assertFalse(responseTwo.isSuccess(), "System allowed registration execution beyond defined seat capacity limit!");
		Assert.assertTrue(responseTwo.getMessage().toLowerCase().contains("full") || responseTwo.getMessage().toLowerCase().contains("cap"),
				"Expected failure message explanation detailing cap/full context status.");
	}
}