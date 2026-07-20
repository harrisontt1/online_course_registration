package com.example.registration.model;

/**
 * Represents the financial/account status of a student.
 * This model is used by the dashboard to display whether
 * the student is in good standing.
 * Student Persona Component:
 * - Supports FinancialController
 * - Supports dashboard.js
 */
public class FinancialStatus {

	private String status;

	/**
	 * Constructs a FinancialStatus object.
	 *
	 * @param status textual representation of account standing
	 */
	public FinancialStatus(String status) {
		this.status = status;
	}

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}
