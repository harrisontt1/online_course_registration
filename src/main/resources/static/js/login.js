/**
 * login.js
 *
 * Handles the student login workflow. This script listens for the
 * login form submission, sends the username/password to the backend
 * authentication endpoint, and stores the authenticated username in
 * sessionStorage. Once authenticated, the student is redirected to
 * the dashboard page.
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *   <li>Authenticates the student</li>
 *   <li>Stores username for session-based navigation</li>
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
 *   <li>login.js stores username in sessionStorage</li>
 *   <li>login.js redirects to dashboard.html</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *   <li>No JWT or token-based authentication</li>
 *   <li>Session state is stored client-side only</li>
 *   <li>Backend uses simple boolean authentication</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *   <li>Add password hashing and secure storage</li>
 *   <li>Add JWT tokens for secure API access</li>
 *   <li>Add role-based access (student, admin, instructor)</li>
 *   <li>Add “Forgot Password” workflow</li>
 * </ul>
 */

document.addEventListener("DOMContentLoaded", () => {

    /**
     * Attach submit handler to the login form.
     */
    const form = document.getElementById("login-form");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        /**
         * Retrieve username and password from input fields.
         */
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        /**
         * Send authentication request to backend.
         *
         * Expected backend response:
         * true  → login successful
         * false → login failed
         */
        try {
            const response = await fetch("/api/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, password })
            });

            const result = await response.json();

            if (result === true) {
                /**
                 * Store username so dashboard, course, registration,
                 * and financial pages can validate the session.
                 */
                sessionStorage.setItem("username", username);
                // Redirect to dashboard on successful login
                window.location.href = "dashboard.html";
            } else {
                // Display login failure message
                document.getElementById("login-error").textContent =
                    "Invalid username or password.";
            }
        } catch (error) {
            // Display server connection error
            document.getElementById("login-error").textContent =
                "Unable to connect to server.";
        }
    });
});
