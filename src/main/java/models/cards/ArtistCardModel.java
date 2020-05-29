package models.cards;

import models.ArtistModel;
import services.ServiceProvider;
import services.IUserService;

public class ArtistCardModel implements TableCardModel {
    private final String artistName;
    private final ArtistModel artistModel;

    public ArtistCardModel(ArtistModel artistModel) {
        this.artistModel = artistModel;
        IUserService userService = ServiceProvider.getUserService();
        artistName = userService.getUser(this.artistModel.getId()).getName();
    }

    public String getArtistName() {
        return artistName;
    }

    public ArtistModel getArtistModel() {
        return artistModel;
    }

    public boolean containsFilter(String filter) {
        return getArtistName().toLowerCase().contains(filter) || getArtistModel().getGenre().toLowerCase().contains(filter);
    }

    public ArtistCardModel getArtistCardModel() {
        return this;
    }
}
