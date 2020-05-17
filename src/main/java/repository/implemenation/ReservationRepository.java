package repository.implemenation;

import models.ReservationModel;
import repository.IReservationRepository;
import services.IStorageManager;

public class ReservationRepository extends JSONRepository<ReservationModel> implements IReservationRepository {
    public ReservationRepository(IStorageManager storageManager) {
        super(ReservationModel.class, storageManager);
    }
}
