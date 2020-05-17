package repository.implemenation;

import models.ArtistModel;
import repository.IArtistRepository;
import services.IStorageManager;

public class ArtistRepository extends JSONRepository<ArtistModel> implements IArtistRepository {
    public ArtistRepository(IStorageManager storageManager) {
        super(ArtistModel.class, storageManager);
    }
}
