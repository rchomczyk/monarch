package moe.rafal.monarch.user;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserService {

    private final UserRepository userRepository;
    private final AsyncLoadingCache<UUID, User> userCache;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userCache = Caffeine.newBuilder()
            .expireAfterWrite(Duration.ofSeconds(20))
            .buildAsync(userRepository::findUserByUuid);
    }

    public CompletableFuture<User> findUserByUuid(UUID uuid) {
        return userCache.get(uuid);
    }

    public CompletableFuture<Void> saveUser(User user) {
        return CompletableFuture.runAsync(() -> userRepository.saveUser(user));
    }
}
