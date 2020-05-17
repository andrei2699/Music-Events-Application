package repository.implemenation;

import models.BarModel;
import repository.IBarRepository;
import services.IStorageManager;

public class BarRepository extends JSONRepository<BarModel> implements IBarRepository {
    public BarRepository(IStorageManager storageManager) {
        super(BarModel.class, storageManager);
    }
}
