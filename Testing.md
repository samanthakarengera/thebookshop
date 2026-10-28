# Testrapport – The Bookshop

## Testomgeving

- Backend gestart in IntelliJ, bereikbaar op `http://localhost:8080`.
- Frontend gestart met Live Server in VS Code, geopend via `http://localhost:5500/frontend/...`
- Browser: Chrome
- Datum van testen: 28 oktober 2026

## Testdata

- De backend is vlak voor de tests herstart, zodat de database leeg start en de voorbeeldboeken uit `books.json` geladen zijn.
- Testaccount 1: naam `Test Gebruiker`, e-mail `test@test.be`, wachtwoord `wachtwoord123`.
- Boeken die gebruikt worden: *Dune* (voorraad 6), *1984* (nooit gereserveerd) en *The Silent Patient* (voorraad 0).

## Handmatige tests

### T1 – Registreren met geldige gegevens
- **Stappen:**
  1. Open `http://localhost:5500/frontend/index.html`.
  2. Vul naam, e-mailadres en een wachtwoord van minstens 8 tekens in.
  3. Klik op "Registreer".
- **Verwacht resultaat:** Het account wordt aangemaakt, je bent meteen ingelogd en komt op `books.html` met bovenaan "Welkom, Test Gebruiker!".
- **Werkelijk resultaat:** Zoals verwacht: account aangemaakt, direct ingelogd en doorgestuurd naar `books.html` met de welkomstboodschap.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T2 – Registreren met een bestaand e-mailadres
- **Stappen:**
  1. Log uit en open `index.html`.
  2. Registreer opnieuw met `test@test.be`.
- **Verwacht resultaat:** Er verschijnt een rode foutmelding en er komt geen tweede account bij (in de H2-console toont `SELECT * FROM USERS;` één rij met dit e-mailadres).
- **Werkelijk resultaat:** Zoals verwacht: foutmelding getoond en geen tweede account aangemaakt.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T3 – Registreren met een te kort wachtwoord
- **Stappen:**
  1. Open `index.html`.
  2. Vul een geldige naam en e-mail in met wachtwoord `abc`.
  3. Klik op "Registreer".
- **Verwacht resultaat:** De browser blokkeert het formulier met een melding over het minimum van 8 tekens. In het tabblad Network (DevTools) is geen request naar de backend verstuurd.
- **Werkelijk resultaat:** Zoals verwacht: de browser blokkeert het formulier en er wordt geen request verstuurd.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T4 – Inloggen met geldige gegevens
- **Stappen:**
  1. Open `login.html`.
  2. Log in met `test@test.be` en `wachtwoord123`.
- **Verwacht resultaat:** Je wordt doorgestuurd naar `books.html` met "Welkom, Test Gebruiker!". In DevTools → Application → Cookies → `http://localhost:8080` staat een `JSESSIONID`.
- **Werkelijk resultaat:** Zoals verwacht: doorgestuurd naar `books.html` met de welkomstboodschap, `JSESSIONID`-cookie aanwezig.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T5 – Inloggen met een fout wachtwoord
- **Stappen:**
  1. Open `login.html`.
  2. Log in met `test@test.be` en een verkeerd wachtwoord.
- **Verwacht resultaat:** Een rode melding dat het e-mailadres of wachtwoord niet klopt. Je blijft op de loginpagina.
- **Werkelijk resultaat:** Zoals verwacht: rode foutmelding en je blijft op de loginpagina.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T6 – Uitloggen beëindigt de sessie
- **Stappen:**
  1. Log in en klik op "Log uit".
  2. Open `http://localhost:8080/api/users/me` in de browser.
- **Verwacht resultaat:** De pagina geeft HTTP 401 en geen gebruikersgegevens.
- **Werkelijk resultaat:** Zoals verwacht: HTTP 401, geen gebruikersgegevens.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt
- **Hertest na de logoutfix (via de link in de navigatiebalk):** <invullen>

### T7 – Catalogus toont alle boeken
- **Stappen:**
  1. Log in en open `books.html`.
- **Verwacht resultaat:** Alle boeken uit `books.json` staan op de pagina met titel, auteur, categorie, prijs en voorraad, en de teller toont "20 boek(en) gevonden." (pas het getal aan als je een ander aantal boeken hebt).
- **Werkelijk resultaat:** Zoals verwacht: alle boeken met hun gegevens en de juiste teller.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T8 – Zoeken op titel of auteur
- **Stappen:**
  1. Typ `tolkien` in het zoekveld.
  2. Wis het veld en typ `xyz`.
- **Verwacht resultaat:** Bij `tolkien` blijft alleen *The Hobbit* over. Bij `xyz` staat er "Geen boeken gevonden."
- **Werkelijk resultaat:** Zoals verwacht: alleen *The Hobbit* bij `tolkien` en "Geen boeken gevonden." bij `xyz`.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T9 – Filteren op categorie
- **Stappen:**
  1. Kies in het menu "Categorie" de waarde "Sci-fi".
  2. Kies daarna "Thriller" en typ `gone` in het zoekveld.
