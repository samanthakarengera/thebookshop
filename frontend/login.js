
const loginForm = document.getElementById("loginForm");
const message = document.getElementById("message");

loginForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const user = {
        email: document.getElementById("email").value.trim(),
        password: document.getElementById("password").value
    };

    try {
        const response = await fetch(
            "http://localhost:8080/api/users/login",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                credentials: "include",
                body: JSON.stringify(user)
            }
        );

        const data = await response.json();

        if (!response.ok) {
            throw new Error(
                data.detail || data.message || "Login mislukt."
            );
        }
        
        message.textContent = "Succesvol ingelogd! ";
        message.style.color = "green";
        // Doorsturen naar de boekenpagina na succesvol inloggen.
        // window.location.href = "books.html";

    } catch (error) {
        message.textContent = error.message === "Mislukt."
            ? "Poging tot verbinden mislukt. Probeer opnieuw."
            : error.message;

        message.style.color = "red";
    }
});
