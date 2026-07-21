document.addEventListener("DOMContentLoaded", async () => {
    const username = sessionStorage.getItem("username");

    if (!username) {
        window.location.href = "login.html";
        return;
    }

    const response = await fetch(`/api/student/${username}`);
    const student = await response.json();

    document.getElementById("student-name").textContent = student.name;
    document.getElementById("student-id").textContent = `Student ID: ${student.id}`;
});

