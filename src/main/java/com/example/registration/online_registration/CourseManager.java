package com.example.registration.online_registration; // Ensure this matches your actual package name

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CourseManager {

	/**
	 * Validates inputs and attempts to add a new course to the catalog.
	 */
	public static boolean addCourse(Connection conn, String title, String description, String rawCredits) {

		// 1. Graceful Error Handling: Check for non-numerical characters or special characters
		if (rawCredits == null || !rawCredits.matches("^\\d+$")) {
		System.err.println("Validation Error: The credits value provided ('" + rawCredits + "') contains invalid characters. Please use a whole number.");
		return false;
		}

		// Parse the validated string into an integer
		int credits = Integer.parseInt(rawCredits);

		if (credits <= 0) {
		System.err.println("Validation Error: Course credits must be greater than zero.");
		return false;
		}

		// 2. Create the Course object
		Course newCourse = new Course(title, description, credits);

		// 3. The SQL query using placeholders (?) for security against injection
		String sql = "INSERT INTO course (title, description, credits) VALUES (?, ?, ?)";

		// 4. Execute the secure transaction
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setString(1, newCourse.getTitle());
		pstmt.setString(2, newCourse.getDescription());
		pstmt.setInt(3, newCourse.getCredits());

		int rowsAffected = pstmt.executeUpdate();

		if (rowsAffected > 0) {
		System.out.println("✅ Success: Course '" + newCourse.getTitle() + "' added to the catalog.");
		return true;
		}

		} catch (SQLException e) {
		// Gracefully catch database constraints or connection drops
		System.err.println("Database Error: Could not add the course to the catalog.");
		System.err.println("Technical Details: " + e.getMessage());
		}

		return false;
	}
}