package online_registration;

public class Enrollment {
	private int enrollmentId;
	private int studentId;
	private int sectionId;
	private String status; // varchar field mapping

	public Enrollment(int studentId, int sectionId, String status) {
		this.studentId = studentId;
		this.sectionId = sectionId;
		this.status = status != null ? status : "ENROLLED";
	}

	public Enrollment(int enrollmentId, int studentId, int sectionId, String status) {
		this.enrollmentId = enrollmentId;
		this.studentId = studentId;
		this.sectionId = sectionId;
		this.status = status;
	}

	// Getters and Setters
	public int getEnrollmentId() { return enrollmentId; }
	public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

	public int getStudentId() { return studentId; }
	public void setStudentId(int studentId) { this.studentId = studentId; }

	public int getSectionId() { return sectionId; }
	public void setSectionId(int sectionId) { this.sectionId = sectionId; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}