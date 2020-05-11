package models.other;

public enum UserType {
    RegularUser("Utilizator obisnuit"), // utilizator obisnuit
    Manager("Manager Local"), // manager local
    Artist("Artist / Formatie"); // artist sau formatie

    private String value;

    UserType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
