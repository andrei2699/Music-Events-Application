package controllers;

public class TableModelContainer {
    private final TableCardModel cardModel;

    public TableModelContainer(TableCardModel cardModel) {
        this.cardModel = cardModel;
    }

    public TableCardModel getCardModel() {
        return cardModel;
    }

    public boolean containsFilter(String filter) {
        return cardModel.containsFilter(filter);
    }
}
