package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.BarModel;
import models.UserModel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BarService {

    BarService() {
    }

    public BarModel getBar(String name) {
        List<BarModel> allBars = getAllBars();
        return allBars.stream().filter(b -> b.getName().equals(name)).findFirst().orElse(null);
    }

    public List<BarModel> getBars(String address) {
        List<BarModel> allBars = getAllBars();
        List<BarModel> searchResults = new ArrayList<>();
        for (BarModel bar : allBars)
            if (bar.getAddress().contains(address))
                searchResults.add(bar);
        return searchResults;
    }

    public void updateBar(BarModel model) {
        FileSystemManager fileSystemManager = ServiceInjector.getInstance().getFileSystemManager();
        Path barsFilePath = fileSystemManager.getBarsFilePath();
        List<BarModel> bars = getAllBars();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (BarModel bar : bars) {
            if (bar.getUser_id() == model.getUser_id()) {
                bar.setName(model.getName());
                bar.setAddress(model.getAddress());
                bar.setIntervals(model.getIntervals());
                break;
            }
        }

        String json = gson.toJson(bars);
        fileSystemManager.writeToFile(barsFilePath, json);
    }

    public void createBar(BarModel barModel) throws UserExistsException {
        FileSystemManager fileSystemManager = ServiceInjector.getInstance().getFileSystemManager();
        Path barsFilePath = fileSystemManager.getBarsFilePath();
        List<BarModel> bars = getAllBars();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        bars.add(barModel);

        String json = gson.toJson(bars);
        fileSystemManager.writeToFile(barsFilePath, json);
    }

    public List<BarModel> getAllBars() {
        FileSystemManager fileSystemManager = ServiceInjector.getInstance().getFileSystemManager();
        Path barsFilePath = fileSystemManager.getBarsFilePath();
        String jsonFileContent = fileSystemManager.readFileContent(barsFilePath);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(jsonFileContent, new TypeToken<List<BarModel>>() {
        }.getType());
    }
}
