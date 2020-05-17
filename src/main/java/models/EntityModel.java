package models;

public abstract class EntityModel {
    protected int id;

    public EntityModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
