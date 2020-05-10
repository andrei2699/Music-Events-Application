package controllers;

import models.BarCardModel;
import models.BarModel;

public class BarModelContainer {
    private final BarCardModel barCardModel;

    public BarModelContainer(BarModel barModel) {
        this.barCardModel = new BarCardModel(barModel);
    }

    public BarCardModel getBarCardModel() {
        return barCardModel;
    }

    public boolean containsFilter(String filter) {
        return barCardModel.containsFilter(filter);
    }
}
