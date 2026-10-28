# The Bookshop

The Bookshop is een webapplicatie voor een fictieve boekenwinkel. Het doel is om een eenvoudige online boekenwinkel te maken waar gebruikers boeken kunnen bekijken en uiteindelijk kunnen reserveren. Hierbij kunnen gebruiker ook reviews schrijven over boeken die ze hebben gekocht. Het project bestaat uit een backend in Java met Spring Boot en een frontend met HTML, CSS en JavaScript. De frontend communiceert met de backend via een REST API.

## Gebruikte technologieën

- Java en Spring Boot: voor de backend en de API.
- Maven: voor het beheren van de Java-afhankelijkheden.
- Spring Data JPA: voor de koppeling met de database.
- H2 Database: voor het bewaren van gegevens tijdens het ontwikkelen.
- Spring Security: voor de beveiliging en authenticatie (sessies met cookies).
- HTML en CSS: voor de structuur en vormgeving van de website.
- JavaScript: voor de interactie met de backend.
- IntelliJ IDEA: voor de backend.
- Visual Studio Code: voor de frontend.
- GitHub: voor versiebeheer en het indienen van het project.

## Projectstructuur

De backend en frontend worden afzonderlijk ontwikkeld.

Backend: bevat de Java-code, controllers, repositories, configuratie en andere onderdelen van Spring Boot.

Frontend: bevat de HTML-, CSS- en JavaScript-bestanden van de website.

Documentatie: bevat de README, planning, testresultaten en informatie over gebruikte bronnen en AI-tools.

## Installatie en uitvoeren

### Backend

1. Open het Spring Boot-project (map `backend`) in IntelliJ IDEA, met JDK 17 of hoger (zie `pom.xml`).
2. Wacht tot Maven de benodigde afhankelijkheden heeft ingeladen.
3. Start de applicatie via `ThebookshopApplication.java`.
4. De backend is bereikbaar via `http://localhost:8080`.
5. Bij het opstarten worden voorbeeldboeken geladen uit `backend/src/main/resources/books.json`, maar alleen als de catalogus leeg is.

### Frontend

1. Open de map `thebookshop` (of `frontend`) in Visual Studio Code.
2. Start de extensie Live Server en open `http://localhost:5500/frontend/login.html` of `http://localhost:5500/frontend/register`.html.
4. Zorg ervoor dat de backend actief is wanneer de frontend gegevens opvraagt.


## Functionaliteiten

### Uitgewerkt

- Een catalogus met boeken (ISBN, titel, auteur(s), omschrijving, prijs, voorraad en categorie), met zoeken op titel of auteur en filteren op categorie.
- Een detailpagina per boek met de reviews en de gemiddelde score.
- Registreren en inloggen. Na registratie ben je meteen ingelogd.
- Een profielpagina met de gegevens van de gebruiker en zijn boeken.
- Boeken reserveren, waarbij de voorraad in de catalogus automatisch daalt en de gebruiker een afhaaldatum krijgt.
- Reviews met score en tekst. Dat kan alleen voor een boek dat je hebt gereserveerd en maar één keer per boek.
- Unit tests en een testrapport met handmatige testscenario's.


## API-endpoints

| Methode | Pad | Toegang | Beschrijving |
|---|---|---|---|
| POST | `/api/users/register` | open | Maakt een account aan. Body: `name`, `email`, `password` |
| POST | `/api/users/login` | open | Logt in en geeft een sessiecookie. Body: `email`, `password`. 401 bij foute gegevens |
| GET | `/api/users/me` | ingelogd | Id, naam en e-mailadres van de ingelogde gebruiker |
| GET | `/api/books` | open | Alle boeken met voorraad en categorie |
| GET | `/api/books/{id}` | open | Eén boek. 404 als het niet bestaat |
| POST | `/api/books` | open | Voegt een boek toe. 400 bij ongeldige gegevens, 409 bij een bestaand ISBN |
| GET | `/api/books/{bookId}/reviews` | open | Reviews van een boek, de nieuwste eerst |
| POST | `/api/books/{bookId}/reviews` | ingelogd | Plaatst een review. Body: `score` (1 tot 5), `text`. 400, 403 (niet gekocht), 404, 409 (al beoordeeld) |
| POST | `/api/reservations/{bookId}` | ingelogd | Reserveert een boek en verlaagt de voorraad. 404, of 409 bij geen voorraad |
| GET | `/api/reservations` | ingelogd | Reservaties van de ingelogde gebruiker |

## Ontwerpkeuzes en design patterns

- **Gelaagde opbouw (controller, service, repository):** controllers regelen HTTP, services bevatten de regels en repositories praten met de database.
- **Repository-patroon (DAO):** Spring Data JPA-repositories zijn de enige toegang tot de database.
- **Dependency injection (inversion of control):** Spring geeft de afhankelijkheden mee via de constructor, waardoor services in tests met mocks vervangen kunnen worden.
- **DTO's:** aparte klassen voor wat de API binnenkrijgt en teruggeeft, bijvoorbeeld voor reviews en de gegevens van de gebruiker.
- **Sessies met cookies:** ingelogde gebruikers krijgen een sessiecookie (JSESSIONID). CSRF-bescherming staat bewust uit omdat de API met JSON werkt en er geen serverformulieren zijn.
- **Niet ingelogd geeft 401:** zo kan de frontend onderscheid maken tussen "niet ingelogd" en "niet toegestaan".

## Testen

De tests staan beschreven in [testing.md](testing.md):

- Handmatige testscenario's (registratie, login, catalogus, reserveren, voorraad, reviews, profiel en mobiele weergave) met stappen, verwacht resultaat, werkelijk resultaat en de uitkomst.

## Bekende beperkingen

- De database is in-memory, dus gegevens verdwijnen bij een herstart van de backend.

## Bronnen

ChatGPT chat (AI-tool): https://chatgpt.com/share/6adfe962-c23c-83ed-9c34-da25642ba66e

Tutorial: https://blog.jetbrains.com/idea/2024/03/getting-started-with-databases-in-intellij-idea/

Google Gemini: debuggen van beveiliging (reserveerknop), opsporen van 403/401-fout


Spring Boot-documentatie: https://spring.io/projects/spring-boot

H2 Database-documentatie: https://www.h2database.com/

Spring Data JPA-documentatie: https://spring.io/projects/spring-data-jpa

Spring Security-documentatie: https://spring.io/projects/spring-security

## Planning

De voortgang en de resterende taken worden bijgehouden in een afzonderlijk bestand PLANNING.md.