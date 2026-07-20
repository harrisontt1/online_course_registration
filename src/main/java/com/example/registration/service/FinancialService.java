package com.example.registration.service;

import com.example.registration.model.FinancialStatus;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for retrieving student financial/account status.
 * Supports the dashboard by providing account standing.
 * <p>This class provides the backend logic used by the FinancialController
 * to supply dashboard.js with the student's current account standing.
 * Financial standing is an important part of the student workflow because
 * outstanding balances or holds may prevent course registration.</p>
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *     <li>Supplies account status to dashboard.html</li>
 *     <li>Supports the student workflow by indicating whether financial holds exist</li>
 *     <li>Acts as the backend source of truth for financial standing</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *     <li>Financial data is currently stubbed and not retrieved from a database</li>
 *     <li>No integration with billing, payment, or student accounts systems exists yet</li>
 *     <li>All students are returned as "Good Standing" for development purposes</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *     <li>Integrate with a real student billing system</li>
 *     <li>Support detailed financial breakdown (tuition, fees, payments, outstanding balances)</li>
 *     <li>Implement financial holds that block registration</li>
 *     <li>Add endpoints for viewing invoices and making payments</li>
 *     <li>Support notifications for overdue balances</li>
 * </ul>
 */
@Service
public class FinancialService {

	/**
	 * Retrieves financial status for a student.
	 *
	 * @param username the student's username
	 * @return FinancialStatus object
	 */
	public FinancialStatus getStatus(String username) {
		// TODO: Replace with real billing database lookup
		return new FinancialStatus("Good Standing");
	}
}
