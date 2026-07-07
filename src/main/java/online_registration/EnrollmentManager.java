package online_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnrollmentManager {

	/**
	 * Enrolls a student into a specific course section and updates the section capacity.
	 *
	 * @param studentIdStr The student's ID as a string for validation
	 * @param sectionIdStr The section's ID as a string for validation
	 * @return true if enrollment and capacity update are successful, false otherwise
	 */
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

		// Define the two SQL statements needed for this transaction
		String insertEnrollmentSql = "INSERT INTO enrollment (student_id, section_id, status) VALUES (?, ?, 'ENROLLED')";
		String updateSectionSql = "UPDATE section SET enrolled_count = enrolled_count + 1 WHERE section_id = ?";

		// Securely connect using the dedicated application user
		try (Connection conn = DatabaseManager.connect("localhost", "3306", "registrationdb", "registrationapp", DBConfig.getDatabasePassword())) {

		// 1. Disable auto-commit to start the manual transaction
		conn.setAutoCommit(false);

		// 2. Prepare both statements
		try (PreparedStatement insertStmt = conn.prepareStatement(insertEnrollmentSql);
		     PreparedStatement updateStmt = conn.prepareStatement(updateSectionSql)) {

		// Execute the INSERT
		insertStmt.setInt(1, studentId);
		insertStmt.setInt(2, sectionId);
		int insertRows = insertStmt.executeUpdate();

		// Execute the UPDATE
		updateStmt.setInt(1, sectionId);
		int updateRows = updateStmt.executeUpdate();

		// 3. Verify both operations succeeded before committing
		if (insertRows > 0 && updateRows > 0) {
		conn.commit(); // Save all changes to the database simultaneously
		System.out.println("Success: Student " + studentId + " enrolled in section " + sectionId + ". Capacity updated.");
		return true;
		} else {
		conn.rollback(); // Cancel the transaction if either step failed
		System.err.println("Enrollment Error: Could not complete both the enrollment and the capacity update. No changes were made.");
		return false;
		}

		} catch (SQLException e) {
		// Gracefully rollback the transaction if an exception occurs during execution
		conn.rollback();
		System.err.println("Database Error during transaction: Rolling back changes to preserve data integrity. Details: " + e.getMessage());
		} finally {
		// 4. Reset auto-commit back to its default state
		conn.setAutoCommit(true);
		}

		} catch (SQLException e) {
		System.err.println("Database Connection Error: Could not establish a connection for enrollment. Details: " + e.getMessage());
		}

		return false;
	}
}