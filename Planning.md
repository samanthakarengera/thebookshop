# Planning – The Bookshop

## 1. Doel

Het doel is om een eenvoudige webapplicatie voor een fictieve boekenwinkel te ontwikkelen. 
De planning houdt bij welke onderdelen al uitgewerkt zijn en welke nog moeten gebeuren.

## 2. Uitgevoerde taken

- [x] Een Java Maven-project aangemaakt in IntelliJ IDEA.
- [x] Spring Boot ingesteld en de applicatie lokaal opgestart.
- [x] De H2-database geconfigureerd.
- [x] Een model voor boeken aangemaakt.
- [x] Een repository gemaakt om boeken op te halen en op te slaan.
- [x] Een controller gemaakt voor de boeken-API.
- [x] Endpoints gemaakt om boeken op te vragen en boeken toe te voegen.
- [x] Een eerste opzet gemaakt voor gebruikersregistratie en authenticatie.
- [x] Een controller gemaakt voor de gebruikers.
- [x] Een repository gemaakt om gebruikers opslaan en op e-mail zoeken.
- [x] De projectstructuur en documentatie gemaakt.
- [x] Registratie testen en eventuele fouten oplossen.
- [x] Login correct laten werken en testen.
- [x] De registratiepagina afwerken.
- [x] Een loginpagina maken.
- [x] Beveiliging met Spring Security afgewerkt: sessies, CORS met cookies en een 401-antwoord wanneer de gebruiker niet is ingelogd.
- [x] Sessieproblemen opgelost (login bewaart de sessie, één host `localhost` voor frontend en backend).
- [x] Na registratie automatisch inloggen.
- [x] Reservatiefunctionaliteit in de backend (reserveren en eigen reservaties ophalen).
- [x] Boekendetailpagina gemaakt.
- [x] Pagina "Mijn reservaties" gemaakt.
- [x] Profielpagina gemaakt met gegevens van de gebruiker en zijn boeken.

## 3. Nog uit te voeren taken

### Backend

- [ ] Controleren welke API-endpoints toegankelijk moeten zijn voor ingelogde gebruikers (bv. `POST /api/books` beperken).
- [x] Functionaliteit maken om boeken te reserveren.
- [x] De voorraad verlagen wanneer een reservatie wordt gemaakt (met afhaaldatum als bevestiging).
- [ ] Functionaliteit voor reviews toevoegen.
- [x] Reservaties teruggeven
- [ ] Een echt logout-endpoint toevoegen.
- [ ] Invoer controleren en foutmeldingen verbeteren.
- [ ] Optioneel: adminrol en voorraadbeheer.
- [x] Optioneel: categorie per boek (thriller, comedy, roman, sci-fi).

### Frontend

- [x] Boeken ophalen uit de backend en weergeven op de website.
- [x] Een boekendetailpagina of detailweergave maken.
- [x] Een pagina met de reservaties van de gebruiker maken.
- [x] Een profielpagina maken.
- [ ] Reviews tonen op de detailpagina en laten schrijven vanuit het profiel.
- [ ] De uitlogknop de sessie laten beëindigen.
- [ ] De verschillende pagina's testen op een normale schermgrootte en op mobiel.
- [] Optioneel: categorie tonen op de detailpagina en filteren in de catalogus.

### Testen

- [ ] Controleren of boeken correct worden opgehaald en toegevoegd.
- [x] Controleren of registratie werkt met geldige en ongeldige gegevens.
- [x] Controleren of gebruikers kunnen inloggen.
- [ ] Controleren of een reservatie de voorraad correct aanpast.
- [ ] Minstens drie geautomatiseerde tests schrijven (bv. voorraad daalt, geen voorraad geeft fout, onbekend boek geeft fout).
- [ ] Controleren of fouten op een duidelijke manier worden weergegeven.
- [ ] De belangrijkste testresultaten documenteren.

### Afronding

- [ ] Documentatie toevoegen: Javadoc bij klassen en methodes en een beschrijving van de endpoints.
- [ ] De README en de planning bijwerken.
- [x] De gebruikte bronnen en AI-hulpmiddelen vermelden.
- [ ] De projectstructuur en bestanden controleren (één hoofdmap met backend en frontend als aparte projecten).
- [ ] Controleren of het project vanaf een nieuwe installatie kan worden opgestart.
- [ ] De finale versie naar GitHub pushen en de indiening controleren.

## 4. Werkwijze

Het project wordt stapsgewijs ontwikkeld. Eerst wordt de backend uitgewerkt en getest. Daarna worden de frontend en de koppeling met de API verder afgewerkt. Vervolgens worden de belangrijkste functionaliteiten getest en wordt de documentatie aangevuld.

De takenlijst wordt tijdens het project bijgewerkt. Taken worden pas als voltooid aangeduid wanneer ze daadwerkelijk uitgevoerd en gecontroleerd zijn.