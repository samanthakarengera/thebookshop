const API = "http://localhost:8080/api";

const bookDetails = document.getElementById("bookDetails");
const reviewList = document.getElementById("reviewList");
const averageScore = document.getElementById("averageScore");
const reviewMessage = document.getElementById("reviewMessage");
const reviewFormSection = document.getElementById("reviewFormSection");
const reviewForm = document.getElementById("reviewForm");

// Het boek-ID ophalen uit de URL
const parameters = new URLSearchParams(window.location.search);
const bookId = parameters.get("id");


const CATEGORY_LABELS = {
    THRILLER: "Thriller",
    COMEDIE: "Komedie",
    ROMAN: "Roman",
    SCIFI: "Sci-fi",
    FANTASY: "Fantasy"
};

// De details van het gekozen boek ophalen
async function loadBookDetails() {
    if (!bookId) {
        bookDetails.textContent = "Geen boek geselecteerd.";
        return;
    }

    try {
        const response = await fetch(`${API}/books/${bookId}`);

        if (!response.ok) {
            throw new Error("Boek niet gevonden.");
        }

        const book = await response.json();

        bookDetails.innerHTML = "";

        const title = document.createElement("h2");
        title.textContent = book.title;

        const author = document.createElement("p");
        author.textContent = "Auteur: " + book.authors;

        const category = document.createElement("p");
        category.textContent = "Categorie: " +
            (CATEGORY_LABELS[book.category] || "Niet opgegeven");

        const isbn = document.createElement("p");
        isbn.textContent = "ISBN: " + book.isbn;

        const description = document.createElement("p");
        description.textContent =
            book.description || "Geen beschrijving beschikbaar.";

        const price = document.createElement("p");
        price.textContent = "Prijs: €" + book.price;

        const stock = document.createElement("p");
        stock.textContent = "Beschikbare voorraad: " + book.stock;

        bookDetails.append(
            title,
            author,
            category,
            isbn,
            description,
            price,
            stock
        );

    } catch (error) {
        bookDetails.textContent =
            "Het boek kon niet worden geladen. Controleer of de backend actief is.";
    }
}

// De reviews van het boek ophalen.
async function loadReviews() {
    try {
        const response = await fetch(`${API}/books/${bookId}/reviews`);

        if (!response.ok) {
            throw new Error("Fout " + response.status);
        }

        const reviews = await response.json();
        displayReviews(reviews);

    } catch (error) {
        reviewMessage.textContent = "Kan de reviews niet laden.";
        reviewMessage.style.color = "red";
    }
}

// De reviews en de gemiddelde score tonen.
function displayReviews(reviews) {
    reviewList.innerHTML = "";

    if (reviews.length === 0) {
        averageScore.textContent = "";
        reviewList.textContent = "Er zijn nog geen reviews voor dit boek.";
        return;
    }

    const total = reviews.reduce(function (sum, review) {
        return sum + review.score;
    }, 0);

    averageScore.textContent =
        "Gemiddelde score: " + (total / reviews.length).toFixed(1) +
        " / 5 (" + reviews.length + " review(s))";

    reviews.forEach(function (review) {
        const card = document.createElement("div");
        card.className = "book-card";

        const header = document.createElement("h3");
        header.textContent = review.authorName + " - " + review.score + "/5";

        const text = document.createElement("p");
        text.textContent = review.text;

        const date = document.createElement("p");
        date.textContent =
            new Date(review.createdAt).toLocaleDateString("nl-BE");

        card.append(header, text, date);
        reviewList.appendChild(card);
    });
}

// Het formulier alleen tonen als de gebruiker ingelogd is en dit boek heeft gekocht.
async function checkReviewForm() {
    try {
        const response = await fetch(`${API}/reservations`, {
            credentials: "include"
        });

        if (!response.ok) {
            return;
        }

        const reservations = await response.json();

        const hasBook = reservations.some(function (reservation) {
            return reservation.book &&
                String(reservation.book.id) === String(bookId);
        });

        if (hasBook) {
            reviewFormSection.hidden = false;
        }

    } catch (error) {
        console.log("Kan de reservaties niet controleren.");
    }
}

// Een review versturen.
reviewForm.addEventListener("submit", async function (event) {
    event.preventDefault();

    const review = {
        score: Number(document.getElementById("score").value),
        text: document.getElementById("reviewText").value.trim()
    };

    try {
        const response = await fetch(`${API}/books/${bookId}/reviews`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(review)
        });

        if (response.status === 401) {
            reviewMessage.textContent = "Log eerst in om een review te schrijven.";
            reviewMessage.style.color = "red";
            return;
        }

        if (!response.ok) {
            const data = await response.json().catch(function () {
                return {};
            });
            throw new Error(
                data.message || "Review plaatsen mislukt (" + response.status + ")."
            );
        }

        reviewForm.reset();
        reviewFormSection.hidden = true;

        reviewMessage.textContent = "Bedankt voor je review!";
        reviewMessage.style.color = "green";

        await loadReviews();

    } catch (error) {
        reviewMessage.textContent = error.message;
        reviewMessage.style.color = "red";
    }
});

loadBookDetails();
loadReviews();
checkReviewForm();