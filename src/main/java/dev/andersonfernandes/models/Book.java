package dev.andersonfernandes.models;

import dev.andersonfernandes.models.utils.ValidationRule;

import java.util.ArrayList;

public class Book extends Material {
    private BookType bookType;
    private String subject;
    private String genre;

    public Book() {
        this.materialType = MaterialType.BOOK;
    }

    public Book(Long id, String title, String publisher, Integer year, Integer quantity, BookType bookType, String subject, String genre) {
        super(id, title, publisher, year, quantity, MaterialType.BOOK);
        this.bookType = bookType;
        this.subject = subject;
        this.genre = genre;
    }

    @Override
    protected ArrayList<ValidationRule> validationRules() {
        ArrayList<ValidationRule> rules = baseValidationRules();

        rules.add(() -> this.bookType == null ? "Tipo do livro deve estar presente" : null);
        rules.add(() -> {
            boolean condition = this.bookType != null &&
                    this.bookType.equals(BookType.TEXTBOOK) &&
                    (this.subject == null || this.subject.isEmpty());
            return condition ? "Disciplina do livro deve estar presente quando seu tipo for didático" : null;
        });
        rules.add(() -> {
            boolean condition = this.bookType != null &&
                    this.bookType.equals(BookType.OTHER) &&
                    (this.genre == null || this.genre.isEmpty());
            return condition ? "Gênero do livro deve estar presente quando seu tipo for paradidático" : null;
        });

        return rules;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
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
