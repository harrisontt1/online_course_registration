/*
 * 
 * Admin Dashboard JavaScript
 * 
 * Allows administrators to add, edit, and delete courses.
 * Students are blocked from this page.
 * 
 */

document.addEventListener("DOMContentLoaded", async () => {
    const role = sessionStorage.getItem("role");

    if (role !== "admin") {
        window.location.href = "dashboard.html";
        return;
    }

    await loadAdminCourses();

    const form = document.getElementById("course-form");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const course = {
            id: document.getElementById("course-id").value,
            name: document.getElementById("course-name").value,
            instructor: document.getElementById("course-instructor").value,
            credits: parseInt(document.getElementById("course-credits").value),
            meetingTime: document.getElementById("course-meeting-time").value
        };

        const response = await fetch("/api/course", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(course)
        });

        const result = await response.json();

        if (result === true) {
            showAdminMessage("Course saved successfully.", true);
            form.reset();
            await loadAdminCourses();
        } else {
            showAdminMessage("Course could not be saved.", false);
        }
    });
});

async function loadAdminCourses() {
    const response = await fetch("/api/course");
    const courses = await response.json();

    const tableBody = document.getElementById("admin-course-table-body");
    tableBody.innerHTML = "";

    courses.forEach(course => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${course.id}</td>
            <td><input value="${course.name}" id="name-${course.id}"></td>
            <td><input value="${course.instructor}" id="instructor-${course.id}"></td>
            <td><input type="number" value="${course.credits}" id="credits-${course.id}"></td>
            <td><input value="${course.meetingTime}" id="meeting-${course.id}"></td>
            <td>
                <button onclick="updateCourse('${course.id}')">Update</button>
                <button onclick="deleteCourse('${course.id}')">Delete</button>
            </td>
        `;

        tableBody.appendChild(row);
    });
}

async function updateCourse(courseId) {
    const course = {
        id: courseId,
        name: document.getElementById(`name-${courseId}`).value,
        instructor: document.getElementById(`instructor-${courseId}`).value,
        credits: parseInt(document.getElementById(`credits-${courseId}`).value),
        meetingTime: document.getElementById(`meeting-${courseId}`).value
    };

    const response = await fetch(`/api/course/${courseId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(course)
    });

    const result = await response.json();

    if (result === true) {
        showAdminMessage("Course updated successfully.", true);
        await loadAdminCourses();
    } else {
        showAdminMessage("Course could not be updated.", false);
    }
}

async function deleteCourse(courseId) {
    const response = await fetch(`/api/course/${courseId}`, {
        method: "DELETE"
    });

    const result = await response.json();

    if (result === true) {
        showAdminMessage("Course deleted successfully.", true);
        await loadAdminCourses();
    } else {
        showAdminMessage("Course could not be deleted.", false);
    }
}

function showAdminMessage(message, success) {
    const messageElement = document.getElementById("admin-message");
    messageElement.textContent = message;
    messageElement.style.fontWeight = "bold";
    messageElement.style.color = success ? "green" : "red";
}