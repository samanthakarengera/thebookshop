const API = "http://localhost:8080/api";

const message = document.getElementById("message");
const reservationList = document.getElementById("reservationList");

const params = new URLSearchParams(window.location.search);
const bookId = params.get("bookId");

// De reserveringen van de ingelogde gebruiker ophalen en tonen.
async function loadReservations() {
    try {
        const response = await fetch(`${API}/reservations`, {
            credentials: "include"
        });

        if (response.status === 401) {
            message.textContent = "Log eerst in om je reservaties te zien.";
            message.style.color = "red";
            return;
        }

        if (!response.ok) {
            throw new Error("Fout " + response.status);
        }

        const reservations = await response.json();
        displayReservations(reservations);

    } catch (error) {
        message.textContent =
            "Kan de reservaties niet laden: " + error.message;
        message.style.color = "red";
    }
}

// De reserveringen op de pagina tonen.
function displayReservations(reservations) {
    reservationList.innerHTML = "";

    if (reservations.length === 0) {
        reservationList.textContent = "Je hebt nog geen reservaties.";
        return;
    }

    reservations.forEach(function (reservation) {
        const card = document.createElement("div");
        card.className = "book-card";

        const title = document.createElement("h3");
        title.textContent = reservation.book
            ? reservation.book.title
            : "Boek #" + reservation.id;

        const pickup = document.createElement("p");
        pickup.textContent =
            "Af te halen op: " + (reservation.pickupDate || "onbekend");

        card.appendChild(title);
        card.appendChild(pickup);
        reservationList.appendChild(card);
    });
}

// Een boek reserveren (alleen als er een bookId in de URL staat).
async function reserveSelectedBook() {
    if (!bookId) {
        await loadReservations();
        return;
    }

    const response = await fetch(`${API}/reservations/${bookId}`, {
        method: "POST",
        credentials: "include"
    });

    if (response.status === 401) {
        message.textContent = "Log eerst in om te reserveren.";
        message.style.color = "red";
        return;
    }

    if (!response.ok) {
        message.textContent =
            "De reservering is niet gelukt (" + response.status + ").";
        message.style.color = "red";
        return;
    }

    // bookId uit de URL halen, zodat vernieuwen niet opnieuw reserveert.
    window.history.replaceState({}, "", window.location.pathname);
    await loadReservations();

    message.textContent = "Je boek is gereserveerd!";
    message.style.color = "green";
}

reserveSelectedBook();