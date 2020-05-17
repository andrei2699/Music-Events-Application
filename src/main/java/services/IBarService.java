package services;

import models.BarModel;

import java.util.List;

public interface IBarService {
    BarModel getBar(int user_id);

    List<BarModel> getBars(String address);

    void updateBar(BarModel model);

    void createBar(BarModel barModel);

    List<BarModel> getAllBars();
}
