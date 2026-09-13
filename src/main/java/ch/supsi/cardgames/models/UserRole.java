package ch.supsi.cardgames.models;

public enum UserRole {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private String name;

    UserRole(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
