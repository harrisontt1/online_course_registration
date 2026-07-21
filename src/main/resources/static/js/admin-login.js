document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("admin-login-form");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const username = document.getElementById("admin-username").value;
        const password = document.getElementById("admin-password").value;

        const response = await fetch("/api/admin/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        const result = await response.json();

        if (result === true) {
            sessionStorage.setItem("admin", username);
            window.location.href = "admin-dashboard.html";
        } else {
            document.getElementById("admin-login-error").textContent =
                "Invalid admin credentials.";
        }
    });
});
