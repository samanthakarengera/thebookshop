const form = document.getElementById("registerForm");
const message = document.getElementById("message");
const API = "http://localhost:8080/api";
/**
 * register.js: verstuurt het registratieformulier naar POST /api/users/register,
 * logt daarna automatisch in en stuurt door naar books.html.
 */
form.addEventListener("submit", async function (event) {
    event.preventDefault();

    // De gegevens uit het formulier ophalen.
    const user = {
        name: document.getElementById("name").value.trim(),
        email: document.getElementById("email").value.trim(),
        password: document.getElementById("password").value
    };

    try {
        // Stap 1: de gebruiker aanmaken in de backend.
        const response = await fetch(`${API}/users/register`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(
                data.detail || data.message || "Registratie mislukt."
            );
        }

        // Stap 2: meteen inloggen met dezelfde gegevens,
        // zodat de backend een sessie aanmaakt.
        const loginResponse = await fetch(`${API}/users/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify({
                email: user.email,
                password: user.password
            })
        });

        if (loginResponse.ok) {
            window.location.href = "books.html";
        } else {
            // Account bestaat, automatisch inloggen mislukt
            window.location.href = "login.html";
        }

    } catch (error) {
        message.textContent = error.message;
        message.style.color = "red";
    }
});