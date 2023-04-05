package dev.andersonfernandes.library.models;

import dev.andersonfernandes.library.models.utils.ValidationRule;

import java.util.ArrayList;

public class Magazine extends Material {
    private String isbn;
    private String volume;
    private String edition;

    public Magazine() {}

    public Magazine(String title, String publisher, Integer year, Integer quantity, String isbn, String volume, String edition) {
        super(title, publisher, year, quantity);
        this.isbn = isbn;
        this.volume = volume;
        this.edition = edition;
    }

    @Override
    protected ArrayList<ValidationRule> validationRules() {
        ArrayList<ValidationRule> rules = baseValidationRules();

        rules.add(() -> this.isbn == null || this.isbn.isEmpty() ? "ISBN deve estar presente" : null);
        rules.add(() -> this.volume == null || this.volume.isEmpty() ? "Volume deve estar presente" : null);
        rules.add(() -> this.edition == null || this.edition.isEmpty() ? "Edição deve estar presente" : null);

        return rules;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}
