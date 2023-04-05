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

        rules.add(() -> this.type == null ? "Tipo do livro deve estar presente" : null);
        rules.add(() -> {
            boolean condition = this.type != null &&
                    this.type.equals(BookType.TEXTBOOK) &&
                    (this.subject == null || this.subject.isEmpty());
            return condition ? "Disciplina do livro deve estar presente quando seu tipo for didático" : null;
        });
        rules.add(() -> {
            boolean condition = this.type != null &&
                    this.type.equals(BookType.OTHER) &&
                    (this.genre == null || this.genre.isEmpty());
            return condition ? "Gênero do livro deve estar presente quando seu tipo for paradidático" : null;
        });

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
