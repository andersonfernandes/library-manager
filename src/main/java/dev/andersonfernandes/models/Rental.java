package dev.andersonfernandes.models;

import java.util.List;

public class Rental {
    private User user;
    private List<Material> materials;

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
}
