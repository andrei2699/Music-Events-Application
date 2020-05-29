package models.cards;

import models.BarModel;
import services.ServiceProvider;
import services.IUserService;

public class BarCardModel implements TableCardModel {
    private final String barName;
    private final BarModel barModel;

    public BarCardModel(BarModel barModel) {
        this.barModel = barModel;
        IUserService userService = ServiceProvider.getUserService();
        barName = userService.getUser(this.barModel.getId()).getName();
    }

    public String getBarName() {
        return barName;
    }

    public BarModel getBarModel() {
        return barModel;
    }

    public boolean containsFilter(String filter) {
        return getBarName().toLowerCase().contains(filter) || getBarModel().getAddress().toLowerCase().contains(filter);
    }

    public BarCardModel getBarCardModel() {
        return this;
    }
}
