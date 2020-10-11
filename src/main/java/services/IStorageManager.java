package services;

import java.io.IOException;
import java.net.ProtocolException;

public interface IStorageManager {
    String readContent(String fileName);

    String writeContent(String fileName, String content);

    void initStorageUnit(String fileName, String content);
}
