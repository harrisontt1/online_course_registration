package online_registration;

public class Section {
	private int sectionId;
	private int courseId;
	private String schedule;
	private int capacity;
	private int enrolledCount;

	// Constructor for creating a new section (enrolledCount defaults to 0)
	public Section(int courseId, String schedule, int capacity) {
		this.courseId = courseId;
		this.schedule = schedule;
		this.capacity = capacity;
		this.enrolledCount = 0;
	}

	// Constructor for retrieving an existing section from the database
	public Section(int sectionId, int courseId, String schedule, int capacity, int enrolledCount) {
		this.sectionId = sectionId;
		this.courseId = courseId;
		this.schedule = schedule;
		this.capacity = capacity;
		this.enrolledCount = enrolledCount;
	}

	// Getters and Setters
	public int getSectionId() { return sectionId; }
	public void setSectionId(int sectionId) { this.sectionId = sectionId; }

	public int getCourseId() { return courseId; }
	public void setCourseId(int courseId) { this.courseId = courseId; }

	public String getSchedule() { return schedule; }
	public void setSchedule(String schedule) { this.schedule = schedule; }

	public int getCapacity() { return capacity; }
	public void setCapacity(int capacity) { this.capacity = capacity; }

	public int getEnrolledCount() { return enrolledCount; }
	public void setEnrolledCount(int enrolledCount) { this.enrolledCount = enrolledCount; }
}