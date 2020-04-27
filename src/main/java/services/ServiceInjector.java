package services;

public class ServiceInjector {
    private static ServiceInjector instance;

    private ServiceInjector() {

    }

    public static ServiceInjector getInstance() {
        if (instance == null) {
            instance = new ServiceInjector();
        }
        return instance;
    }
}
