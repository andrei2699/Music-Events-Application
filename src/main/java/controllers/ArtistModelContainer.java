package controllers;

import models.ArtistCardModel;
import models.ArtistModel;

public class ArtistModelContainer {
    private final ArtistCardModel artistCardModel;

    public ArtistModelContainer(ArtistModel artistModel) {
        this.artistCardModel = new ArtistCardModel(artistModel);
    }

    public ArtistCardModel getArtistCardModel() {
        return artistCardModel;
    }

    public boolean containsFilter(String filter) {
        return artistCardModel.containsFilter(filter);
    }
}