- **Verwacht resultaat:** Bij Sci-fi blijven alleen sci-fiboeken over (5 in de voorbeeldgegevens). Bij Thriller met zoekterm `gone` blijft alleen *Gone Girl* over.
- **Werkelijk resultaat:** Zoals verwacht: het filter toont de juiste boeken en werkt samen met het zoekveld.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T10 – Detailpagina van een boek
- **Stappen:**
  1. Klik bij *Dune* op "Bekijk details".
- **Verwacht resultaat:** De pagina toont titel, auteur, categorie "Sci-fi", ISBN, omschrijving, prijs en voorraad van *Dune*.
- **Werkelijk resultaat:** Zoals verwacht: alle gegevens van *Dune* worden getoond.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T11 – Detailpagina van een onbestaand boek
- **Stappen:**
  1. Open `http://localhost:5500/frontend/book-detail.html?id=999`.
- **Verwacht resultaat:** Er staat een foutmelding dat het boek niet kon worden geladen, en de pagina crasht niet.
- **Werkelijk resultaat:** Zoals verwacht: foutmelding, de pagina crasht niet.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T12 – Een boek reserveren
- **Stappen:**
  1. Log in en noteer de voorraad van *Dune* (6).
  2. Klik bij *Dune* op "Reserveer".
- **Verwacht resultaat:** Je gaat naar `reservations.html`, daar staat "Je boek is gereserveerd!" en *Dune* staat in de lijst met een afhaaldatum twee dagen later.
- **Werkelijk resultaat:** Zoals verwacht: *Dune* is gereserveerd en staat in de lijst met afhaaldatum.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T13 – De voorraad daalt na een reservatie
- **Stappen:**
  1. Ga na T12 terug naar `books.html`.
  2. Bekijk de voorraad van *Dune*, en eventueel in de H2-console.
- **Verwacht resultaat:** De voorraad van *Dune* is 5, dus één minder dan voor de reservatie.
- **Werkelijk resultaat:** Zoals verwacht: de voorraad is met één gedaald.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T14 – Reserveren zonder ingelogd te zijn
- **Stappen:**
  1. Log uit.
  2. Open `books.html` en klik bij een boek op "Reserveer".
- **Verwacht resultaat:** Op `reservations.html` staat "Log eerst in om te reserveren." en er is geen reservatie aangemaakt.
- **Werkelijk resultaat:** Zoals verwacht: melding om eerst in te loggen en geen reservatie aangemaakt.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T15 – Beveiligde API zonder login geeft 401
- **Stappen:**
  1. Log uit.
  2. Open `http://localhost:8080/api/reservations` in de browser.
- **Verwacht resultaat:** De pagina geeft HTTP 401. Je krijgt geen 403 en geen reservaties te zien.
- **Werkelijk resultaat (eerste run):** <invullen: wat zag je op de pagina, bijvoorbeeld de JSON met je reservaties of een andere foutcode?>
- **Geslaagd of mislukt:** ☐ Geslaagd ☒ Mislukt
- **Oorzaak en oplossing:** <invullen na de check: de "Log uit"-link in de navigatiebalk beëindigde de sessie niet. Opgelost met een logout-endpoint en `logout.js`.>
- **Hertest na de fix:** <invullen> ☐ Geslaagd ☐ Mislukt

### T16 – Boek zonder voorraad kan niet gereserveerd worden
- **Stappen:**
  1. Open `books.html` en zoek *The Silent Patient* (voorraad 0).
- **Verwacht resultaat:** De knop staat op "Niet in voorraad" en kan niet worden aangeklikt.
- **Werkelijk resultaat:** Zoals verwacht: de knop is uitgeschakeld en toont "Niet in voorraad".
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T17 – Een review plaatsen na een reservatie
- **Stappen:**
  1. Log in met het account dat *Dune* heeft gereserveerd.
  2. Open de detailpagina van *Dune*.
  3. Kies score 5, schrijf "Geweldig boek" en klik op "Plaats review".
- **Verwacht resultaat:** Er staat "Bedankt voor je review!", de review verschijnt met de naam van de schrijver, 5/5 en de datum, en er staat "Gemiddelde score: 5.0 / 5 (1 review(s))".
- **Werkelijk resultaat:** Zoals verwacht: de review is geplaatst en verschijnt met de gemiddelde score.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T18 – Geen reviewformulier zonder gekocht boek
- **Stappen:**
  1. Open de detailpagina van *1984* (niet gereserveerd) terwijl je ingelogd bent.
  2. Log uit en open de detailpagina van *Dune*.
- **Verwacht resultaat:** Bij *1984* staat geen formulier "Schrijf een review". Uitgelogd zijn de reviews van *Dune* wel zichtbaar, maar er is geen formulier.
- **Werkelijk resultaat:** Zoals verwacht: geen formulier bij *1984* en uitgelogd zijn de reviews zichtbaar zonder formulier.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T19 – Twee reviews voor hetzelfde boek
- **Stappen:**
  1. Log in en open de detailpagina van *Dune* opnieuw (ververs de pagina).
  2. Probeer een tweede review te plaatsen.
