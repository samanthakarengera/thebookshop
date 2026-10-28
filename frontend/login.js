const loginForm = document.getElementById("loginForm");
const message = document.getElementById("message");
const API = "http://localhost:8080/api";

loginForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const user = {
        email: document.getElementById("email").value.trim(),
        password: document.getElementById("password").value
    };

    try {
        const response = await fetch(`${API}/users/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(user)
        });

        if (response.status === 401) {
            throw new Error("Verkeerd e-mailadres of wachtwoord.");
        }

        if (!response.ok) {
            throw new Error("Login mislukt (" + response.status + ").");
        }

        // De body is leeg bij een geslaagde login, dus geen response.json() hier.
        window.location.href = "books.html";

    } catch (error) {
        message.textContent = error.message;
        message.style.color = "red";
    }
});