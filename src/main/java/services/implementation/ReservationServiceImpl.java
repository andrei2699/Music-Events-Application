package services.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.ReservationModel;
import services.FileSystemManager;
import services.ReservationService;
import services.ServiceProvider;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    @Override
    public List<ReservationModel> getReservationUsingEventId(int event_id) {
        List<ReservationModel> allReservations = getAllReservations();
        List<ReservationModel> searchResults = new ArrayList<>();
        for (ReservationModel reservation : allReservations)
            if (reservation.getEvent_id()==event_id)
                searchResults.add(reservation);
        return searchResults;
    }

    @Override
    public List<ReservationModel> getReservationUsingUserId(int user_id) {
        List<ReservationModel> allReservations = getAllReservations();
        List<ReservationModel> searchResults = new ArrayList<>();
        for (ReservationModel reservation : allReservations)
            if (reservation.getUser_id() == user_id)
                searchResults.add(reservation);
        return searchResults;
    }

    @Override
    public List<ReservationModel> getAllReservations() {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path reservationFilesPath = fileSystemManager.getReservationsFilePath();
        String jsonFileContent = fileSystemManager.readFileContent(reservationFilesPath);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(jsonFileContent, new TypeToken<List<ReservationModel>>() {
        }.getType());
    }

    @Override
    public void makeReservation(int userId, int eventId, int numberOfSeats) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path reservationsFilesPath = fileSystemManager.getReservationsFilePath();
        List<ReservationModel> reservations = getAllReservations();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        int biggestId = -1;
        for (ReservationModel reservation : reservations) {
            if (reservation.getReservation_id() > biggestId) {
                biggestId = reservation.getReservation_id();
            }
        }

        ReservationModel reservationModel = new ReservationModel(biggestId + 1, userId, eventId, numberOfSeats);

        reservations.add(reservationModel);

        String json = gson.toJson(reservations);
        fileSystemManager.writeToFile(reservationsFilesPath, json);
    }
}
