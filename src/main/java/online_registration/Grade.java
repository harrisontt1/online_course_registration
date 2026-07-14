package online_registration;
import java.math.BigDecimal;

public class Grade {
	private int gradeId;
	private int enrollmentId;
	private String letterGrade;   // enum mapping
	private BigDecimal gradePoints; // decimal(3,2) mapping
	private int earnedCredits;

	public Grade(int enrollmentId, String letterGrade, double gradePoints, int earnedCredits) {
		this.enrollmentId = enrollmentId;
		this.letterGrade = letterGrade;
		this.gradePoints = BigDecimal.valueOf(gradePoints);
		this.earnedCredits = earnedCredits;
	}

	public Grade(int gradeId, int enrollmentId, String letterGrade, BigDecimal gradePoints, int earnedCredits) {
		this.gradeId = gradeId;
		this.enrollmentId = enrollmentId;
		this.letterGrade = letterGrade;
		this.gradePoints = gradePoints;
		this.earnedCredits = earnedCredits;
	}

	// Getters and Setters
	public int getGradeId() { return gradeId; }
	public void setGradeId(int gradeId) { this.gradeId = gradeId; }

	public int getEnrollmentId() { return enrollmentId; }
	public void setEnrollmentId(int enrollmentId) { this.enrollmentId = enrollmentId; }

	public String getLetterGrade() { return letterGrade; }
	public void setLetterGrade(String letterGrade) { this.letterGrade = letterGrade; }

	public BigDecimal getGradePoints() { return gradePoints; }
	public void setGradePoints(BigDecimal gradePoints) { this.gradePoints = gradePoints; }

	public int getEarnedCredits() { return earnedCredits; }
	public void setEarnedCredits(int earnedCredits) { this.earnedCredits = earnedCredits; }
}