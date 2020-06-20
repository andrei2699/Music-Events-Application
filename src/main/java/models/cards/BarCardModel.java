package models.cards;

import models.BarModel;
import services.IUserService;
import services.ServiceProvider;

public class BarCardModel implements TableCardModel {
    private final String barName;
    private final BarModel barModel;

    public BarCardModel(BarModel barModel) {
        this(barModel, ServiceProvider.getUserService());
    }

    protected BarCardModel(BarModel barModel, IUserService userService) {
        this.barModel = barModel;
        barName = userService.getUser(this.barModel.getId()).getName();
    }

    public String getBarName() {
        return barName;
    }

    public BarModel getBarModel() {
        return barModel;
    }

    public boolean containsFilter(String filter) {
        if (filter == null)
            return true;
        return getBarName().toLowerCase().contains(filter.toLowerCase()) || getBarModel().getAddress().toLowerCase().contains(filter.toLowerCase());
    }

    public BarCardModel getBarCardModel() {
        return this;
    }
}
