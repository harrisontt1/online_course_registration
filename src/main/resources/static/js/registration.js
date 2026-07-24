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
let availableCourses = [];

document.addEventListener("DOMContentLoaded", async () => {
    const username = sessionStorage.getItem("username");
    const role = sessionStorage.getItem("role");

    if (!username) {
        window.location.href = "login.html";
        return;
    }

    if (role === "admin") {
        window.location.href = "admin-dashboard.html";
        return;
    }

    await loadAvailableCourses(username);
    await loadRegisteredCourses(username);

    const searchInput = document.getElementById("course-search");

    searchInput.addEventListener("input", () => {
        const searchValue = searchInput.value.toLowerCase();

        const filteredCourses = availableCourses.filter(course =>
            course.id.toLowerCase().includes(searchValue) ||
            course.name.toLowerCase().includes(searchValue)
        );

        renderCourseList(filteredCourses, username);
    });
});

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
     *   Each course has registration button
     * ]
     */

async function loadAvailableCourses(username) {
    const tableBody = document.getElementById("registration-table-body");

    try {
        const response = await fetch("/api/course");

        if (!response.ok) {
            throw new Error("Course API failed with status: " + response.status);
        }

        availableCourses = await response.json();

        renderCourseList(availableCourses, username);

    } catch (error) {
        console.error("Error fetching course list:", error);

        if (tableBody) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="8">Unable to load available courses.</td>
                </tr>
            `;
        }

        showMessage("Unable to load available courses.", false);
    }
}

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

    if (!tableBody) {
        console.error("Could not find registration-table-body.");
        return;
    }

    tableBody.innerHTML = "";

    if (!courseList || courseList.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="8">No available courses found.</td>
            </tr>
        `;
        return;
    }

    courseList.forEach(course => {
        const maxCapacity = course.maxCapacity || 0;
        const enrolledCount = course.enrolledCount || 0;
        const seatsAvailable = Math.max(maxCapacity - enrolledCount, 0);
        const prerequisite = course.prerequisiteCourseId && course.prerequisiteCourseId.trim() !== ""
            ? formatCourseId(course.prerequisiteCourseId)
            : "None";

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.name}</td>
            <td>${course.instructor}</td>
            <td>${course.credits}</td>
            <td>${course.meetingTime}</td>
            <td>${seatsAvailable} / ${maxCapacity}</td>
            <td>${prerequisite}</td>
            <td>
                <button class="register-btn" data-course-id="${course.id}">
                    Register
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });

    document.querySelectorAll(".register-btn").forEach(button => {
        button.addEventListener("click", async () => {
            const courseId = button.getAttribute("data-course-id");
            await submitRegistration(username, courseId);
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

        showMessage(result.message + " Email notification sent.", result.success);

        await loadAvailableCourses(username);
        await loadRegisteredCourses(username);

    } catch (error) {
        console.error("Error submitting registration:", error);
        showMessage("An error occurred while registering. Email notification could not be completed.", false);
    }
}

/*
 * 
 * Load Registered Courses
 * 
 * This function retrieves the courses currently registered to
 * the logged-in student and sends them to the display function.
 * 
 */

async function loadRegisteredCourses(username) {
    const tableBody = document.getElementById("registered-course-table-body");

    try {
        const response = await fetch(`/api/registration/${username}`);

        if (!response.ok) {
            throw new Error("Registered courses API failed with status: " + response.status);
        }

        const registeredCourses = await response.json();

        renderRegisteredCourses(registeredCourses, username);

    } catch (error) {
        console.error("Error loading registered courses:", error);

        if (tableBody) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="6">Unable to load registered courses.</td>
                </tr>
            `;
        }

        showMessage("Unable to load registered courses.", false);
    }
}

/*
 * 
 * Render Registered Courses
 * 
 * This function displays the student's registered courses in the
 * My Registered Courses table. Each registered course includes a
 * Withdraw button.
 * 
 */

function renderRegisteredCourses(registeredCourses, username) {
    const tableBody = document.getElementById("registered-course-table-body");

    if (!tableBody) {
        console.error("Could not find registered-course-table-body.");
        return;
    }

    tableBody.innerHTML = "";

    if (!registeredCourses || registeredCourses.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="6">No registered courses yet.</td>
            </tr>
        `;
        return;
    }

    registeredCourses.forEach(course => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.name}</td>
            <td>${course.instructor}</td>
            <td>${course.credits}</td>
            <td>${course.meetingTime}</td>
            <td>
                <button class="withdraw-btn" data-course-id="${course.id}">
                    Withdraw
                </button>
            </td>
        `;

        tableBody.appendChild(row);
    });

    document.querySelectorAll(".withdraw-btn").forEach(button => {
        button.addEventListener("click", async () => {
            const courseId = button.getAttribute("data-course-id");
            await withdrawRegistration(username, courseId);
        });
    });
}

/*
 * 
 * Withdraw From Course
 * 
 * This function sends a withdrawal request to the backend API.
 * If the withdrawal succeeds, the registered course list is
 * refreshed and the student receives a confirmation message.
 * 
 */

async function withdrawRegistration(username, courseId) {
    try {
        const response = await fetch("/api/registration", {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                username: username,
                courseId: courseId
            })
        });

        const result = await response.json();

        showMessage(result.message + " Email notification sent.", result.success);

        await loadAvailableCourses(username);
        await loadRegisteredCourses(username);

    } catch (error) {
        console.error("Error withdrawing from course:", error);
        showMessage("An error occurred while withdrawing. Email notification could not be completed.", false);
    }
}

/*
 * 
 * Display Status Message
 * 
 * This function displays success or error messages on the course
 * registration page. Successful actions appear in green, while
 * failed actions appear in red.
 * 
 */

function showMessage(message, success) {
    const messageElement = document.getElementById("registration-message");

    if (!messageElement) {
        console.error("Could not find registration-message.");
        return;
    }

    messageElement.textContent = message;
    messageElement.style.fontWeight = "bold";
    messageElement.style.color = success ? "green" : "red";
}

function formatCourseId(courseId) {
    if (!courseId) {
        return "None";
    }

    return courseId.replace(/([A-Z]+)([0-9]+)/, "$1 $2");
}
