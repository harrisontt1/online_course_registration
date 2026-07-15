package com.example.registration.online_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class StudentManager {

	/**
	 * Attempts to add a new student to the database.
	 * Includes graceful validation and error handling for optimal stability.
	 */
	public static boolean addStudent(Connection conn, Student student) {
		if (student == null) {
		System.err.println("Validation Error: Student context object cannot be null.");
		return false;
		}

		// 1. Pre-validation: Ensure the email is somewhat properly formatted before hitting the DB
		String email = student.getEmail();
		if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
		System.err.println("Validation Error: The email provided ('" + email + "') is not a valid format.");
		return false;
		}

		// 2. Updated SQL query using your production singular table columns
		String sql = "INSERT INTO student (first_name, last_name, email, major_id) VALUES (?, ?, ?, ?)";

		// 3. Execute the transaction
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setString(1, student.getFirstName());
		pstmt.setString(2, student.getLastName());
		pstmt.setString(3, student.getEmail());

		// Handle optional major_id safely (can be NULL)
		if (student.getMajorId() != null) {
		pstmt.setInt(4, student.getMajorId());
		} else {
		pstmt.setNull(4, Types.INTEGER);
		}

		int rowsAffected = pstmt.executeUpdate();

		if (rowsAffected > 0) {
		System.out.println("✅ Success: Student '" + student.getFirstName() + " " + student.getLastName() + "' has been added to the system.");
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