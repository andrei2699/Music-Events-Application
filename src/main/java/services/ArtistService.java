package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.ArtistModel;
import models.BarModel;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArtistService {

    ArtistService() {
    }

    public ArtistModel getArtist(String name) {
        List<ArtistModel> allArtists = getAllArtists();
        return allArtists.stream().filter(a -> a.getName().equals(name)).findFirst().orElse(null);
    }

    public ArtistModel getArtist(int user_id) {
        List<ArtistModel> allArtists = getAllArtists();
        return allArtists.stream().filter(a -> a.getUser_id() == user_id).findFirst().orElse(null);
    }

    public List<ArtistModel> getArtists(String genre) {
        List<ArtistModel> allArtists = getAllArtists();
        List<ArtistModel> searchResults = new ArrayList<>();
        for (ArtistModel artist : allArtists)
            if (artist.getGenre().equals(genre))
                searchResults.add(artist);
        return searchResults;
    }

    public void updateArtist(ArtistModel artistModel) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path artistsFilePath = fileSystemManager.getArtistsFilePath();
        List<ArtistModel> artists = getAllArtists();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for (ArtistModel artist : artists) {
            if (artist.getUser_id() == artistModel.getUser_id()) {
                artist.setName(artistModel.getName());
                artist.setGenre(artistModel.getGenre());
                artist.setIntervals(artistModel.getIntervals());
                artist.setPath_to_image(artistModel.getPath_to_image());
                if(artist.getIs_band())
                    artist.setMembers(artistModel.getMembers());
                break;
            }
        }

        String json = gson.toJson(artists);
        fileSystemManager.writeToFile(artistsFilePath, json);
    }

    public void createArtist(ArtistModel artistModel) {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path artistsFilePath = fileSystemManager.getArtistsFilePath();
        List<ArtistModel> artists = getAllArtists();
        boolean found=false;

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        for(ArtistModel artist : artists) {
            if (artist.getUser_id() == artistModel.getUser_id()) {
                found=true;
                updateArtist(artistModel);
                break;
            }
        }
        if(!found)
            artists.add(artistModel);

        String json = gson.toJson(artists);
        fileSystemManager.writeToFile(artistsFilePath, json);
    }

    public List<ArtistModel> getAllArtists() {
        FileSystemManager fileSystemManager = ServiceProvider.getFileSystemManager();
        Path artistsFilePath = fileSystemManager.getArtistsFilePath();
        String jsonFileContent = fileSystemManager.readFileContent(artistsFilePath);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(jsonFileContent, new TypeToken<List<ArtistModel>>() {
        }.getType());
    }
}
