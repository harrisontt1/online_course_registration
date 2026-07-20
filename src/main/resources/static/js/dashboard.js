/**
 * dashboard.js
 *
 * Handles dynamic population of the Student Dashboard page using
 * asynchronous REST API calls. This script runs immediately after
 * the DOM is fully loaded and retrieves student-specific data from
 * the backend to personalize the dashboard experience.
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *   <li>Displays student name and student ID</li>
 *   <li>Shows number of registered courses</li>
 *   <li>Shows financial/account status</li>
 *   <li>Prevents dashboard access unless the user is authenticated</li>
 * </ul>
 *
 * <p><strong>Data Sources:</strong></p>
 * <ul>
 *   <li>/api/student/{username}</li>
 *   <li>/api/registration/{username}</li>
 *   <li>/api/financial/{username}</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *   <li>Uses sessionStorage to track authentication state</li>
 *   <li>Uses async/await for cleaner asynchronous flow</li>
 *   <li>Assumes backend endpoints return valid JSON</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *   <li>Add loading indicators for each data section</li>
 *   <li>Add user-friendly error messages instead of console logs</li>
 *   <li>Support additional student metadata (major, GPA, email)</li>
 *   <li>Integrate notifications for holds or overdue balances</li>
 * </ul>
 */

document.addEventListener("DOMContentLoaded", async () => {
    const username = sessionStorage.getItem("username");

    /**
     * Prevent access if not logged in
     * Retrieve the authenticated student's username from sessionStorage.
     * If no username is found, redirect the user back to the login page.
     */
    if (!username) {
        window.location.href = "login.html";
        return;
    }

    /**
     * Fetch student profile information.
     *
     * Expected JSON:
     * {
     *   "id": "12345",
     *   "username": "jdoe",
     *   "name": "John Doe"
     * }
     */
    const studentResponse = await fetch(`/api/student/${username}`);
    const student = await studentResponse.json();

    document.getElementById("student-name").textContent = student.name;
    document.getElementById("student-id").textContent = `Student ID: ${student.id}`;

    /**
     * Fetch registered course count for the student.
     *
     * Expected JSON:
     * [
     *   { "id": "CMSC495", "name": "Capstone", ... },
     *   { "id": "CMSC451", "name": "Algorithms", ... }
     * ]
     *
     * Only the count is displayed on the dashboard.
     */
    const regResponse = await fetch(`/api/registration/${username}`);
    const registeredCourses = await regResponse.json();

    document.getElementById("registered-count").textContent = registeredCourses.length;

    /**
     * Fetch financial/account status (placeholder logic)
     *
     * Expected JSON:
     * {
     *   "status": "Good Standing"
     * }
     */
    const financialResponse = await fetch(`/api/financial/${username}`);
    const financial = await financialResponse.json();

    document.getElementById("account-status").textContent = financial.status;
});
