package services.implementation;

import models.BarModel;
import repository.IBarRepository;
import services.IBarService;

import java.util.ArrayList;
import java.util.List;

public class BarServiceImpl implements IBarService {

    private final IBarRepository barRepository;

    public BarServiceImpl(IBarRepository barRepository) {
        this.barRepository = barRepository;
    }

    @Override
    public BarModel getBar(int user_id) {
        List<BarModel> allBars = getAllBars();
        return allBars.stream().filter(b -> b.getId() == user_id).findFirst().orElse(null);
    }

    @Override
    public List<BarModel> getBars(String address) {
        List<BarModel> allBars = getAllBars();
        List<BarModel> searchResults = new ArrayList<>();
        for (BarModel bar : allBars)
            if (bar.getAddress().contains(address))
                searchResults.add(bar);
        return searchResults;
    }

    @Override
    public void updateBar(BarModel model) {
        barRepository.update(model);
    }

    @Override
    public void createBar(BarModel barModel) {
        List<BarModel> bars = getAllBars();

        for (BarModel bar : bars) {
            if (bar.getId() == barModel.getId()) {
                updateBar(barModel);
                return;
            }
        }

        barRepository.create(barModel);
    }

    @Override
    public List<BarModel> getAllBars() {
        return barRepository.getAll();
    }
}