- **Verwacht resultaat:** Er verschijnt een rode melding dat je dit boek al hebt beoordeeld, en er blijft maar één review staan.
- **Werkelijk resultaat:** Zoals verwacht: rode melding en er blijft één review staan.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T20 – Profielpagina
- **Stappen:**
  1. Log in en open `profile.html`.
- **Verwacht resultaat:** De pagina toont naam en e-mailadres, en bij "Mijn boeken" staat *Dune*.
- **Werkelijk resultaat:** Zoals verwacht: naam, e-mailadres en *Dune* bij "Mijn boeken".
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### T21 – Profielpagina zonder login
- **Stappen:**
  1. Log uit.
  2. Open `profile.html`.
- **Verwacht resultaat:** Je wordt doorgestuurd naar `login.html`.
- **Werkelijk resultaat (eerste run):** De profielpagina bleef getoond en er was geen doorverwijzing naar `login.html`.
- **Geslaagd of mislukt:** ☐ Geslaagd ☒ Mislukt
- **Oorzaak en oplossing:** <invullen na de check: de sessie was nog actief omdat de "Log uit"-link de sessie niet beëindigde. Opgelost met een logout-endpoint en `logout.js`.>
- **Hertest na de fix:** <invullen> ☐ Geslaagd ☐ Mislukt

### T22 – Weergave op mobiel
- **Stappen:**
  1. Open DevTools en zet de apparaatweergave aan (Ctrl+Shift+M), breedte 375 px.
  2. Bekijk `books.html`, `book-detail.html`, `reservations.html` en `profile.html`.
- **Verwacht resultaat:** Er is geen horizontale scrollbalk, de tekst is leesbaar en alle knoppen zijn bruikbaar.
- **Werkelijk resultaat:** Zoals verwacht: geen horizontale scrollbalk, tekst leesbaar en knoppen bruikbaar.
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

## Unit tests

Bestand: `backend/src/test/java/be/ehb/thebookshop/reservation/ReservationServiceTest.java`.
Uitvoeren in IntelliJ met rechtsklikken op de klasse en **Run**, of met `mvnw.cmd test` in de map `backend`.

### U1 – Reserveren verlaagt de voorraad met één
- **Stappen:** Voer de test `reserveBook_verlaagtDeVoorraadMetEen` uit. Een boek met voorraad 3 wordt gereserveerd.
- **Verwacht resultaat:** De voorraad wordt op 2 gezet, het boek wordt opgeslagen en de reservatie heeft status `RESERVED`.
- **Werkelijk resultaat:** Zoals verwacht: de test is geslaagd (groen).
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### U2 – Reserveren zonder voorraad geeft een fout
- **Stappen:** Voer `reserveBook_zonderVoorraad_geeftConflictEnSlaatNietsOp` uit. Het boek heeft voorraad 0.
- **Verwacht resultaat:** Er komt een `ResponseStatusException` met status 409 (Conflict). Het boek en de reservatie worden niet opgeslagen.
- **Werkelijk resultaat:** Zoals verwacht: de test is geslaagd (groen).
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### U3 – Reserveren van een onbekend boek
- **Stappen:** Voer `reserveBook_onbekendBoek_geeftNotFound` uit met boek-id 99.
- **Verwacht resultaat:** Status 404 (Not Found) en er wordt niets opgeslagen.
- **Werkelijk resultaat:** Zoals verwacht: de test is geslaagd (groen).
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### U4 – Reserveren met een onbekende gebruiker
- **Stappen:** Voer `reserveBook_onbekendeGebruiker_geeftNotFound` uit.
- **Verwacht resultaat:** Status 404 (Not Found) en er wordt niets opgeslagen.
- **Werkelijk resultaat:** Zoals verwacht: de test is geslaagd (groen).
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

### U5 – Nieuwe reservatie heeft status en afhaaldatum
- **Stappen:** Voer `nieuweReservatie_heeftStatusReservedEnAfhaaldatumOverTweeDagen` uit.
- **Verwacht resultaat:** De status is `RESERVED` en de afhaaldatum is twee dagen na vandaag.
- **Werkelijk resultaat:** Zoals verwacht: de test is geslaagd (groen).
- **Geslaagd of mislukt:** ☒ Geslaagd ☐ Mislukt

## Samenvatting

- Handmatige tests, eerste run: 20 geslaagd, 2 mislukt (T15 en T21). Na de logoutfix: <aantal> geslaagd, <aantal> mislukt.
- Unit tests: 5 geslaagd, 0 mislukt.
- Gevonden fouten en hoe ze zijn opgelost: T15 en T21 mislukten bij de eerste run omdat <oorzaak invullen na de check>. Opgelost met <oplossing invullen>.