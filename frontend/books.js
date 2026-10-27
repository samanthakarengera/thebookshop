const bookList = document.getElementById("bookList");
const searchInput = document.getElementById("searchInput");
const bookCount = document.getElementById("bookCount");
const message = document.getElementById("message");
const welcomeMessage = document.getElementById("welcomeMessage");
const logoutButton = document.getElementById("logoutButton");

let books = [];

// De boeken ophalen uit de backend.
async function loadBooks() {
    try {
        const response = await fetch("http://localhost:8080/api/books");

        if (!response.ok) {
            throw new Error("Kan de boeken niet laden.");
        }

        books = await response.json();

        displayBooks(books);

    } catch (error) {
        message.textContent =
            "Kan de boeken niet laden. Probeer opnieuw.";
        message.style.color = "red";
    }
}

// De boeken op de pagina tonen.
function displayBooks(bookArray) {
    bookList.innerHTML = "";
    bookCount.textContent = bookArray.length + " boek(en) gevonden.";

    if (bookArray.length === 0) {
        bookList.textContent = "Geen boeken gevonden.";
        return;
    }

    bookArray.forEach(function (book) {
        const card = document.createElement("div");
        card.className = "book-card";

        const title = document.createElement("h3");
        title.textContent = book.title;

        const author = document.createElement("p");
        author.textContent = "Auteur: " + book.authors;

        const description = document.createElement("p");
        description.textContent =
            book.description || "Geen beschrijving.";

        const price = document.createElement("p");
        price.textContent = "Prijs: €" + book.price;

        const stock = document.createElement("p");
        stock.textContent = "Beschikbaar: " + book.stock;

        card.appendChild(title);
        card.appendChild(author);
        card.appendChild(description);
        card.appendChild(price);
        card.appendChild(stock);

        bookList.appendChild(card);
    });
}

// Zoeken op titel of auteur.
searchInput.addEventListener("input", function () {
    const searchTerm = searchInput.value.toLowerCase().trim();

    const filteredBooks = books.filter(function (book) {
        return (
            (book.title || "").toLowerCase().includes(searchTerm) ||
            (book.authors || "").toLowerCase().includes(searchTerm)
        );
    });

    displayBooks(filteredBooks);
});

async function loadUser() {
    try {
        const response = await fetch(
            "http://localhost:8080/api/users/me",
            {
                credentials: "include"
            }
        );

        if (response.ok) {
            const user = await response.json();
            welcomeMessage.textContent =
                "Welkom, " + user.name + "!";
        }
    } catch (error) {
        console.log("Kan gebruiker niet laden.");
    }
}

// Voorlopig teruggaan naar de loginpagina.
logoutButton.addEventListener("click", function () {
    window.location.href = "login.html";
});

loadBooks();
loadUser();