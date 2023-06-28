package moe.rafal.monarch.user;

class UserSaveException extends IllegalStateException {

    protected UserSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
