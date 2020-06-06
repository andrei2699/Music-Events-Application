package main;

import services.ICrashService;
import services.ServiceProvider;

public class Main {

    // run jar command
    // java -jar build/libs/"Music Events Application-1.0.jar"

    public static void main(String[] args) {
        try {
            JavaFXBoot.main(args);
        } catch (Exception e) {
            ICrashService crashService = ServiceProvider.getCrashService();
            crashService.createCrashReport(e.getMessage());
        }
    }
}
