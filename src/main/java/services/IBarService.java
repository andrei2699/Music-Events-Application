package services;

import models.BarModel;

import java.util.List;

public interface IBarService {
    BarModel getBar(int user_id);

    List<BarModel> getBars(String address);

    BarModel updateBar(BarModel model);

    BarModel createBar(BarModel barModel);

    List<BarModel> getAllBars();
}
