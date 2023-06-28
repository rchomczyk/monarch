package moe.rafal.monarch.user;

import java.util.UUID;

public interface UserRepository {

    void createSchema();

    User findUserByUuid(UUID uuid) throws UserFindException;

    void saveUser(User user) throws UserSaveException;
}
