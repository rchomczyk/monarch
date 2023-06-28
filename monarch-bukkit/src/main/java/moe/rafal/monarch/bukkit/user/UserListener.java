package moe.rafal.monarch.bukkit.user;

import moe.rafal.monarch.user.User;
import moe.rafal.monarch.user.UserService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class UserListener implements Listener {

    private static final int DEFAULT_LANGUAGE_ID = 1;
    private final UserService userService;

    public UserListener(UserService userService) {
        this.userService = userService;
    }

    @EventHandler
    public void onUserCreate(PlayerJoinEvent event) {
        userService.findUserByUuid(event.getPlayer().getUniqueId())
            .thenAccept(user -> createUserIfNull(user, event.getPlayer().getUniqueId()));
    }

    private void createUserIfNull(User user, UUID uuid) {
        if (user == null) {
            userService.saveUser(new User(uuid, DEFAULT_LANGUAGE_ID));
        }
    }
}
