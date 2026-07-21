package com.example.registration.controller;

import org.springframework.web.bind.annotation.*;
import com.example.registration.model.FinancialStatus;
import com.example.registration.services.FinancialService;

/**
 * Controller responsible for exposing student financial/account status
 * to the frontend dashboard.
 *
 * <p>This controller provides the REST endpoint used by dashboard.js
 * to retrieve the authenticated student's financial standing. The
 * financial status displayed on the dashboard helps students understand
 * whether their account is in good standing and whether any holds may
 * affect registration.</p>
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *     <li>Supplies account status to dashboard.html</li>
 *     <li>Supports the student workflow by indicating financial holds</li>
 *     <li>Acts as the backend source of truth for financial standing</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *     <li>Financial data is currently stubbed in {@link FinancialService}</li>
 *     <li>No integration with billing or payment systems exists yet</li>
 *     <li>Username is passed from sessionStorage on the frontend</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *     <li>Integrate with a real student billing system</li>
 *     <li>Support detailed financial breakdown (tuition, fees, payments)</li>
 *     <li>Add endpoints for viewing invoices and making payments</li>
 *     <li>Support financial holds that block registration</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/financial")
public class FinancialController {

	private final FinancialService financialService;

	/**
	 * Constructor for dependency injection of FinancialService.
	 *
	 * @param financialService service used to retrieve financial status
	 */
	public FinancialController(FinancialService financialService) {
		this.financialService = financialService;
	}

	/**
	 * Retrieves the financial status for a specific student.
	 *
	 * <p>This endpoint is called by dashboard.js immediately after
	 * page load. The username is retrieved from sessionStorage on
	 * the frontend and passed to this method via the URL path.</p>
	 * @param username the student's username
	 * @return FinancialStatus object containing account standing
	 */
	@GetMapping("/{username}")
	public FinancialStatus getFinancialStatus(@PathVariable String username) {
		return financialService.getStatus(username);
	}
}
