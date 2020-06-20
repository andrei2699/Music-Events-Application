package models.cards;

import models.ArtistModel;
import services.IUserService;
import services.ServiceProvider;

public class ArtistCardModel implements TableCardModel {
    private final String artistName;
    private final ArtistModel artistModel;

    public ArtistCardModel(ArtistModel artistModel, IUserService userService) {
        this.artistModel = artistModel;
        artistName = userService.getUser(this.artistModel.getId()).getName();
    }

    public String getArtistName() {
        return artistName;
    }

    public ArtistModel getArtistModel() {
        return artistModel;
    }

    public boolean containsFilter(String filter) {
        if (filter == null)
            return true;
        return getArtistName().toLowerCase().contains(filter.toLowerCase()) || getArtistModel().getGenre().toLowerCase().contains(filter.toLowerCase());
    }

    public ArtistCardModel getArtistCardModel() {
        return this;
    }
}
