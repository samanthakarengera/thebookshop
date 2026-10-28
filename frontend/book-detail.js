const bookDetails = document.getElementById("bookDetails");
const API = "http://localhost:8080/api";

// Het boek-ID ophalen uit de URL.
const parameters = new URLSearchParams(window.location.search);
const bookId = parameters.get("id");

// De details van het gekozen boek ophalen.
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

loadBookDetails();