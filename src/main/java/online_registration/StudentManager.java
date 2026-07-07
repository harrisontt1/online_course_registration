package online_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentManager {

	/**
	 * Attempts to add a new student to the database.
	 * Includes graceful validation and error handling for optimal stability.
	 */
	public static boolean addStudent(Connection conn, Student student) {

		// 1. Pre-validation: Ensure the email is somewhat properly formatted before hitting the DB
		String email = student.getEmail();
		if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
		System.err.println("Validation Error: The email provided ('" + email + "') is not a valid format.");
		return false;
		}

		// 2. The SQL query using placeholders (?) for security
		String sql = "INSERT INTO student (name, email) VALUES (?, ?)";

		// 3. Execute the transaction
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setString(1, student.getName());
		pstmt.setString(2, student.getEmail());

		int rowsAffected = pstmt.executeUpdate();

		if (rowsAffected > 0) {
		System.out.println("✅ Success: Student '" + student.getName() + "' has been added to the system.");
		return true;
		}

		} catch (SQLException e) {
		// Gracefully handle specific MySQL errors, like trying to add a duplicate email
		if (e.getErrorCode() == 1062) { // 1062 is the MySQL code for Duplicate Entry
		System.err.println("Database Error: A student with the email '" + email + "' is already registered.");
		} else {
		System.err.println("Database Error: Could not add the student. Details: " + e.getMessage());
		}
		}

		return false;
	}
}
