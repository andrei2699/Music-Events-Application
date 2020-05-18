package services.implementation;

import models.BarModel;
import repository.IRepository;
import services.IBarService;

import java.util.ArrayList;
import java.util.List;

public class BarServiceImpl implements IBarService {

    private final IRepository<BarModel> barRepository;

    public BarServiceImpl(IRepository<BarModel> barRepository) {
        this.barRepository = barRepository;
    }

    @Override
    public BarModel getBar(int user_id) {
        List<BarModel> allBars = getAllBars();
        if (allBars == null)
            return null;
        return allBars.stream().filter(b -> b.getId() == user_id).findFirst().orElse(null);
    }

    @Override
    public List<BarModel> getBars(String address) {
        List<BarModel> allBars = getAllBars();
        if (allBars == null)
            return new ArrayList<>();
        List<BarModel> searchResults = new ArrayList<>();
        for (BarModel bar : allBars)
            if (bar.getAddress().contains(address))
                searchResults.add(bar);
        return searchResults;
    }

    @Override
    public BarModel updateBar(BarModel model) {
        return barRepository.update(model);
    }

    @Override
    public BarModel createBar(BarModel barModel) {
        List<BarModel> bars = getAllBars();
        if(bars==null)

           return barRepository.create(barModel);

        for (BarModel bar : bars) {
            if (bar.getId() == barModel.getId()) {
                return updateBar(barModel);
            }
        }
        return barRepository.create(barModel);
    }

    @Override
    public List<BarModel> getAllBars() {
        return barRepository.getAll();
    }
}
