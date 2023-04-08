package dev.andersonfernandes.models;

import dev.andersonfernandes.models.utils.Record;
import dev.andersonfernandes.models.utils.ValidationRule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rental extends Record {
    private static final int STUDENT_RENT_DAYS = 5;
    private static final int TEACHER_RENT_DAYS = 10;

    public static final String TABLE_NAME = "rentals";
    public static final String MATERIALS_JOIN_TABLE_NAME = "rental_materials";

    private RentalStatus status;
    private LocalDate returnAt;
    private User user;
    private List<Material> materials = new ArrayList<>();

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public LocalDate getReturnAt() {
        return returnAt;
    }

    public void setReturnAt(LocalDate returnAt) {
        this.returnAt = returnAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Material> materials) {
        this.materials = materials;
    }

    public void calculateReturnAt() {
        int rentDays = switch (user.getType()) {
            case STUDENT -> STUDENT_RENT_DAYS;
            case TEACHER -> TEACHER_RENT_DAYS;
        };
        this.returnAt = LocalDate.now().plusDays(rentDays);
    }

    @Override
    protected ArrayList<ValidationRule> validationRules() {
        return new ArrayList<>();
    }
}
