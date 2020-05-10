package models;

import services.ServiceProvider;
import services.UserService;

public class ArtistCardModel implements FilterableModel {
    private final String artistName;
    private final ArtistModel artistModel;

    public ArtistCardModel(ArtistModel artistModel) {
        this.artistModel = artistModel;
        UserService userService = ServiceProvider.getUserService();
        artistName = userService.getUser(this.artistModel.getUser_id()).getName();
    }

    public String getArtistName() {
        return artistName;
    }

    public ArtistModel getArtistModel() {
        return artistModel;
    }

    public boolean containsFilter(String filter) {
        return getArtistName().contains(filter);
    }
}
