
const bookList = document.getElementById("bookList");
const searchInput = document.getElementById("searchInput");
const categoryFilter = document.getElementById("categoryFilter");
const bookCount = document.getElementById("bookCount");
const message = document.getElementById("message");
const welcomeMessage = document.getElementById("welcomeMessage");
const logoutButton = document.getElementById("logoutButton");
const API = "http://localhost:8080/api";

const CATEGORY_LABELS = {
    THRILLER: "Thriller",
    COMEDIE: "Komedie",
    ROMAN: "Roman",
    SCIFI: "Sci-fi",
    FANTASY: "Fantasy"
};

let books = [];

// De categorieën in het keuzemenu zetten
function fillCategoryFilter() {
    Object.keys(CATEGORY_LABELS).forEach(function (key) {
        const option = document.createElement("option");
        option.value = key;
        option.textContent = CATEGORY_LABELS[key];
        categoryFilter.appendChild(option);
    });
}

// De boeken ophalen uit de backend
async function loadBooks() {
    try {
        const response = await fetch(`${API}/books`);

        if (!response.ok) {
            throw new Error("Kan de boeken niet laden.");
        }

        books = await response.json();

        applyFilters();

    } catch (error) {
        message.textContent =
            "Kan de boeken niet laden. Probeer opnieuw.";
        message.style.color = "red";
    }
}

// Zoeken op titel of auteur en filteren op categorie.
function applyFilters() {
    const searchTerm = searchInput.value.toLowerCase().trim();
    const category = categoryFilter.value;

    const filteredBooks = books.filter(function (book) {
        const matchesSearch =
            (book.title || "").toLowerCase().includes(searchTerm) ||
            (book.authors || "").toLowerCase().includes(searchTerm);

        const matchesCategory =
            category === "" || book.category === category;

        return matchesSearch && matchesCategory;
    });

    displayBooks(filteredBooks);
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

        const category = document.createElement("p");
        category.textContent = "Categorie: " +
            (CATEGORY_LABELS[book.category] || "Niet opgegeven");

        const description = document.createElement("p");
        description.textContent =
            book.description || "Geen beschrijving beschikbaar.";

        const price = document.createElement("p");
        price.textContent = "Prijs: €" + book.price;

        const stock = document.createElement("p");
        stock.textContent = "Voorraad: " + book.stock;

        const detailsButton = document.createElement("button");
        detailsButton.textContent = "Bekijk details";
        detailsButton.addEventListener("click", function () {
            window.location.href = "book-detail.html?id=" + book.id;
        });

        const reserveButton = document.createElement("button");
        reserveButton.textContent = "Reserveer";

        if (book.stock <= 0) {
            reserveButton.disabled = true;
            reserveButton.textContent = "Niet in voorraad";
        }

        reserveButton.addEventListener("click", function () {
            window.location.href = "reservations.html?bookId=" + book.id;
        });

        card.append(
            title,
            author,
            category,
            //description
            price,
            stock,
            detailsButton,
            reserveButton
        );

        bookList.appendChild(card);
    });
}

// De naam van de ingelogde gebruiker tonen.
async function loadUser() {
    try {
        const response = await fetch(`${API}/users/me`, {
            credentials: "include"
        });

        if (response.ok) {
            const user = await response.json();
            welcomeMessage.textContent = 
                "Welkom, " + user.name + "!";
        }
    } catch (error) {
        console.log("Kan gebruiker niet laden.");
    }
}

searchInput.addEventListener("input", applyFilters);
categoryFilter.addEventListener("change", applyFilters);

// Voorlopig teruggaan naar de loginpagina.
logoutButton.addEventListener("click", function () {
    window.location.href = "login.html";
});

fillCategoryFilter();
loadBooks();
loadUser();