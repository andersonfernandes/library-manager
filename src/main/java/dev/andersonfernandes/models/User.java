package dev.andersonfernandes.models;

import dev.andersonfernandes.models.utils.Record;
import dev.andersonfernandes.models.utils.ValidationRule;

import java.util.ArrayList;

public class User extends Record {
    public static final String TABLE_NAME = "users";
    private String name;
    private String address;
    private String email;
    private String registration;
    private String[] subjects;
    private UserType type;

    public User() {}

    public User(Long id, String name, String address, String email, UserType type, String registration, String[] subjects) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.registration = registration;
        this.subjects = subjects;
        this.type = type;
    }

    @Override
    protected ArrayList<ValidationRule> validationRules() {
        ArrayList<ValidationRule> rules = new ArrayList<>();

        rules.add(() -> this.name == null || this.name.isEmpty() ? "Nome deve estar presente" : null);
        rules.add(() -> this.address == null || this.address.isEmpty() ? "Endereço deve estar presente" : null);
        rules.add(() -> this.email == null || this.email.isEmpty() ? "Email deve estar presente" : null);
        rules.add(() -> this.type == null ? "Tipo deve estar presente" : null);
        rules.add(() -> {
            boolean condition = this.type != null &&
                    this.type.equals(UserType.STUDENT) &&
                    (this.registration == null || this.registration.isEmpty());
            return condition ? "Matrícula deve estar presente quando seu usuário for Aluno" : null;
        });
        rules.add(() -> {
            boolean condition = this.type != null &&
                    this.type.equals(UserType.TEACHER) &&
                    (this.subjects == null || this.subjects.length == 0);
            return condition ? "Alguma disciplina deve ser associada ao professor" : null;
        });

        return rules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String[] getSubjects() {
        return subjects == null ? new String[0] : subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
