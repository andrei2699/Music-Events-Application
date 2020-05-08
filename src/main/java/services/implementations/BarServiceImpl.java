package services.implementations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.BarModel;
import services.FileSystemManager;
import services.ServiceProvider;
import services.interfaces.BarService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BarServiceImpl implements BarService {

    public BarServiceImpl() {
    }

    @Override
    public BarModel getBar(int user_id) {
        List<BarModel> allBars = getAllBars();
        return allBars.stream().filter(b -> b.getUser_id() == user_id).findFirst().orElse(null);
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
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path barsFilePath = fileSystemManager.getBarsFilePath();
        List<BarModel> bars = getAllBars();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (BarModel bar : bars) {
            if (bar.getUser_id() == model.getUser_id()) {
                bar.setAddress(model.getAddress());
                bar.setPath_to_image(model.getPath_to_image());
                bar.setIntervals(model.getIntervals());
                break;
            }
        }

        String json = gson.toJson(bars);
        fileSystemManager.writeToFile(barsFilePath, json);
    }

    @Override
    public void createBar(BarModel barModel) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path barsFilePath = fileSystemManager.getBarsFilePath();
        List<BarModel> bars = getAllBars();
        boolean found = false;

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (BarModel bar : bars) {
            if (bar.getUser_id() == barModel.getUser_id()) {
                found = true;
                updateBar(barModel);
                break;
            }
        }
        if (!found) {
            bars.add(barModel);

            String json = gson.toJson(bars);
            fileSystemManager.writeToFile(barsFilePath, json);
        }
    }

    @Override
    public List<BarModel> getAllBars() {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path barsFilePath = fileSystemManager.getBarsFilePath();
        String jsonFileContent = fileSystemManager.readFileContent(barsFilePath);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(jsonFileContent, new TypeToken<List<BarModel>>() {
        }.getType());
    }
}
