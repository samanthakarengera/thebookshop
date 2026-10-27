
const reservationList = document.getElementById("reservationList");
const message = document.getElementById("message");

async function loadReservations() {
    try {
        const response = await fetch(
            "http://localhost:8080/api/reservations",
            {
                credentials: "include"
            }
        );

        if (!response.ok) {
            message.textContent =
                "Kan de reserveringen niet laden. Log in.";
            return;
        }

        const reservations = await response.json();

        if (reservations.length === 0) {
            reservationList.textContent = "U heeft nog niks gereserveerd.";
            return;
        }

        reservations.forEach(function (reservation) {
            const card = document.createElement("div");
            card.className = "book-card";

            const title = document.createElement("h3");
            title.textContent = reservation.book.title;

            const pickup = document.createElement("p");
            pickup.textContent =
                "Ophaaldatum: " + reservation.pickupDate;

            const status = document.createElement("p");
            status.textContent = "Status: " + reservation.status;

            card.appendChild(title);
            card.appendChild(pickup);
            card.appendChild(status);

            reservationList.appendChild(card);
        });

    } catch (error) {
        message.textContent =
            "Lukt niet.";
    }
}

loadReservations();
