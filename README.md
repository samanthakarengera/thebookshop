## The Bookshop 

The Bookshop is een webapplicatie voor een fictieve boekenwinkel. Het doel is om een eenvoudige online boekenwinkel te maken waar gebruikers boeken kunnen bekijken en uiteindelijk kunnen reserveren. Het project bestaat uit een backend in Java met Spring Boot en een frontend met HTML, CSS en JavaScript. De frontend communiceert met de backend via een API.



## Gebruikte Technologien

- Java en Spring Boot: voor de backend en de API.
- Maven: voor het beheren van de Java-afhankelijkheden.
- Spring Data JPA: voor de koppeling met de database.
- H2 Database: voor het bewaren van gegevens tijdens het ontwikkelen.
- Spring Security: voor de beveiliging en authenticatie.
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

Open het Spring Boot-project in IntelliJ IDEA.

Wacht tot Maven de benodigde afhankelijkheden heeft ingeladen.

Start de applicatie via ThebookshopApplication.java.

De backend is bereikbaar via http://localhost:8080.

De H2-databaseconsole is beschikbaar via http://localhost:8080/h2-console, als de applicatie actief is.

De database-instellingen zijn terug te vinden in src/main/resources/application.properties.

### Frontend

Open de map frontend in Visual Studio Code.

Open index.html met de extensie Live Server.

Zorg ervoor dat de backend actief is wanneer de frontend gegevens opvraagt.

## Functionaliteiten

De applicatie wordt stap voor stap ontwikkeld.

### Momenteel uitgewerkt

Een backend met Spring Boot.

Een boekenmodel met onder andere ISBN, titel, auteur, omschrijving, prijs en voorraad.

Een API om boeken op te vragen en een boek op basis van het ID op te zoeken.

Een API om een boek toe te voegen.


### Nog verder uit te werken

Een eerste opzet voor gebruikersregistratie en authenticatie.

Een frontend voor gebruikersregistratie met een eigen vormgeving.

Een loginpagina en het testen van de authenticatie.

Het weergeven van boeken op de website.

Het reserveren van boeken door ingelogde gebruikers.

Het verwerken van voorraadwijzigingen bij reservaties.

Het toevoegen en bekijken van reviews.

Het testen en verbeteren van de applicatie.

De status van deze functionaliteiten wordt bijgewerkt naarmate de ontwikkeling vordert.

## Testen

Tijdens de ontwikkeling worden de functionaliteiten getest. Dit gebeurt bijvoorbeeld door de API-endpoints uit te proberen, formulieren in te vullen en na te gaan of gegevens correct worden opgeslagen.

Eventuele geautomatiseerde tests en hun resultaten worden toegevoegd zodra ze zijn uitgewerkt.

## Bronnen

ChatGPT chat (AI-tool): https://chatgpt.com/share/6adfe962-c23c-83ed-9c34-da25642ba66e

https://blog.jetbrains.com/idea/2024/03/getting-started-with-databases-in-intellij-idea/

Spring Boot-documentatie: https://spring.io/projects/spring-boot

H2 Database-documentatie: https://www.h2database.com/

Spring Data JPA-documentatie: https://spring.io/projects/spring-data-jpa

Spring Security-documentatie: https://spring.io/projects/spring-security

## Planning

De voortgang en de resterende taken worden bijgehouden in een afzonderlijk bestand PLANNING.md.
