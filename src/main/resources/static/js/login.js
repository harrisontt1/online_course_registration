/**
 * login.js
 *
 * Handles the student login workflow. This script listens for the
 * login form submission, sends the username/password to the backend
 * authentication endpoint, and redirects the student to the dashboard
 * upon successful login.
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *   <li>Authenticates the student</li>
 *   <li>Redirects to dashboard.html upon successful login</li>
 *   <li>Acts as the entry point for all authenticated persona pages</li>
 * </ul>
 *
 * <p><strong>Data Flow:</strong></p>
 * <ul>
 *   <li>login.js listens for form submission</li>
 *   <li>login.js sends POST /api/login with:</li>
 *   <pre>
 *   {
 *     "username": "<entered>",
 *     "password": "<entered>"
 *   }
 *   </pre>
 *   <li>Backend returns true/false</li>
 *   <li>login.js redirects to dashboard.html if login succeeds</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *   <li>Does NOT store username in sessionStorage</li>
 *   <li>Dashboard and other pages must fetch username differently</li>
 *   <li>Backend uses simple boolean authentication</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *   <li>Store username in sessionStorage for consistent persona navigation</li>
 *   <li>Add password hashing and secure storage</li>
 *   <li>Add JWT tokens for secure API access</li>
 *   <li>Add role-based access (student, admin, instructor)</li>
 * </ul>
 */

document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("login-form");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("/api/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });

            const result = await response.json();

            if (result.success === true) {
                sessionStorage.setItem("username", username);
                sessionStorage.setItem("role", result.role);

                if (result.role === "admin") {
                    window.location.href = "admin-dashboard.html";
                } else {
                    window.location.href = "dashboard.html";
                }
            } else {
                document.getElementById("login-error").textContent =
                    "Invalid username or password.";
            }
        } catch (error) {
            document.getElementById("login-error").textContent =
                "Unable to connect to server.";
        }
    });
});
