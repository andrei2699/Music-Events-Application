package services;

public interface IStorageManager {
    String readContent(String fileName);

    void writeContent(String fileName, String content);

    void initStorageUnit(String fileName, String content);
}
