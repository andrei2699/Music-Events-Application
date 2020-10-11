package repository.implemenation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.EntityModel;
import repository.IRepository;
import services.IStorageManager;
import utils.StringValidator;

import java.util.ArrayList;
import java.util.List;

public class JSONRepository<T extends EntityModel> implements IRepository<T> {
    private final Class<T> clazz;
    private final IStorageManager storageManager;
    private String fileName;

    public JSONRepository(Class<T> modelClass, IStorageManager storageManager, boolean initFile) {
        clazz = modelClass;
        this.fileName = (modelClass.getSimpleName() + "/");
        this.storageManager = storageManager;

        if (initFile) {
            initFile();
        }
    }

    public JSONRepository(Class<T> modelClass, IStorageManager storageManager) {
        this(modelClass, storageManager, true);
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String json = gson.toJson(entity);

        String writeResponseMessage = storageManager.writeContent(fileName, json);

        if(writeResponseMessage.contains("true")) {
            return entity;
        }
        else {
            return null;
        }
    }

    @Override
    public T update(T entity) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        String json = gson.toJson(entity);

        String writeResponseMessage = storageManager.writeContent(fileName, json);

        if(writeResponseMessage.contains("true")) {
            return entity;
        }
        else {
            return null;
        }
    }

    @Override
    public void setDestinationFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void initFile() {
        if (StringValidator.isStringNotEmpty(fileName)) {
            storageManager.initStorageUnit(fileName, "[]");
        }
    }
}
