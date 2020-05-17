package repository.implemenation;

import models.UserModel;
import repository.IUserRepository;
import services.IStorageManager;

public class UserRepository extends JSONRepository<UserModel> implements IUserRepository {
    public UserRepository(IStorageManager storageManager) {
        super(UserModel.class, storageManager);
    }
}
