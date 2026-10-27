
const form = document.getElementById("registerForm");
const message = document.getElementById("message");

form.addEventListener("submit", async function (event) {
    event.preventDefault();

    // De gegevens uit het formulier ophalen.
    const user = {
        name: document.getElementById("name").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    try {
        // De gegevens naar de Spring Boot-backend sturen.
        const response = await fetch(
            "http://localhost:8080/api/users/register",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            }
        );

        const data = await response.json();

        if (!response.ok) {
            throw new Error(
                data.detail || data.message || "Registratie mislukt."
            );
        }

        //message.textContent = "Account succesvol aangemaakt! ";
        //message.style.color = "green";
        // Doorsturen naar de boekenpagina na succesvol inloggen.
        window.location.href = "books.html";

        form.reset();

    } catch (error) {
        message.textContent = error.message === "Mislukt."
            ? "Poging tot verbinden mislukt. Probeer opnieuw."
            : error.message;

        message.style.color = "red";
    }
});
