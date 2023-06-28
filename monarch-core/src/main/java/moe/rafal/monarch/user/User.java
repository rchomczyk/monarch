package moe.rafal.monarch.user;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private int languageId;

    public User(UUID uuid, int languageId) {
        this.uuid = uuid;
        this.languageId = languageId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
}