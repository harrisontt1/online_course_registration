package com.example.registration.online_registration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SectionManager {

	/**
	 * Validates inputs and attempts to add a new course section to the database.
	 */
	public static boolean addSection(Connection conn, int courseId, int termId, String schedule, String rawCapacity) {

		// Graceful Error Handling: Check for non-numerical or special characters before parsing
		if (rawCapacity == null || !rawCapacity.matches("^\\d+$")) {
		System.err.println("Validation Error: The capacity value ('" + rawCapacity + "') contains invalid characters. Please use a whole number.");
		return false;
		}

		int capacity = Integer.parseInt(rawCapacity);

		if (capacity <= 0) {
		System.err.println("Validation Error: Section capacity must be greater than zero.");
		return false;
		}

		// Updated secure SQL query using placeholders to map term_id
		String sql = "INSERT INTO section (course_id, term_id, schedule, capacity, enrolled_count) VALUES (?, ?, ?, ?, 0)";

		// Execute the transaction
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

		pstmt.setInt(1, courseId);
		pstmt.setInt(2, termId);
		pstmt.setString(3, schedule);
		pstmt.setInt(4, capacity);

		int rowsAffected = pstmt.executeUpdate();

		if (rowsAffected > 0) {
		System.out.println("Success: Section added for Course ID " + courseId + " in Term ID " + termId + " (" + schedule + ").");
		return true;
		}

		} catch (SQLException e) {
		System.err.println("Database Error: Could not add the section. Ensure the Course ID and Term ID are valid.");
		System.err.println("Technical Details: " + e.getMessage());
		}

		return false;
	}

	/**
	 * Checks if a section has available seats.
	 */
	public static boolean checkAvailability(Connection conn, int sectionId) {
		String sql = "SELECT capacity, enrolled_count FROM section WHERE section_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
		pstmt.setInt(1, sectionId);

		try (ResultSet rs = pstmt.executeQuery()) {
		if (rs.next()) {
		int capacity = rs.getInt("capacity");
		int enrolledCount = rs.getInt("enrolled_count");

		return enrolledCount < capacity;
		}
		}
		} catch (SQLException e) {
		System.err.println("Database Error: Could not retrieve availability for Section " + sectionId);
		}

		return false;
	}
}