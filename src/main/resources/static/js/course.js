/**
 * course.js
 *
 * Handles dynamic population of the Course page by retrieving
 * the list of available course objects from the backend and
 * rendering them into the UI. This script ensures that only
 * authenticated students can access the page and provides the
 * foundation for course browsing and registration.
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *   <li>Displays each available course</li>
 *   <li>Supports course exploration before registration</li>
 *   <li>Prevents access unless the student is logged in</li>
 *   <li>Acts as the entry point for the registration workflow</li>
 * </ul>
 *
 * <p><strong>Data Source:</strong></p>
 * <ul>
 *   <li>/api/course — returns list of all available course objects</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *   <li>Uses sessionStorage for authentication state</li>
 *   <li>Assumes backend returns valid JSON</li>
 *   <li>Renders each course into a table or list</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *   <li>Add filtering (credits, instructor, meeting time)</li>
 *   <li>Add sorting (alphabetical, credits, schedule)</li>
 *   <li>Add pagination for large catalogs</li>
 *   <li>Add “View Details” modal for each course</li>
 * </ul>
 */
document.addEventListener("DOMContentLoaded", async () => {

    /**
     * Retrieve the authenticated student's username from sessionStorage.
     * If no username is found, redirect the user back to the login page.
     */
    const username = sessionStorage.getItem("username");

    if (!username) {
        window.location.href = "login.html";
        return;
    }

    /**
     * Fetch the list of available course objects from the backend.
     *
     * Expected JSON:
     * [
     *   {
     *     "id": "CMSC495",
     *     "name": "Capstone",
     *     "instructor": "Dr. Smith",
     *     "credits": 3,
     *     "meetingTime": "MWF 10AM"
     *   },
     *   ...
     * ]
     */
    try {
        const response = await fetch("/api/course");
        const courseList = await response.json();

        renderCourseList(courseList);

    } catch (error) {
        console.error("Error fetching course list:", error);
    }
});


/**
 * Renders the list of course objects into the UI.
 *
 * <p>This function dynamically creates table rows (or list items)
 * for each course returned by the backend. It displays key course
 * attributes such as ID, name, instructor, credits, and meeting time.</p>
 *
 * @param {Array<Object>} courseList - List of course objects
 */
function renderCourseList(courseList) {
    const tableBody = document.getElementById("course-table-body");

    courseList.forEach(course => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.name}</td>
            <td>${course.instructor}</td>
            <td>${course.credits}</td>
            <td>${course.meetingTime}</td>
        `;

        tableBody.appendChild(row);
    });
}
