package services;

import models.ArtistModel;

import java.util.List;

public interface IArtistService {
    ArtistModel getArtist(int user_id);

    List<ArtistModel> getArtists(String genre);

    ArtistModel updateArtist(ArtistModel model);

    ArtistModel createArtist(ArtistModel artistModel);

    List<ArtistModel> getAllArtists();
}
