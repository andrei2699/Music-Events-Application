package repository.implemenation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.EntityModel;
import repository.IRepository;
import services.IStorageManager;

import java.util.ArrayList;
import java.util.List;

public class JSONRepository<T extends EntityModel> implements IRepository<T> {
    private final Class<T> clazz;
    private final IStorageManager storageManager;
    private String fileName;

    public JSONRepository(Class<T> modelClass, IStorageManager storageManager) {
        clazz = modelClass;
        this.fileName = (modelClass.getSimpleName() + ".json");
        this.storageManager = storageManager;

        storageManager.initStorageUnit(fileName, "[]");
    }

    @Override
    public List<T> getAll() {
        String jsonFileContent = storageManager.readContent(fileName);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(jsonFileContent, TypeToken.getParameterized(ArrayList.class, clazz).getType());
    }

    @Override
    public T create(T entity) {
        List<T> allData = getAll();

        if (allData == null) {
            allData = new ArrayList<>();
        }

        allData.add(entity);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String json = gson.toJson(allData);
        storageManager.writeContent(fileName, json);

        return entity;
    }

    @Override
    public T update(T entity) {
        List<T> allData = getAll();

        if (allData == null) {
            return null;
        }

        boolean updated = false;
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).getId() == entity.getId()) {
                allData.set(i, entity);
                updated = true;
                break;
            }
        }

        if (!updated) {
            return null;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String json = gson.toJson(allData);
        storageManager.writeContent(fileName, json);

        return entity;
    }

    @Override
    public void setDestinationFileName(String fileName) {
        this.fileName = fileName;
    }
}
