package services.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.ArtistModel;
import services.ServiceProvider;
import services.ArtistService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArtistServiceImpl implements ArtistService {
    @Override
    public ArtistModel getArtist(int user_id) {
        List<ArtistModel> allArtists = getAllArtists();
        return allArtists.stream().filter(a -> a.getUser_id() == user_id).findFirst().orElse(null);
    }

    @Override
    public List<ArtistModel> getArtists(String genre) {
        List<ArtistModel> allArtists = getAllArtists();
        List<ArtistModel> searchResults = new ArrayList<>();
        for (ArtistModel artist : allArtists)
            if (artist.getGenre().equals(genre))
                searchResults.add(artist);
        return searchResults;
    }

    @Override
    public void updateArtist(ArtistModel model) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path artistsFilePath = FileSystemManager.getArtistsFilePath();
        List<ArtistModel> artists = getAllArtists();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (ArtistModel artist : artists) {
            if (artist.getUser_id() == model.getUser_id()) {
                artist.setGenre(model.getGenre());
                artist.setIntervals(model.getIntervals());
                artist.setPath_to_image(model.getPath_to_image());
                artist.setIs_band(model.getIs_band());
                if (artist.getIs_band()) {
                    artist.setMembers(model.getMembers());
                }
                break;
            }
        }

        String json = gson.toJson(artists);
        fileSystemManager.writeToFile(artistsFilePath, json);
    }

    @Override
    public void createArtist(ArtistModel artistModel) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path artistsFilePath = FileSystemManager.getArtistsFilePath();
        List<ArtistModel> artists = getAllArtists();
        boolean found = false;

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (ArtistModel artist : artists) {
            if (artist.getUser_id() == artistModel.getUser_id()) {
                found = true;
                updateArtist(artistModel);
                break;
            }
        }

        if (!found) {
            artists.add(artistModel);

            String json = gson.toJson(artists);
            fileSystemManager.writeToFile(artistsFilePath, json);
        }
    }

    @Override
    public List<ArtistModel> getAllArtists() {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path artistsFilePath = FileSystemManager.getArtistsFilePath();
        String jsonFileContent = fileSystemManager.readFileContent(artistsFilePath);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(jsonFileContent, new TypeToken<List<ArtistModel>>() {
        }.getType());
    }
}
