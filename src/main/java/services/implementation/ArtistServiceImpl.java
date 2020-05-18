package services.implementation;

import models.ArtistModel;
import repository.IRepository;
import services.IArtistService;

import java.util.ArrayList;
import java.util.List;

public class ArtistServiceImpl implements IArtistService {
    private final IRepository<ArtistModel> artistRepository;

    public ArtistServiceImpl(IRepository<ArtistModel> artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public ArtistModel getArtist(int user_id) {
        List<ArtistModel> allArtists = getAllArtists();

        if (allArtists == null)
            return null;

        return allArtists.stream().filter(a -> a.getId() == user_id).findFirst().orElse(null);
    }

    @Override
    public List<ArtistModel> getArtists(String genre) {
        List<ArtistModel> allArtists = getAllArtists();

        if (allArtists == null)
            return new ArrayList<>();

        List<ArtistModel> searchResults = new ArrayList<>();
        for (ArtistModel artist : allArtists)
            if (artist.getGenre().contains(genre))
                searchResults.add(artist);
        return searchResults;
    }

    @Override
    public ArtistModel updateArtist(ArtistModel model) {
        return artistRepository.update(model);
    }

    @Override
    public ArtistModel createArtist(ArtistModel artistModel) {
        List<ArtistModel> artists = getAllArtists();

        if (artists == null)
            return artistRepository.create(artistModel);

        for (ArtistModel artist : artists) {
            if (artist.getId() == artistModel.getId()) {
                return updateArtist(artistModel);
            }
        }
        return artistRepository.create(artistModel);
    }

    @Override
    public List<ArtistModel> getAllArtists() {
        return artistRepository.getAll();
    }
}
