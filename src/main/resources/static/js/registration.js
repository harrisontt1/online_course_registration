/**
 * registration.js
 *
 * Handles the student course registration workflow. This script
 * ensures that only authenticated students can access the page,
 * retrieves the list of available course objects, and allows the
 * student to submit a registration request for a single course.
 *
 * <p><strong>Student Persona Component:</strong></p>
 * <ul>
 *   <li>Displays each available course object</li>
 *   <li>Allows the student to register for a course</li>
 *   <li>Prevents access unless the student is logged in</li>
 *   <li>Acts as the core of the registration workflow</li>
 * </ul>
 *
 * <p><strong>Data Sources:</strong></p>
 * <ul>
 *   <li>/api/course — retrieves available course objects</li>
 *   <li>/api/registration — accepts registration requests</li>
 * </ul>
 *
 * <p><strong>Current Implementation Notes:</strong></p>
 * <ul>
 *   <li>Uses sessionStorage for authentication state</li>
 *   <li>Assumes backend returns valid JSON</li>
 *   <li>Registration requests are POSTed as JSON</li>
 * </ul>
 *
 * <p><strong>Future Enhancements:</strong></p>
 * <ul>
 *   <li>Add confirmation modals before registration</li>
 *   <li>Add schedule conflict detection</li>
 *   <li>Add credit limit validation</li>
 *   <li>Add ability to deregister from a course</li>
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
     * Fetch the list of available course objects.
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

        renderCourseList(courseList, username);

    } catch (error) {
        console.error("Error fetching course list:", error);
    }
});


/**
 * Renders the list of course objects into the UI.
 *
 * <p>This function dynamically creates table rows (or list items)
 * for each course returned by the backend. It also attaches a
 * “Register” button to each course, allowing the student to submit
 * a registration request.</p>
 *
 * @param {Array<Object>} courseList - List of course objects
 * @param {string} username - The authenticated student's username
 */
function renderCourseList(courseList, username) {
    const tableBody = document.getElementById("registration-table-body");

    courseList.forEach(course => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.name}</td>
            <td>${course.instructor}</td>
            <td>${course.credits}</td>
            <td>${course.meetingTime}</td>
            <td>
                <button class="register-btn" data-course-id="${course.id}">
                    Register
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });

    /**
     * Attach click handlers to all “Register” buttons.
     */
    document.querySelectorAll(".register-btn").forEach(button => {
        button.addEventListener("click", () => {
            const courseId = button.getAttribute("data-course-id");
            submitRegistration(username, courseId);
        });
    });
}


/**
 * Submits a registration request to the backend.
 *
 * <p>This function sends a POST request containing a
 * RegistrationRequest object:</p>
 *
 * <pre>
 * {
 *   "username": "jdoe",
 *   "courseId": "CMSC495"
 * }
 * </pre>
 *
 * <p>Upon success, the student is notified that the registration
 * was completed.</p>
 *
 * @param {string} username - The student's username
 * @param {string} courseId - The ID of the course to register for
 */
async function submitRegistration(username, courseId) {
    try {
        const response = await fetch("/api/registration", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                username: username,
                courseId: courseId
            })
        });

        const result = await response.json();

        if (result === true) {
            alert(`Successfully registered for course: ${courseId}`);
        } else {
            alert(`Registration failed for course: ${courseId}`);
        }

    } catch (error) {
        console.error("Error submitting registration:", error);
        alert("An error occurred while registering.");
    }
}
