package dev.andersonfernandes.models.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class Record {
    protected Long id;
    protected List<String> errors;

    protected abstract ArrayList<ValidationRule> validationRules();

    public boolean isValid() {
        if (this.errors == null) return validate();
        else return this.errors.isEmpty();
    }

    public boolean validate() {
        if (this.errors == null) this.errors = new ArrayList<>();

        validationRules().forEach(rule -> {
            String error = rule.call();
            if (error != null) this.errors.add(error);
        });

        return this.errors.isEmpty();
    }

    public Long getId() {
        return id;
    }

    public List<String> getErrors() {
        return errors == null ? new ArrayList<>() : errors;
    }
}
