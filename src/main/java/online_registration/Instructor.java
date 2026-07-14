package online_registration;

public class Instructor {
	private int instructorId;
	private String firstName;
	private String lastName;
	private String email;

	public Instructor(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public Instructor(int instructorId, String firstName, String lastName, String email) {
		this.instructorId = instructorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	// Getters and Setters
	public int getInstructorId() { return instructorId; }
	public void setInstructorId(int instructorId) { this.instructorId = instructorId; }

	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
}