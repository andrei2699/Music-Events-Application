package repository.implemenation;

import models.EventModel;
import repository.IEventRepository;
import services.IStorageManager;

public class EventRepository extends JSONRepository<EventModel> implements IEventRepository {
    public EventRepository(IStorageManager storageManager) {
        super(EventModel.class, storageManager);
    }
}
