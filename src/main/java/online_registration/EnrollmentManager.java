package online_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnrollmentManager {

	public boolean enrollStudent(String studentIdStr, String sectionIdStr) {

		int studentId;
		int sectionId;

		// Gracefully handle potential errors with numerical inputs
		try {
		if (studentIdStr == null || !studentIdStr.matches("\\d+")) {
		throw new NumberFormatException("Student ID contains non-numerical or special characters.");
		}
		if (sectionIdStr == null || !sectionIdStr.matches("\\d+")) {
		throw new NumberFormatException("Section ID contains non-numerical or special characters.");
		}

		studentId = Integer.parseInt(studentIdStr);
		sectionId = Integer.parseInt(sectionIdStr);

		} catch (NumberFormatException e) {
		System.err.println("Enrollment Validation Error: Invalid ID provided. Please ensure IDs are strictly numerical. Details: " + e.getMessage());
		return false;
		}

		// Securely connect using the dedicated application user
		try (Connection conn = DatabaseManager.connect("localhost", "3306", "registrationdb", "registrationapp", DBConfig.getDatabasePassword())) {

		// Rule Check 1: Financial and Academic Holds
		String holdCheckSql = "SELECT COUNT(*) FROM financial_hold WHERE student_id = ? AND is_active = 1";
		try (PreparedStatement holdStmt = conn.prepareStatement(holdCheckSql)) {
		holdStmt.setInt(1, studentId);
		try (ResultSet rs = holdStmt.executeQuery()) {
		if (rs.next() && rs.getInt(1) > 0) {
		System.err.println("Enrollment Blocked: Student ID " + studentId + " has an active hold.");
		return false;
		}
		}
		}

		// Look up the course_id associated with this section
		int courseId = -1;
		String courseLookupSql = "SELECT course_id FROM section WHERE section_id = ?";
		try (PreparedStatement courseStmt = conn.prepareStatement(courseLookupSql)) {
		courseStmt.setInt(1, sectionId);
		try (ResultSet rs = courseStmt.executeQuery()) {
		if (rs.next()) {
		courseId = rs.getInt("course_id");
		} else {
		System.err.println("Enrollment Error: Section ID does not exist.");
		return false;
		}
		}
		}

		// Rule Check 2: Verify Prerequisites (If any prerequisites are required)
		String prereqCheckSql = "SELECT required_course_id FROM course_prerequisite WHERE course_id = ?";
		try (PreparedStatement prereqStmt = conn.prepareStatement(prereqCheckSql)) {
		prereqStmt.setInt(1, courseId);
		try (ResultSet rs = prereqStmt.executeQuery()) {
		while (rs.next()) {
		int requiredCourseId = rs.getInt("required_course_id");

		// Check if student completed the required course with passing marks
		String gradeCheckSql = "SELECT COUNT(*) FROM enrollment e " +
				"JOIN section s ON e.section_id = s.section_id " +
				"JOIN grade g ON e.enrollment_id = g.enrollment_id " +
				"WHERE e.student_id = ? AND s.course_id = ? AND g.letter_grade IN ('A', 'B', 'C')";
		try (PreparedStatement gradeStmt = conn.prepareStatement(gradeCheckSql)) {
		gradeStmt.setInt(1, studentId);
		gradeStmt.setInt(2, requiredCourseId);
		try (ResultSet rsGrade = gradeStmt.executeQuery()) {
		if (!rsGrade.next() || rsGrade.getInt(1) == 0) {
		System.err.println("Enrollment Blocked: Missing mandatory prerequisite course ID " + requiredCourseId);
		return false;
		}
		}
		}
		}
		}
		}

		// Rule Check 3: Seat Availability Bounds
		if (!SectionManager.checkAvailability(conn, sectionId)) {
		System.err.println("Enrollment Blocked: Section " + sectionId + " is completely filled.");
		return false;
		}

		// Define SQL statements for execution phase
		String insertEnrollmentSql = "INSERT INTO enrollment (student_id, section_id, status) VALUES (?, ?, 'ENROLLED')";
		String updateSectionSql = "UPDATE section SET enrolled_count = enrolled_count + 1 WHERE section_id = ?";

		conn.setAutoCommit(false);

		try (PreparedStatement insertStmt = conn.prepareStatement(insertEnrollmentSql);
		     PreparedStatement updateStmt = conn.prepareStatement(updateSectionSql)) {

		insertStmt.setInt(1, studentId);
		insertStmt.setInt(2, sectionId);
		int insertRows = insertStmt.executeUpdate();

		updateStmt.setInt(1, sectionId);
		int updateRows = updateStmt.executeUpdate();

		if (insertRows > 0 && updateRows > 0) {
		conn.commit();
		System.out.println("Success: Student " + studentId + " enrolled in section " + sectionId + ". Capacity updated.");
		return true;
		} else {
		conn.rollback();
		System.err.println("Enrollment Error: Transaction execution failure. No changes were made.");
		return false;
		}

		} catch (SQLException e) {
		conn.rollback();
		System.err.println("Database Error during transaction: Rolling back changes. Details: " + e.getMessage());
		} finally {
		conn.setAutoCommit(true);
		}

		} catch (SQLException e) {
		System.err.println("Database Connection Error: " + e.getMessage());
		}

		return false;
	}
}