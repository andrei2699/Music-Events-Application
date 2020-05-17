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
    private final Class clazz;
    private final IStorageManager storageManager;
    private final String fileName;

    public JSONRepository(Class<T> modelClass, IStorageManager storageManager) {
        clazz = modelClass;
        fileName = (modelClass.getSimpleName() + ".json");
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

        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i).getId() == entity.getId()) {
                allData.set(i, entity);
                break;
            }
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String json = gson.toJson(allData);
        storageManager.writeContent(fileName, json);

        return entity;
    }
}
