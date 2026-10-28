const API = "http://localhost:8080/api";

const profileInfo = document.getElementById("profileInfo");
const purchasedBooks = document.getElementById("purchasedBooks");
const message = document.getElementById("message");

// De gegevens van de ingelogde gebruiker ophalen.
async function loadProfile() {
    try {
        const response = await fetch(`${API}/users/me`, {
            credentials: "include"
        });

        if (response.status === 401) {
            window.location.href = "login.html";
            return;
        }

        if (!response.ok) {
            throw new Error("Fout " + response.status);
        }

        const user = await response.json();

        profileInfo.innerHTML = "";

        const name = document.createElement("p");
        name.textContent = "Naam: " + user.name;

        const email = document.createElement("p");
        email.textContent = "E-mail: " + user.email;

        profileInfo.append(name, email);

    } catch (error) {
        profileInfo.textContent =
            "Kan je profiel niet laden: " + error.message;
    }
}

// De boeken van de gebruiker ophalen via zijn reserveringen.
async function loadPurchasedBooks() {
    try {
        const response = await fetch(`${API}/reservations`, {
            credentials: "include"
        });

        if (!response.ok) {
            return;
        }

        const reservations = await response.json();

        purchasedBooks.innerHTML = "";

        if (reservations.length === 0) {
            purchasedBooks.textContent = "Je hebt nog geen boeken.";
            return;
        }

        reservations.forEach(function (reservation) {
            const card = document.createElement("div");
            card.className = "book-card";

            const title = document.createElement("h3");
            title.textContent = reservation.book.title;

            const author = document.createElement("p");
            author.textContent = "Auteur: " + reservation.book.authors;

            const pickup = document.createElement("p");
            pickup.textContent = "Af te halen op: " + reservation.pickupDate;

            const detailsButton = document.createElement("button");
            detailsButton.textContent = "Bekijk details of schrijf een review";
            detailsButton.addEventListener("click", function () {
                window.location.href =
                    "book-detail.html?id=" + reservation.book.id;
            });

            card.append(title, author, pickup, detailsButton);
            purchasedBooks.appendChild(card);
        });

    } catch (error) {
        message.textContent = "Kan je boeken niet laden.";
        message.style.color = "red";
    }
}

loadProfile();
loadPurchasedBooks();