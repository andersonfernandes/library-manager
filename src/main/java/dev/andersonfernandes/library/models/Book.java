package dev.andersonfernandes.library.models;

import dev.andersonfernandes.library.models.utils.ValidationRule;

import java.util.ArrayList;

public class Book extends Material {
    private BookType type;
    private String subject;
    private String genre;

    public Book() {}

    public Book(String title, String publisher, Integer year, Integer quantity, BookType type, String subject, String genre) {
        super(title, publisher, year, quantity);
        this.type = type;
        this.subject = subject;
        this.genre = genre;
    }

    @Override
    protected ArrayList<ValidationRule> validationRules() {
        ArrayList<ValidationRule> rules = baseValidationRules();

        rules.add(() -> this.title.length() < 4 ? "Título deve possuir no mínimo 4 caracteres" : null);
//        TODO: Add book specific validations

        return rules;
    }

    public BookType getType() {
        return type;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
