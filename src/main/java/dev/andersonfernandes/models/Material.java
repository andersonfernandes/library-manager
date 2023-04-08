package dev.andersonfernandes.models;

import dev.andersonfernandes.models.utils.Record;
import dev.andersonfernandes.models.utils.ValidationRule;

import java.util.ArrayList;

public abstract class Material extends Record {
    public static final String TABLE_NAME = "materials";

    protected String title;
    protected String publisher;
    protected Integer year;
    protected Integer quantity;
    protected MaterialType materialType;

    public Material() {}

    public Material(Long id, String title, String publisher, Integer year, Integer quantity, MaterialType materialType) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.year = year;
        this.quantity = quantity;
        this.materialType = materialType;
    }

    protected ArrayList<ValidationRule> baseValidationRules() {
        ArrayList<ValidationRule> rules = new ArrayList<>();

        rules.add(() -> this.title == null || this.title.isEmpty() ? "Título deve estar presente" : null);
        rules.add(() -> this.title != null && this.title.length() < 4 ? "Título deve possuir no mínimo 4 caracteres" : null);
        rules.add(() -> this.publisher == null || this.publisher.isEmpty() ? "Editora deve estar presente" : null);
        rules.add(() -> this.year == null ? "Ano deve estar presente" : null);
        rules.add(() -> this.quantity == null ? "Quantidade deve estar presente" : null);
        rules.add(() -> this.quantity != null && this.quantity <= 0 ? "Quantidade deve ser maior que 0" : null);

        return rules;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public String getMaterialTypeLabel() {
        return switch (materialType) {
            case BOOK -> "Livro";
            case MAGAZINE -> "Revista";
        };
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }
}
