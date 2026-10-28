package be.ehb.thebookshop.review;

/**
 * De gegevens die de frontend meestuurt om een review te plaatsen
 * @param score een geheel getal van 1 tot 5
 * @param text  de tekst van de review (maximaal 1000 tekens)
 */
public record ReviewRequest(Integer score, String text) {
}